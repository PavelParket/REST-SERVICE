package com.springboot.flats.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "flat")
//@JsonIgnoreProperties({"owner"})
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private int number;
    private int countOfPeople;
    private int countOfRooms;
    private double totalSquare;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Person owner;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    @JsonBackReference
    private Building building;

    public Flat() {}

    public Flat(int countOfPeople, int countOfRooms, double totalSquare) {
        this.countOfPeople = countOfPeople;
        this.countOfRooms = countOfRooms;
        this.totalSquare = totalSquare;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCountOfPeople() {
        return countOfPeople;
    }

    public void setCountOfPeople(int countOfPeople) {
        this.countOfPeople = countOfPeople;
    }

    public int getCountOfRooms() {
        return countOfRooms;
    }

    public void setCountOfRooms(int countOfRooms) {
        this.countOfRooms = countOfRooms;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
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

    /*public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }*/
}
