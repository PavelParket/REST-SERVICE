package com.springboot.flats.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "building")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private int countOfFlats;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    @JsonManagedReference("building")
    private List<Flat> flats;

    public Building() {
    }

    public Building(String address, int countOfFlats) {
        this.address = address;
        this.countOfFlats = countOfFlats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCountOfFlats() {
        return countOfFlats;
    }

    public void setCountOfFlats(int countOfFlats) {
        this.countOfFlats = countOfFlats;
    }

    public List<Flat> getFlats() {
        return flats;
    }

    public void setFlats(List<Flat> flats) {
        this.flats = flats;
    }
}
