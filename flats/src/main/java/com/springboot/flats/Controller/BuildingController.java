package com.springboot.flats.Controller;

import com.springboot.flats.Entity.Building;
import com.springboot.flats.Service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BuildingController {
    @Autowired
    BuildingService buildingService;

    @PostMapping("/building")
    public ResponseEntity<?> createBuilding(@RequestBody Building building) {
        return buildingService.create(building);
    }

    @GetMapping("/buildings")
    public ResponseEntity<?> getAllBuildings() {
        return buildingService.getAll();
    }

    @GetMapping("/buildings/{id}")
    public ResponseEntity<?> getBuildingById(@PathVariable Long id) {
        return buildingService.getById(id);
    }

    @DeleteMapping("/building/{id}")
    public ResponseEntity<?> removeBuildingById(@PathVariable Long id) {
        return buildingService.removeById(id);
    }
}
