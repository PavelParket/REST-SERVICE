package com.springboot.flats.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "person_link_flat")
public class PersonLinkFlat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "person_id")
    @JsonBackReference("person")
    private Person person;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "flat_id")
    @JsonBackReference("flat")
    private Flat flat;

    boolean owning;

    public PersonLinkFlat() {
    }

    public PersonLinkFlat(boolean owning) {
        this.owning = owning;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Flat getFlat() {
        return flat;
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }

    public boolean isOwning() {
        return owning;
    }

    public void setOwning(boolean owning) {
        this.owning = owning;
    }
}
