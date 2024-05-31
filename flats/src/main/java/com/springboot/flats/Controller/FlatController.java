package com.springboot.flats.Controller;

import com.springboot.flats.Entity.DTO.BuildingDTO;
import com.springboot.flats.Entity.DTO.FlatDTO;
import com.springboot.flats.Entity.DTO.ObjectListDTO;
import com.springboot.flats.Entity.Flat;
import com.springboot.flats.Service.FlatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "api/flat")
@Tag(name = "Flat Controller", description = "To process information about Flat")
public class FlatController {
    @Autowired
    FlatService flatService;

    @PostMapping
    @Operation(summary = "Create a new flat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Flat created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> create(@RequestBody Flat flat) {
        FlatDTO flatDTO = flatService.create(flat);
        if (flatDTO == null) return new ResponseEntity<>("Not created", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(flatDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all flats")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(flatService.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get flat by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flat found"),
            @ApiResponse(responseCode = "404", description = "Flat not found")
    })
    public ResponseEntity<?> getById(@PathVariable Long id) {
        FlatDTO flatDTO = flatService.getById(id);
        if (flatDTO == null) return new ResponseEntity<>("No such flat", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(flatDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a flat by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flat deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Flat not found")
    })
    public ResponseEntity<?> remove(@PathVariable Long id) {
        if (flatService.remove(id)) return new ResponseEntity<>("Deleted", HttpStatus.ACCEPTED);
        return new ResponseEntity<>("No such flat", HttpStatus.NOT_FOUND);
    }

    @PutMapping
    @Operation(summary = "Update a flat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flat updated successfully"),
            @ApiResponse(responseCode = "404", description = "Flat not found")
    })
    public ResponseEntity<?> update(@RequestBody Flat flat) {
        FlatDTO flatDTO = flatService.update(flat);
        if (flatDTO == null) return new ResponseEntity<>("No such flat", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(flatDTO, HttpStatus.OK);
    }

    @GetMapping("/flat-persons/{id}")
    @Operation(summary = "Get tenants in a flat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tenants retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Flat not found")
    })
    public ResponseEntity<?> getFlatTenants(@PathVariable Long id) {
        ObjectListDTO dto = flatService.getFlatTenants(id);
        if (dto == null) return new ResponseEntity<>("No such flat", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("building/{id}")
    @Operation(summary = "Get building info for a flat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Building info retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Flat not found")
    })
    public ResponseEntity<?> getBuildingInfo(@PathVariable Long id) {
        BuildingDTO buildingDTO = flatService.getBuildingInfo(id);
        if (buildingDTO == null) return new ResponseEntity<>("No such flat", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(buildingDTO, HttpStatus.OK);
    }

    @GetMapping("/count-tenants/{id}")
    @Operation(summary = "Get count of tenants in a flat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tenants count retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Flat not found")
    })
    public ResponseEntity<?> getCountOfTenants(@PathVariable Long id) {
        Map<String, Object> response = flatService.getCountOfTenants(id);

        if (response.containsKey("error")) return new ResponseEntity<>(response.get("error"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>("Count of tenant in the flat is " + response.get("count"), HttpStatus.OK);
    }
}
