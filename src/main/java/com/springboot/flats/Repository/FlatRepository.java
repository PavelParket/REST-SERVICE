package com.springboot.flats.Repository;

import com.springboot.flats.Entity.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlatRepository extends JpaRepository<Flat, Long> {
}
