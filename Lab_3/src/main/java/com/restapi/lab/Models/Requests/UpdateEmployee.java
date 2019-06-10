package com.restapi.lab.Models.Requests;

public class UpdateEmployee {

    private Integer id;
    private String fullname;
    private String position;
    private String company;
    private Boolean isDeleted;

    public UpdateEmployee(){

    }

    public UpdateEmployee(Integer id, String fullname, String position, String company){
        this.id = id;
        this.company = company;
        this.fullname = fullname;
        this.position = position;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
