package com.springboot.flats.Service;

import com.springboot.flats.Entity.Building;
import com.springboot.flats.Repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuildingService implements IService<Building> {
    @Autowired
    BuildingRepository buildingRepository;

    @Override
    public ResponseEntity<?> create(Building building) {
        buildingRepository.save(building);
        Optional<Building> newBuilding = buildingRepository.findById(building.getId());

        if (newBuilding.isPresent()) {
            return new ResponseEntity<>(newBuilding.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Building>> getAll() {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        Optional<Building> building = buildingRepository.findById(id);

        if (building.isPresent()) {
            return new ResponseEntity<>(building, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> removeById(Long id) {
        Optional<Building> building = buildingRepository.findById(id);

        if (building.isPresent()) {
            buildingRepository.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> update(Long id, Building building) {
        Optional<Building> oldBuilding = buildingRepository.findById(id);

        if (oldBuilding.isEmpty()) return new ResponseEntity<>("No such building", HttpStatus.NOT_FOUND);

        if (building.getAddress() == null) building.setAddress(oldBuilding.get().getAddress());

        if (building.getCountOfFlats() == 0) building.setCountOfFlats(oldBuilding.get().getCountOfFlats());

        Building newBuilding = oldBuilding.get();
        newBuilding.setAddress(building.getAddress());
        newBuilding.setCountOfFlats(building.getCountOfFlats());
        buildingRepository.save(building);
        return new ResponseEntity<>(building, HttpStatus.ACCEPTED);
    }
}
