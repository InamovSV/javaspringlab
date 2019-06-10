package com.restapi.lab.Services;

import com.restapi.lab.DAO.EmployeeRep;
import com.restapi.lab.DAO.TaskRep;
import com.restapi.lab.Models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
@Service
public class TaskService {

    private TaskRep taskRep;
    private EmployeeRep employeeRep;

    @Autowired
    public TaskService(TaskRep taskRep, EmployeeRep employeeRep) {
        this.taskRep = taskRep;
        this.employeeRep = employeeRep;
    }

    public Boolean checkId(int id){
        return taskRep.findById(id).isPresent();
    }

    public Iterable<Task> getAll(){
        return taskRep.findAll();
    }

    public Optional<Task> getById(int id){
        return taskRep.findById(id);
    }

    public void create(String label, Date time, Integer performerId){
        Task res = new Task(label, time);
        if(performerId != null)
            res.setPerformer(employeeRep.findById(performerId).orElse(null));
        taskRep.save(res);
    }



    public void update(int id,
                       String label,
                       Date time,
                       Boolean isReady,
                       Integer performer_id){
        Optional<Task> oldTask = taskRep.findById(id);
        if(oldTask.isPresent()){
            Task res = new Task();
            if(label != null)
                oldTask.get().setLabel(label);
            if(time != null)
                oldTask.get().setTime(time);
            if(isReady != null)
                oldTask.get().setIsReady(isReady);
            if(performer_id != null)
                oldTask.get().setPerformer(employeeRep.findById(performer_id).orElse(null));
            res.setId(id);
                taskRep.save(oldTask.get());
        } else {
            throw new NullPointerException("There's no such id");
        }

    }

    public void remove(int id){
        taskRep.deleteById(id);
    }
}
