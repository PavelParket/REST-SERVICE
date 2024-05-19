package com.springboot.flats.Service;

import com.springboot.flats.Entity.*;
import com.springboot.flats.Repository.BuildingRepository;
import com.springboot.flats.Repository.FlatRepository;
import com.springboot.flats.Repository.PersonLinkFlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FlatService implements IService<Flat> {
    @Autowired
    FlatRepository flatRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    PersonLinkFlatRepository personLinkFlatRepository;

    @Override
    public ResponseEntity<?> create(Flat flat) {
        Building building = flat.getBuilding();
        Long id = building.getId();
        building = buildingRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        flat.setBuilding(building);
        building.getFlats().add(flat);
        buildingRepository.save(building);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Flat>> get() {
        return new ResponseEntity<>(flatRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        Optional<Flat> flat = flatRepository.findById(id);

        if (flat.isPresent()) {
            return new ResponseEntity<>(flat, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> remove(Long id) {
        Flat flat = flatRepository.findById(id).get();
        Building building = flat.getBuilding();
        building.getFlats().remove(flat);
        buildingRepository.save(building);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> update(Flat flat) {
        Long id = flat.getId();
        Optional<Flat> oldFlat = flatRepository.findById(id);

        if (oldFlat.isEmpty()) return new ResponseEntity<>("No such flat", HttpStatus.NOT_FOUND);

        if (flat.getNumber() == 0) flat.setNumber(oldFlat.get().getNumber());

        if (flat.getCountOfRooms() == 0) flat.setCountOfRooms(oldFlat.get().getCountOfRooms());

        if (flat.getTotalSquare() == 0) flat.setTotalSquare(oldFlat.get().getTotalSquare());

        Flat newFlat = oldFlat.get();
        newFlat.setNumber(flat.getNumber());
        newFlat.setCountOfRooms(flat.getCountOfRooms());
        newFlat.setTotalSquare(flat.getTotalSquare());
        flatRepository.save(newFlat);
        return new ResponseEntity<>(newFlat, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> getFlatPersons(Long id) {
        Optional<Flat> flat = flatRepository.findById(id);

        if (flat.isEmpty()) return new ResponseEntity<>("No such flat", HttpStatus.NOT_FOUND);

        List<PersonLinkFlat> list = personLinkFlatRepository.findAll();
        List<Person> personList = list.stream()
                .filter(a -> a.getFlat().getId().equals(id) && !a.isOwning())
                .map(PersonLinkFlat::getPerson)
                .toList();
        ObjectListDTO dtoResponse = new ObjectListDTO();
        dtoResponse.setObject(flat.get());
        dtoResponse.setList(dtoResponse.getObjectList(personList, Person.class));
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }
}
