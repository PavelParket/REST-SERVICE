package com.springboot.flats.Repository;

import com.springboot.flats.Entity.PersonLinkFlat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonLinkFlatRepository extends JpaRepository<PersonLinkFlat, Long> {
}
