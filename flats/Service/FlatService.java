package com.springboot.flats.Service;

import com.springboot.flats.Entity.Flat;
import com.springboot.flats.Repository.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlatService implements IService<Flat> {
    @Autowired
    FlatRepository flatRepository;

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

        flatRepository.save(flat);
        return new ResponseEntity<>(flat, HttpStatus.ACCEPTED);
    }
}
