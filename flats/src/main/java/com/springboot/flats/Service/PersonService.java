package com.springboot.flats.Service;

import com.springboot.flats.Entity.Person;
import com.springboot.flats.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService implements IService<Person> {
    @Autowired
    PersonRepository personRepository;

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
    public ResponseEntity<List<Person>> getAll() {
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
    public ResponseEntity<?> removeById(Long id) {
        Optional<Person> person = personRepository.findById(id);

        if (person.isPresent()) {
            personRepository.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("No such person", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> update(Long id, Person person) {
        Optional<Person> oldPerson = personRepository.findById(id);

        if (oldPerson.isEmpty()) return new ResponseEntity<>("No such person", HttpStatus.NOT_FOUND);

        if (person.getName() == null) person.setName(oldPerson.get().getName());

        if (person.getSurname() == null) person.setSurname(oldPerson.get().getSurname());

        if (person.isOwner()) person.setOwner(oldPerson.get().isOwner());

        Person newPerson = oldPerson.get();
        newPerson.setName(person.getName());
        newPerson.setSurname(person.getSurname());
        newPerson.setOwner(person.isOwner());
        personRepository.save(newPerson);
        return new ResponseEntity<>(newPerson, HttpStatus.ACCEPTED);
    }
}
