package com.restapi.lab.Services;

import com.restapi.lab.DAO.EmployeeRep;
import com.restapi.lab.DAO.TaskRep;
import com.restapi.lab.Models.Employee;
import com.restapi.lab.Models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRep employeeRep;

    @Autowired
    public EmployeeService(EmployeeRep employeeRep) {
        this.employeeRep = employeeRep;

    }

    public Boolean checkId(int id){
        return employeeRep.findById(id).isPresent();
    }

    public Iterable<Employee> getAll(){
        return employeeRep.findAll();
    }

    public Optional<Employee> getById(int id){
        return employeeRep.findById(id);
    }

    public void create(String fullname, String position, String company){
        Employee res = new Employee(fullname, position);
        res.setCompany(company);
        employeeRep.save(res);
    }



    public void update(int id,
                       String fullname,
                       String position,
                       String company,
                       Boolean isDeleted){
        Optional<Employee> res = employeeRep.findById(id);

        if(res.isPresent()){
            if(fullname != null)
                res.get().setFullname(fullname);
            if(position != null)
                res.get().setPosition(position);
            if(company != null)
                res.get().setCompany(company);
            if(isDeleted != null)
                res.get().setIsDeleted(isDeleted);
            employeeRep.save(res.get());
        } else {
            throw new NullPointerException("There's no such id");
        }

    }

    public void remove(int id){
        employeeRep.deleteById(id);
    }
}
