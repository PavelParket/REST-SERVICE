package com.springboot.flats.Service;

import com.springboot.flats.Entity.*;
import com.springboot.flats.Entity.DTO.BuildingDTO;
import com.springboot.flats.Entity.DTO.FlatDTO;
import com.springboot.flats.Entity.DTO.ObjectListDTO;
import com.springboot.flats.Entity.DTO.PersonDTO;
import com.springboot.flats.Repository.BuildingRepository;
import com.springboot.flats.Repository.FlatRepository;
import com.springboot.flats.Repository.PersonLinkFlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FlatService implements IService<FlatDTO, Flat> {
    @Autowired
    FlatRepository flatRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    PersonLinkFlatRepository personLinkFlatRepository;

    @Override
    public FlatDTO create(Flat flat) {
        if (flat.getNumber() == 0 || flat.getTotalSquare() == 0 || flat.getCountOfRooms() == 0 || flat.getBuilding() == null) {
            return null;
        }

        Building building = flat.getBuilding();
        building = buildingRepository.findById(building.getId()).orElseThrow(() -> new IllegalArgumentException("Building not found"));
        flat.setBuilding(building);
        building.getFlats().add(flat);
        Flat newFlat = flatRepository.save(flat);
        buildingRepository.save(building);
        return new FlatDTO(newFlat);
    }

    @Override
    public List<FlatDTO> get() {
        List<Flat> flats = flatRepository.findAll();
        return flats.stream()
                .map(FlatDTO::new)
                .toList();
    }

    @Override
    public FlatDTO getById(Long id) {
        Optional<Flat> flat = flatRepository.findById(id);
        return flat.map(FlatDTO::new).orElse(null);
    }

    @Override
    public boolean remove(Long id) {
        Optional<Flat> flat = flatRepository.findById(id);

        if (flat.isEmpty()) return false;

        Building building = flat.get().getBuilding();
        building.getFlats().remove(flat.get());
        buildingRepository.save(building);
        return true;
    }

    @Override
    public FlatDTO update(Flat flat) {
        Long id = flat.getId();
        Optional<Flat> oldFlat = flatRepository.findById(id);

        if (oldFlat.isEmpty()) return null;

        if (flat.getNumber() == 0) flat.setNumber(oldFlat.get().getNumber());

        if (flat.getCountOfRooms() == 0) flat.setCountOfRooms(oldFlat.get().getCountOfRooms());

        if (flat.getTotalSquare() == 0) flat.setTotalSquare(oldFlat.get().getTotalSquare());

        if (flat.getBuilding() == null) flat.setBuilding(oldFlat.get().getBuilding());

        Flat newFlat = oldFlat.get();
        newFlat.setNumber(flat.getNumber());
        newFlat.setCountOfRooms(flat.getCountOfRooms());
        newFlat.setTotalSquare(flat.getTotalSquare());
        newFlat.setBuilding(buildingRepository.findById(flat.getBuilding().getId()).get());
        flatRepository.save(newFlat);
        return new FlatDTO(newFlat);
    }

    public ObjectListDTO getFlatTenants(Long id) {
        Optional<Flat> flat = flatRepository.findById(id);

        if (flat.isEmpty()) return null;

        List<PersonLinkFlat> list = personLinkFlatRepository.findAll();
        List<PersonDTO> personList = list.stream()
                .filter(a -> a.getFlat().getId().equals(id) && !a.isOwning())
                .map(PersonLinkFlat::getPerson)
                .map(PersonDTO::new)
                .toList();
        ObjectListDTO dtoResponse = new ObjectListDTO();
        dtoResponse.setObject(new FlatDTO(flat.get()));
        dtoResponse.setList(dtoResponse.getObjectList(personList, PersonDTO.class));
        return dtoResponse;
    }

    public BuildingDTO getBuildingInfo(Long id) {
        Optional<Flat> flat = flatRepository.findById(id);
        return flat.map(f -> new BuildingDTO(f.getBuilding())).orElse(null);
    }

    public Map<String, Object> getCountOfTenants(Long id) {
        Optional<Flat> flat = flatRepository.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (flat.isEmpty()) {
            response.put("error", "No such flat");
            return response;
        }

        List<PersonLinkFlat> linkList = flat.get().getLinkList();
        int count = (int) linkList.stream()
                .filter(a -> !a.isOwning())
                .count();
        response.put("count", count);
        return response;
    }
}
