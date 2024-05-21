package com.springboot.flats.Entity.DTO;

import com.springboot.flats.Entity.Flat;

public class FlatDTO {
    private Long id;

    private int number;

    private int countOfRooms;

    private double totalSquare;

    public FlatDTO(Flat flat) {
        this.id = flat.getId();
        this.number = flat.getNumber();
        this.countOfRooms = flat.getCountOfRooms();
        this.totalSquare = flat.getTotalSquare();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCountOfRooms() {
        return countOfRooms;
    }

    public void setCountOfRooms(int countOfRooms) {
        this.countOfRooms = countOfRooms;
    }

    public double getTotalSquare() {
        return totalSquare;
    }

    public void setTotalSquare(double totalSquare) {
        this.totalSquare = totalSquare;
    }
}
