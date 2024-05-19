package com.springboot.flats.Service;

import com.springboot.flats.Entity.Flat;
import com.springboot.flats.Entity.Person;
import com.springboot.flats.Entity.ObjectListDTO;
import com.springboot.flats.Entity.PersonLinkFlat;
import com.springboot.flats.Repository.FlatRepository;
import com.springboot.flats.Repository.PersonLinkFlatRepository;
import com.springboot.flats.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonService implements IService<Person> {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    FlatRepository flatRepository;

    @Autowired
    PersonLinkFlatRepository personLinkFlatRepository;

    @Override
    public ResponseEntity<?> create(Person person) {
        personRepository.save(person);
        Optional<Person> newPerson = personRepository.findById(person.getId());

        if (newPerson.isPresent()) {
            return new ResponseEntity<>(newPerson.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Person>> get() {
        return new ResponseEntity<>(personRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        Optional<Person> person = personRepository.findById(id);

        if (person.isPresent()) {
            return new ResponseEntity<>(person, HttpStatus.FOUND);
        }
        return new ResponseEntity<>("No such person", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> remove(Long id) {
        Optional<Person> person = personRepository.findById(id);

        if (person.isPresent()) {
            personRepository.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("No such person", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> update(Person person) {
        Long id = person.getId();
        Optional<Person> oldPerson = personRepository.findById(id);

        if (oldPerson.isEmpty()) return new ResponseEntity<>("No such person", HttpStatus.NOT_FOUND);

        if (person.getName() == null) person.setName(oldPerson.get().getName());

        if (person.getSurname() == null) person.setSurname(oldPerson.get().getSurname());

        Person newPerson = oldPerson.get();
        newPerson.setName(person.getName());
        newPerson.setSurname(person.getSurname());
        personRepository.save(newPerson);
        return new ResponseEntity<>(newPerson, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> addPersonFlatLink(Long personId, Long flatId, boolean owning) {
        Optional<Person> person = personRepository.findById(personId);
        Optional<Flat> flat = flatRepository.findById(flatId);

        if (person.isEmpty() || flat.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        PersonLinkFlat link = new PersonLinkFlat();
        link.setPerson(person.get());
        link.setFlat(flat.get());
        link.setOwning(owning);
        personLinkFlatRepository.save(link);
        return new ResponseEntity<>("Person " + personId + " link flat " + flatId, HttpStatus.OK);
    }

    public ResponseEntity<?> getPersonFlats(Long id) {
        Optional<Person> person = personRepository.findById(id);

        if (person.isEmpty()) return new ResponseEntity<>("No such person", HttpStatus.NOT_FOUND);

        List<PersonLinkFlat> list = personLinkFlatRepository.findAll();
        List<Flat> flatList = list.stream()
                .filter(a -> a.getPerson().getId().equals(id) && a.isOwning())
                .map(PersonLinkFlat::getFlat)
                .toList();
        ObjectListDTO dtoResponse = new ObjectListDTO();
        dtoResponse.setObject(person.get());
        dtoResponse.setList(dtoResponse.getObjectList(flatList, Flat.class));
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }
}
