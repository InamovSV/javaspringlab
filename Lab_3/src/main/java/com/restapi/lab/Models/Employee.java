package com.restapi.lab.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "emloyee")
public class Employee implements Base{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String fullname;
    private String position;
    private String company;
    private boolean isDeleted;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "performer")
    private Set<Task> tasks;

    public Employee(String fullname, String position){
        this.fullname = fullname;
        this.position = position;
        isDeleted = false;
    }

    public Employee(){
        isDeleted = false;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
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
