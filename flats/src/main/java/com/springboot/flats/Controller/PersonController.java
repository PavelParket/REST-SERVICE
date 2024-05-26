package com.springboot.flats.Controller;

import com.springboot.flats.Entity.DTO.ObjectListDTO;
import com.springboot.flats.Entity.DTO.PersonDTO;
import com.springboot.flats.Entity.Person;
import com.springboot.flats.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "api/person")
public class PersonController {
    @Autowired
    PersonService personService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Person person) {
        PersonDTO personDTO = personService.create(person);
        if (personDTO == null) return new ResponseEntity<>("Not created", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(personDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(personService.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        PersonDTO personDTO = personService.getById(id);
        if (personDTO == null) return new ResponseEntity<>("No such person", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        if (personService.remove(id)) return new ResponseEntity<>("Deleted", HttpStatus.OK);
        return new ResponseEntity<>("No such person", HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Person person) {
        PersonDTO personDTO = personService.update(person);
        if (personDTO == null) return new ResponseEntity<>("No such person", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @PostMapping("/person-link")
    public ResponseEntity<?> addPersonFlatLink(@RequestBody Map<String, Object> body) {
        Long personId = ((Integer) body.get("personId")).longValue();
        Long flatId = ((Integer) body.get("flatId")).longValue();
        boolean owning = body.get("owning") != null && (boolean) body.get("owning");
        HttpStatus httpStatus = personService.addPersonFlatLink(personId, flatId, owning);
        return new ResponseEntity<>(httpStatus);
    }

    @DeleteMapping("/person-link")
    public ResponseEntity<?> removePersonFlatLink(@RequestBody Map<String, Object> body) {
        Long personId = ((Integer) body.get("personId")).longValue();
        Long flatId = ((Integer) body.get("flatId")).longValue();
        return personService.removePersonFlatLink(personId, flatId) ?
                new ResponseEntity<>("Deleted", HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/person-flats/{id}")
    public ResponseEntity<?> getOwnerFlats(@PathVariable Long id) {
        ObjectListDTO dto = personService.getOwnerFlats(id);
        if (dto == null) return new ResponseEntity<>("No such person", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/count-flats/{id}")
    public ResponseEntity<?> getCountOfFlatsInPossession(@PathVariable Long id) {
        Map<String, Object> response = personService.getCountOfFlatsInPossession(id);

        if (response.containsKey("error")) new ResponseEntity<>(response.get("error"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>("Person " + response.get("name") + " " + response.get("surname") +
                " owns " + response.get("count") + " flats", HttpStatus.OK);
    }
}
