package com.springboot.flats.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    @OneToMany(mappedBy = "person", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JsonManagedReference("person")
    private List<PersonLinkFlat> linkList;

    public Person() {
    }

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<PersonLinkFlat> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<PersonLinkFlat> linkList) {
        this.linkList = linkList;
    }
}
