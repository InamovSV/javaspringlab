package com.restapi.lab.Controllers;

import com.google.gson.Gson;
import com.restapi.lab.DAO.EmployeeRep;
import com.restapi.lab.Models.Requests.CreateEmployee;
import com.restapi.lab.Models.Employee;
import com.restapi.lab.Models.Requests.UpdateEmployee;
import com.restapi.lab.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping(path = "employees")
public class EmployeeController {
    @Autowired
    private EmployeeRep employeeRep;

    @Autowired
    EmployeeService employeeService;

    @KafkaListener(topics = "emptop", groupId = "crud_1")
    public void createEmployeelisten(String message) {
//        System.out.println("Received Messasge in group foo: " + message);
         CreateEmployee employee = new Gson().fromJson(message, CreateEmployee.class);
        if (!(employee.getCompany() == null || employee.getPosition() == null || employee.getFullname() == null)){
            employeeService.create(employee.getFullname(), employee.getPosition(), employee.getCompany());
        }
    }

    @KafkaListener(topics = "updemptop", groupId = "crud_1")
    public void updateEmployeelisten(String message) {
        UpdateEmployee employee = new Gson().fromJson(message, UpdateEmployee.class);
        if (employee.getId() != null){
            employeeService.update(employee.getId(), employee.getFullname(), employee.getPosition(), employee.getCompany(), employee.getIsDeleted());
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestBody CreateEmployee employee) {
        try {
//            if(employee.getFullname() != null && employee.getPosition() != null && employee.getCompany() != null){
            if (employee.getCompany() == null)
                throw new NullPointerException("Field 'company' is absent or equal null");
            if (employee.getPosition() == null)
                throw new NullPointerException("Field 'position' is absent or equal null");
            if (employee.getFullname() == null)
                throw new NullPointerException("Field 'fullname' is absent or equal null");
            employeeService.create(employee.getFullname(), employee.getPosition(), employee.getCompany());
            return new ResponseEntity(HttpStatus.OK);

        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> update(@RequestBody UpdateEmployee employee) {
        System.out.println(employee.getId());
        try {
            if (employee.getId() == null)
                throw new NullPointerException();
            employeeService.update(employee.getId(), employee.getFullname(), employee.getPosition(), employee.getCompany(), employee.getIsDeleted());
            return new ResponseEntity(HttpStatus.OK);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        if (employeeService.checkId(id)) {
            employeeService.remove(id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Such id is absent");
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getemployee(@PathVariable("id") Integer id) {

        Optional<Employee> res = employeeService.getById(id);

        if(res.isPresent()){
            return new ResponseEntity(res.get(), HttpStatus.OK);

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Such id is absent");
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Employee> getAllemployees() {
        return employeeService.getAll();
    }
}