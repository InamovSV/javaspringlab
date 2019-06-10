package com.restapi.lab.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Company implements Base{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String name;
    private boolean isDeleted;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.EAGER)
    private Set<Employee> employees;

    public Company(){
        isDeleted = false;
    }

    public Company(String name){

        this.name = name;
        isDeleted = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(boolean b) {
        isDeleted = b;
    }
}
