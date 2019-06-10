package com.eclient.lab3client.Models.Requests;

public class CreateEmployee {
    private String fullname;
    private String position;
    private String company;

    public CreateEmployee() {

    }

    public CreateEmployee(String fullname, String position, String company) {
        this.company = company;
        this.position = position;
        this.fullname = fullname;
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
}
