package com.springboot.flats.Service;

import com.springboot.flats.Entity.Flat;
import com.springboot.flats.Entity.ObjectListDTO;
import com.springboot.flats.Entity.Person;
import com.springboot.flats.Entity.PersonLinkFlat;
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
    PersonLinkFlatRepository personLinkFlatRepository;

    @Override
    public ResponseEntity<?> create(Flat flat) {
        flatRepository.save(flat);
        Optional<Flat> newFlat = flatRepository.findById(flat.getId());

        if (newFlat.isPresent()) {
            return new ResponseEntity<>(newFlat.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Flat>> getAll() {
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
    public ResponseEntity<?> removeById(Long id) {
        Optional<Flat> flat = flatRepository.findById(id);

        if (flat.isPresent()) {
            flatRepository.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> update(Long id, Flat flat) {
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
