package com.springboot.flats.Controller;

import com.springboot.flats.Entity.DTO.ObjectListDTO;
import com.springboot.flats.Entity.DTO.PersonDTO;
import com.springboot.flats.Entity.Person;
import com.springboot.flats.Service.PersonService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "api/person")
@Tag(name = "Person Controller", description = "To process information about Person")
public class PersonController {
    @Autowired
    PersonService personService;

    @PostMapping
    @Operation(summary = "Create a new person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Person created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> create(@RequestBody Person person) {
        PersonDTO personDTO = personService.create(person);
        if (personDTO == null) return new ResponseEntity<>("Not created", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(personDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all persons")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(personService.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get person by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person found"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<?> getById(@PathVariable Long id) {
        PersonDTO personDTO = personService.getById(id);
        if (personDTO == null) return new ResponseEntity<>("No such person", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a person by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<?> remove(@PathVariable Long id) {
        if (personService.remove(id)) return new ResponseEntity<>("Deleted", HttpStatus.OK);
        return new ResponseEntity<>("No such person", HttpStatus.NOT_FOUND);
    }

    @PutMapping
    @Operation(summary = "Update a person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person updated successfully"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<?> update(@RequestBody Person person) {
        PersonDTO personDTO = personService.update(person);
        if (personDTO == null) return new ResponseEntity<>("No such person", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }

    @PostMapping("/person-link")
    @Operation(summary = "Add a person-flat link")
    public ResponseEntity<?> addPersonFlatLink(@RequestBody Map<String, Object> body) {
        Long personId = body.get("personId") != null ? ((Integer) body.get("personId")).longValue() : null;
        Long flatId = body.get("flatId") != null ? ((Integer) body.get("flatId")).longValue() : null;
        boolean owning = body.get("owning") != null && (boolean) body.get("owning");
        HttpStatus httpStatus = personService.addPersonFlatLink(personId, flatId, owning);
        return new ResponseEntity<>(httpStatus);
    }

    @DeleteMapping("/person-link")
    @Operation(summary = "Remove a person-flat link")
    public ResponseEntity<?> removePersonFlatLink(@RequestBody Map<String, Object> body) {
        Long personId = body.get("personId") != null ? ((Integer) body.get("personId")).longValue() : null;
        Long flatId = body.get("flatId") != null ? ((Integer) body.get("flatId")).longValue() : null;
        return personService.removePersonFlatLink(personId, flatId) ?
                new ResponseEntity<>("Deleted", HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/person-flats/{id}")
    @Operation(summary = "Get flats in person possession")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner flats retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<?> getFlatsInPossession(@PathVariable Long id) {
        ObjectListDTO dto = personService.getFlatsInPossession(id);
        if (dto == null) return new ResponseEntity<>("No such person", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/count-flats/{id}")
    @Operation(summary = "Get count of flats in person possession")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flats count retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<?> getCountOfFlatsInPossession(@PathVariable Long id) {
        Map<String, Object> response = personService.getCountOfFlatsInPossession(id);

        if (response.containsKey("error")) return new ResponseEntity<>(response.get("error"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>("Person " + response.get("name") + " " + response.get("surname") +
                " owns " + response.get("count") + " flats", HttpStatus.OK);
    }
}
