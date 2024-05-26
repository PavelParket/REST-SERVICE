package com.springboot.flats.Service;

import com.springboot.flats.Entity.*;
import com.springboot.flats.Entity.DTO.*;
import com.springboot.flats.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonService implements IService<PersonDTO, Person> {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    FlatRepository flatRepository;

    @Autowired
    PersonLinkFlatRepository personLinkFlatRepository;

    public PersonDTO create(Person person) {
        personRepository.save(person);
        Optional<Person> newPerson = personRepository.findById(person.getId());
        return newPerson.map(PersonDTO::new).orElse(null);
    }

    @Override
    public List<PersonDTO> get() {
        List<Person> persons = personRepository.findAll();
        return persons.stream()
                .map(PersonDTO::new)
                .toList();
    }

    @Override
    public PersonDTO getById(Long id) {
        Optional<Person> person = personRepository.findById(id);
        return person.map(PersonDTO::new).orElse(null);

    }

    @Override
    public boolean remove(Long id) {
        Optional<Person> person = personRepository.findById(id);

        if (person.isPresent()) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public PersonDTO update(Person person) {
        Long id = person.getId();
        Optional<Person> oldPerson = personRepository.findById(id);

        if (oldPerson.isEmpty()) return null;

        if (person.getName() == null) person.setName(oldPerson.get().getName());

        if (person.getSurname() == null) person.setSurname(oldPerson.get().getSurname());

        Person newPerson = oldPerson.get();
        newPerson.setName(person.getName());
        newPerson.setSurname(person.getSurname());
        personRepository.save(newPerson);
        return new PersonDTO(newPerson);
    }

    public HttpStatus addPersonFlatLink(Long personId, Long flatId, boolean owning) {
        Optional<Person> person = personRepository.findById(personId);
        Optional<Flat> flat = flatRepository.findById(flatId);

        if (person.isEmpty()) return HttpStatus.NOT_FOUND;
        if (flat.isEmpty()) return HttpStatus.NOT_FOUND;

        PersonLinkFlat link = new PersonLinkFlat();
        link.setPerson(person.get());
        link.setFlat(flat.get());
        link.setOwning(owning);
        personLinkFlatRepository.save(link);
        return HttpStatus.OK;
    }

    public boolean removePersonFlatLink(Long personId, Long flatId) {
        PersonLinkFlat link = personLinkFlatRepository.findByPersonIdAndFlatId(personId, flatId);

        if (link == null) return false;

        personLinkFlatRepository.deleteById(link.getId());
        return true;
    }

    public ObjectListDTO getOwnerFlats(Long id) {
        Optional<Person> person = personRepository.findById(id);

        if (person.isEmpty()) return null;

        List<PersonLinkFlat> list = personLinkFlatRepository.findAll();
        List<FlatDTO> flatList = list.stream()
                .filter(a -> a.getPerson().getId().equals(id) && a.isOwning())
                .map(PersonLinkFlat::getFlat)
                .map(FlatDTO::new)
                .toList();
        ObjectListDTO dtoResponse = new ObjectListDTO();
        dtoResponse.setObject(person.get());
        dtoResponse.setList(dtoResponse.getObjectList(flatList, FlatDTO.class));
        return dtoResponse;
    }

    public Map<String, Object> getCountOfFlatsInPossession(Long id) {
        Optional<Person> person = personRepository.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (person.isEmpty()) {
            response.put("error", "No such person");
            return response;
        }

        List<PersonLinkFlat> list = personLinkFlatRepository.findAll();
        int count = (int) list.stream()
                .filter(a -> a.getPerson().getId().equals(id) && a.isOwning())
                .count();
        response.put("name", person.get().getName());
        response.put("surname", person.get().getSurname());
        response.put("count", count);
        return response;
    }
}
