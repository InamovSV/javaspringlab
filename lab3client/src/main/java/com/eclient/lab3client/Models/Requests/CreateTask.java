package com.eclient.lab3client.Models.Requests;

import java.util.Date;

public class CreateTask {
    private String label;
    private Date time;
    private Integer performer_id;

    public CreateTask(){

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getPerformer_id() {
        return performer_id;
    }

    public void setPerformer_id(Integer performer_id) {
        this.performer_id = performer_id;
    }
}
