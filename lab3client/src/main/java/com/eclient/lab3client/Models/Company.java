package com.eclient.lab3client.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

public class Company {

    private int id;

    private String name;

    @JsonIgnore
    private Set<Employee> employees;

    public Company(){

    }

    public Company(String name){
        this.name = name;
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
}
