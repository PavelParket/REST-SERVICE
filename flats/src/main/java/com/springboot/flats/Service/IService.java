package com.springboot.flats.Service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IService<T> {
    ResponseEntity<?> create(T object);
    ResponseEntity<List<T>> getAll();
    ResponseEntity<?> getById(Long id);
    ResponseEntity<?> removeById(Long id);
    ResponseEntity<?> update(Long id, T object);
}
