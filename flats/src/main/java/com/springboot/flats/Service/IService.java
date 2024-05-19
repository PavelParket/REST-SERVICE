package com.springboot.flats.Service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IService<T> {
    ResponseEntity<?> create(T object);
    ResponseEntity<List<T>> get();
    ResponseEntity<?> getById(Long id);
    ResponseEntity<?> remove(Long id);
    ResponseEntity<?> update(T object);
}
