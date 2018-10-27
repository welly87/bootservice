package com.tambunan.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private boolean hasAccessDoor;

    public Employee() {

    }

    public Employee(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public void join(Date date) {
        hasAccessDoor = true;
    }

    public boolean isHasAccessDoor() {
        return hasAccessDoor;
    }

    public void setHasAccessDoor(boolean hasAccessDoor) {
        this.hasAccessDoor = hasAccessDoor;
    }

    public void resign() {
        hasAccessDoor = false;
    }

    public boolean hasAccessDoor() {
        return hasAccessDoor;
    }
}
