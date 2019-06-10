package com.restapi.lab.DAO;


import com.restapi.lab.Models.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRep extends CrudRepository<Employee,Integer> {

}

