package com.springboot.flats.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "flat")
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;

    private int countOfRooms;

    private double totalSquare;

    @OneToMany(mappedBy = "flat", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JsonManagedReference("flat")
    private List<PersonLinkFlat> linkList;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "building_id")
    @JsonBackReference("building")
    private Building building;

    public Flat() {
    }

    public Flat(int number, int countOfRooms, double totalSquare) {
        this.number = number;
        this.countOfRooms = countOfRooms;
        this.totalSquare = totalSquare;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCountOfRooms() {
        return countOfRooms;
    }

    public void setCountOfRooms(int countOfRooms) {
        this.countOfRooms = countOfRooms;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public double getTotalSquare() {
        return totalSquare;
    }

    public void setTotalSquare(double totalSquare) {
        this.totalSquare = totalSquare;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<PersonLinkFlat> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<PersonLinkFlat> linkList) {
        this.linkList = linkList;
    }
}
