package com.springboot.flats.Service;

import com.springboot.flats.Entity.Building;
import com.springboot.flats.Entity.DTO.BuildingDTO;
import com.springboot.flats.Entity.DTO.ObjectListDTO;
import com.springboot.flats.Entity.DTO.PersonDTO;
import com.springboot.flats.Entity.Flat;
import com.springboot.flats.Entity.Person;
import com.springboot.flats.Entity.PersonLinkFlat;
import com.springboot.flats.Repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BuildingService implements IService<BuildingDTO, Building> {
    @Autowired
    BuildingRepository buildingRepository;

    @Override
    public BuildingDTO create(Building building) {
        buildingRepository.save(building);
        Optional<Building> newBuilding = buildingRepository.findById(building.getId());
        return newBuilding.map(BuildingDTO::new).orElse(null);

    }

    @Override
    public List<BuildingDTO> get() {
        List<Building> buildings = buildingRepository.findAll();
        return buildings.stream()
                .map(BuildingDTO::new)
                .toList();
    }

    @Override
    public BuildingDTO getById(Long id) {
        Optional<Building> building = buildingRepository.findById(id);
        return building.map(BuildingDTO::new).orElse(null);
    }

    @Override
    public boolean remove(Long id) {
        Optional<Building> building = buildingRepository.findById(id);

        if (building.isPresent()) {
            buildingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public BuildingDTO update(Building building) {
        Long id = building.getId();
        Optional<Building> oldBuilding = buildingRepository.findById(id);

        if (oldBuilding.isEmpty()) return null;

        if (building.getAddress() == null) building.setAddress(oldBuilding.get().getAddress());

        if (building.getCountOfFlats() == 0) building.setCountOfFlats(oldBuilding.get().getCountOfFlats());

        Building newBuilding = oldBuilding.get();
        newBuilding.setAddress(building.getAddress());
        newBuilding.setCountOfFlats(building.getCountOfFlats());
        buildingRepository.save(newBuilding);
        return new BuildingDTO(newBuilding);
    }

    public Integer getCountOfTenants(Long id) {
        Optional<Building> building = buildingRepository.findById(id);

        if (building.isEmpty()) return null;

        List<Flat> flats = building.get().getFlats();
        return flats.stream()
                .flatMap(flat -> flat.getLinkList().stream())
                .filter(link -> !link.isOwning())
                .mapToInt(link -> 1)
                .sum();
    }

    public ObjectListDTO getOwnersInBuilding(Long id) {
        Optional<Building> building = buildingRepository.findById(id);

        if (building.isEmpty()) return null;

        List<Flat> flats = building.get().getFlats();
        List<PersonLinkFlat> links = flats.stream()
                .flatMap(flat -> flat.getLinkList().stream())
                .filter(PersonLinkFlat::isOwning)
                .toList();
        List<PersonDTO> owners = links.stream()
                .map(PersonLinkFlat::getPerson)
                .distinct()
                .map(PersonDTO::new)
                .toList();

        ObjectListDTO dto = new ObjectListDTO();
        dto.setObject(new BuildingDTO(building.get()));
        dto.setList(dto.getObjectList(owners, PersonDTO.class));
        return dto;
    }
}
