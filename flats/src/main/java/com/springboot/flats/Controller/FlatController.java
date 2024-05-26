package com.springboot.flats.Controller;

import com.springboot.flats.Entity.DTO.BuildingDTO;
import com.springboot.flats.Entity.DTO.FlatDTO;
import com.springboot.flats.Entity.DTO.ObjectListDTO;
import com.springboot.flats.Entity.Flat;
import com.springboot.flats.Service.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "api/flat")
public class FlatController {
    @Autowired
    FlatService flatService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Flat flat) {
        FlatDTO flatDTO = flatService.create(flat);
        if (flatDTO == null) return new ResponseEntity<>("Not created", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(flatDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(flatService.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        FlatDTO flatDTO = flatService.getById(id);
        if (flatDTO == null) return new ResponseEntity<>("No such flat", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(flatDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        if (flatService.remove(id)) return new ResponseEntity<>("Deleted", HttpStatus.ACCEPTED);
        return new ResponseEntity<>("No such flat", HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Flat flat) {
        FlatDTO flatDTO = flatService.update(flat);
        if (flatDTO == null) return new ResponseEntity<>("No such flat", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(flatDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping("/flat-persons/{id}")
    public ResponseEntity<?> getFlatTenants(@PathVariable Long id) {
        ObjectListDTO dto = flatService.getFlatTenants(id);
        if (dto == null) return new ResponseEntity<>("No such flat", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("building/{id}")
    public ResponseEntity<?> getBuildingInfo(@PathVariable Long id) {
        BuildingDTO buildingDTO = flatService.getBuildingInfo(id);
        if (buildingDTO == null) return new ResponseEntity<>("No such flat", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(buildingDTO, HttpStatus.OK);
    }

    @GetMapping("/count-tenants/{id}")
    public ResponseEntity<?> getCountOfTenants(@PathVariable Long id) {
        Map<String, Object> response = flatService.getCountOfTenants(id);

        if (response.containsKey("error")) return new ResponseEntity<>(response.get("error"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>("Count of tenant in the flat is " + response.get("count"), HttpStatus.OK);
    }
}
