package com.springboot.flats.Controller;

import com.springboot.flats.Entity.Person;
import com.springboot.flats.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {
    @Autowired
    PersonService personService;

    @PostMapping("/person")
    public ResponseEntity<?> createPerson(@RequestBody Person person) {
        return personService.create(person);
    }

    @GetMapping("/persons")
    public ResponseEntity<?> getAllPersons() {
        return personService.getAll();
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<?> getPersonById(@PathVariable Long id) {
        return personService.getById(id);
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<?> removePersonById(@PathVariable Long id) {
        return personService.removeById(id);
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<?> updatePersonById(@PathVariable Long id, @RequestBody Person person) {
        return personService.update(id, person);
    }
}
