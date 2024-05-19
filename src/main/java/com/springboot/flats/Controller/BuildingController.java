package com.springboot.flats.Controller;

import com.springboot.flats.Entity.Building;
import com.springboot.flats.Service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/building")
public class BuildingController {
    @Autowired
    BuildingService buildingService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Building building) {
        return buildingService.create(building);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return buildingService.get();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return buildingService.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        return buildingService.remove(id);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Building building) {
        return buildingService.update(building);
    }
}
