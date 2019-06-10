package com.restapi.lab.Controllers;

import com.google.gson.Gson;
import com.restapi.lab.DAO.EmployeeRep;
import com.restapi.lab.DAO.TaskRep;
import com.restapi.lab.Models.Requests.CreateEmployee;
import com.restapi.lab.Models.Requests.CreateTask;
import com.restapi.lab.Models.Requests.UpdateEmployee;
import com.restapi.lab.Models.Requests.UpdateTask;
import com.restapi.lab.Models.Task;
import com.restapi.lab.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "tasks")
public class TaskController {
    @Autowired
    private TaskRep taskRep;

    @Autowired
    private EmployeeRep employeeRep;

    @Autowired
    private TaskService taskService;

    @KafkaListener(topics = "tasktop", groupId = "crud_1")
    public void createTasklisten(String message) {
//        System.out.println("Received Messasge in group foo: " + message);
        CreateTask task = new Gson().fromJson(message, CreateTask.class);
        if (!(task.getLabel() == null || task.getTime() == null)){
            taskService.create(task.getLabel(), task.getTime(), task.getPerformer_id());
        }
    }

    @KafkaListener(topics = "updtasktop", groupId = "crud_1")
    public void updateTasklisten(String message) {
        UpdateTask task = new Gson().fromJson(message, UpdateTask.class);
        if (task.getId() != null){
            taskService.update(task.getId(), task.getLabel(), task.getTime(), task.isReady(), task.getPerformer_id());
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestBody CreateTask task) {
        try {
            if(task.getLabel() == null)
                throw new NullPointerException("Field 'label' is absent or equal null");
            if(task.getTime() == null)
                throw new NullPointerException("Field 'time' is absent or equal null");

            taskService.create(task.getLabel(), task.getTime(), task.getPerformer_id());
            return new ResponseEntity(HttpStatus.OK);

        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(e.getMessage());
        }


    }

    @RequestMapping(method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> update(@RequestBody UpdateTask task) {

        try{
            if(task.getId() == null)
                throw new NullPointerException("Field 'id' is absent or equal null");

//            if (taskService.checkId(task.getId())) {
                taskService.update(task.getId(), task.getLabel(), task.getTime(), task.isReady(), task.getPerformer_id());
//            } else {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .contentType(MediaType.TEXT_PLAIN)
//                        .body("Such id is absent");
//            }

            return new ResponseEntity(HttpStatus.OK);
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(e.getMessage());
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        if (taskService.checkId(id) ) {
            taskService.remove(id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Such id is absent");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getemployee(@PathVariable("id") Integer id) {

//        ResponseEntity<?> resp = ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .contentType(MediaType.TEXT_PLAIN)
//                .body("Such id is absent")
//
        Optional<Task> res = taskRep.findById(id);
//        return res.map((x) -> new ResponseEntity(res.get(), HttpStatus.OK)).orElseGet(
//                resp.
//        );
        if(res.isPresent()){
            return new ResponseEntity(res.get(), HttpStatus.OK);

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Such id is absent");
        }
//         return res;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Task> getAllemployees() {
        return taskService.getAll();
    }
}
