package com.springboot.flats.Service;

import java.util.List;

public interface IService<T, U> {
    T create(U object);

    List<T> get();

    T getById(Long id);

    boolean remove(Long id);

    T update(U object);
}
