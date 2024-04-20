package com.springboot.flats.Repository;

import com.springboot.flats.Entity.PersonLinkFlat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonLinkFlatRepository extends JpaRepository<PersonLinkFlat, Long> {
    List<PersonLinkFlat> findByPersonId(Long personId);
    List<PersonLinkFlat> findByFlatId(Long flatId);
}
