package com.springboot.flats.Entity.DTO;

import com.springboot.flats.Entity.Building;

public class BuildingDTO {
    private Long id;

    private String address;

    private int countOfFlats;

    public BuildingDTO(Building building) {
        this.id = building.getId();
        this.address = building.getAddress();
        this.countOfFlats = building.getCountOfFlats();
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
}
