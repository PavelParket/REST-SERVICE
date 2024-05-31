package com.springboot.flats.Controller;

import com.springboot.flats.Entity.Building;
import com.springboot.flats.Entity.DTO.BuildingDTO;
import com.springboot.flats.Service.BuildingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/building")
@Tag(name = "Building  Controller", description = "To process information about Building")
public class BuildingController {
    @Autowired
    BuildingService buildingService;

    @PostMapping
    @Operation(summary = "Create a new building")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Building created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> create(@RequestBody Building building) {
        BuildingDTO buildingDTO = buildingService.create(building);
        if (buildingDTO == null) return new ResponseEntity<>("Not created", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(buildingDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all buildings")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(buildingService.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")

    @Operation(summary = "Get building by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Building found"),
            @ApiResponse(responseCode = "404", description = "Building not found")
    })
    public ResponseEntity<?> getById(@PathVariable Long id) {
        BuildingDTO buildingDTO = buildingService.getById(id);
        if (buildingDTO == null) return new ResponseEntity<>("No such building", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(buildingDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a building by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Building deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Building not found")
    })
    public ResponseEntity<?> remove(@PathVariable Long id) {
        if (buildingService.remove(id)) return new ResponseEntity<>("Deleted", HttpStatus.OK);
        return new ResponseEntity<>("No such building", HttpStatus.NOT_FOUND);
    }

    @PutMapping
    @Operation(summary = "Update a building")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Building updated successfully"),
            @ApiResponse(responseCode = "404", description = "Building not found")
    })
    public ResponseEntity<?> update(@RequestBody Building building) {
        BuildingDTO buildingDTO = buildingService.update(building);
        if (buildingDTO == null) return new ResponseEntity<>("No such building", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(buildingDTO, HttpStatus.OK);
    }

    @GetMapping("/count-tenants/{id}")
    @Operation(summary = "Get count of tenants in a building")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tenants count retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Building not found")
    })
    public ResponseEntity<?> getCountOfTenants(@PathVariable Long id) {
        Integer count = buildingService.getCountOfTenants(id);
        if (count == null) return new ResponseEntity<>("No such building", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>("Count of tenant in the building is " + count, HttpStatus.OK);
    }

    @GetMapping("/owners/{id}")
    @Operation(summary = "Get owners in a building")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owners retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Building not found")
    })
    public ResponseEntity<?> getOwnersInBuilding(@PathVariable Long id) {
        return buildingService.getOwnersInBuilding(id) == null ?
                new ResponseEntity<>("No such building", HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(buildingService.getOwnersInBuilding(id), HttpStatus.OK);
    }
}
