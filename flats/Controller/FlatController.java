package com.springboot.flats.Controller;

import com.springboot.flats.Entity.Flat;
import com.springboot.flats.Service.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/flat")
public class FlatController {
    @Autowired
    FlatService flatService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Flat flat) {
        return flatService.create(flat);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return flatService.get();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return flatService.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        return flatService.remove(id);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Flat flat) {
        return flatService.update(flat);
    }

    @GetMapping("/flat-persons/{id}")
    public ResponseEntity<?> getFlatPersons(@PathVariable Long id) {
        return flatService.getFlatPersons(id);
    }
}
