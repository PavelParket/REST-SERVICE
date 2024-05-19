package com.springboot.flats.Controller;

import com.springboot.flats.Entity.Person;
import com.springboot.flats.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return personService.create(person);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return personService.get();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return personService.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        return personService.remove(id);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Person person) {
        return personService.update(person);
    }

    @PostMapping("/person-link")
    public ResponseEntity<?> addPersonFlatLink(@RequestBody Map<String, Object> body) {
        Long personId = ((Integer) body.get("personId")).longValue();
        Long flatId = ((Integer) body.get("flatId")).longValue();
        boolean owning = body.get("owning") != null && (boolean) body.get("owning");
        return personService.addPersonFlatLink(personId, flatId, owning);
    }

    @GetMapping("/person-flats/{id}")
    public ResponseEntity<?> getPersonFlats(@PathVariable Long id) {
        return personService.getPersonFlats(id);
    }
}
