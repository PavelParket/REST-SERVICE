package com.springboot.flats.Controller;

import com.springboot.flats.Entity.Building;
import com.springboot.flats.Entity.DTO.BuildingDTO;
import com.springboot.flats.Service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/building")
public class BuildingController {
    @Autowired
    BuildingService buildingService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Building building) {
        BuildingDTO buildingDTO = buildingService.create(building);
        if (buildingDTO == null) return new ResponseEntity<>("Not created", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(buildingDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(buildingService.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        BuildingDTO buildingDTO = buildingService.getById(id);
        if (buildingDTO == null) return new ResponseEntity<>("No such building", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(buildingDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        if (buildingService.remove(id)) return new ResponseEntity<>(null, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Building building) {
        BuildingDTO buildingDTO = buildingService.update(building);
        if (buildingDTO == null) return new ResponseEntity<>("No such building", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(buildingDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping("/count-tenants/{id}")
    public ResponseEntity<?> getCountOfTenants(@PathVariable Long id) {
        Integer count = buildingService.getCountOfTenants(id);
        if (count == null) return new ResponseEntity<>("No such building", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>("Count of tenant in the building is " + count, HttpStatus.OK);
    }
}
