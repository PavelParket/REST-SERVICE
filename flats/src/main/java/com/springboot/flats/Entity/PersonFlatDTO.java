package com.springboot.flats.Entity;

import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;

public class PersonFlatDTO {
    private Person person;
    private List<Flat> flatList;

    public PersonFlatDTO() {
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Flat> getFlatList() {
        return flatList;
    }

    public void setFlatList(List<Flat> flatList) {
        this.flatList = flatList;
    }

    public List<Flat> getObjectFlatList(List<Flat> flatList) {
        if (flatList != null) {
            List<Flat> realFlatList = new ArrayList<>();
            for (Flat flat : flatList) {
                if (flat instanceof HibernateProxy) realFlatList.add((Flat) ((HibernateProxy) flat).getHibernateLazyInitializer().getImplementation());
                else realFlatList.add(flat);
            }
            return realFlatList;
        }
        return flatList;
    }
}
