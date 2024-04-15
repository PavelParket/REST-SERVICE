package com.springboot.flats.Controller;

import com.springboot.flats.Entity.Flat;
import com.springboot.flats.Service.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FlatController {
    @Autowired
    FlatService flatService;

    @PostMapping("/flat")
    public ResponseEntity<?> createFlat(@RequestBody Flat flat) {
        return flatService.create(flat);
    }

    @GetMapping("/flats")
    public ResponseEntity<?> getAllFlats() {
        return flatService.getAll();
    }

    @GetMapping("/flats/{id}")
    public ResponseEntity<?> getFlatById(@PathVariable Long id) {
        return flatService.getById(id);
    }

    @DeleteMapping("/flat/{id}")
    public ResponseEntity<?> removeFlatById(@PathVariable Long id) {
        return flatService.removeById(id);
    }

    @PutMapping("/flat/{id}")
    public ResponseEntity<?> updateFlatById(@PathVariable Long id, @RequestBody Flat flat) {
        return flatService.update(id, flat);
    }
}
