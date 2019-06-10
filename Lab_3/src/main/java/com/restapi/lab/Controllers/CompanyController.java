package com.restapi.lab.Controllers;

import com.restapi.lab.DAO.CompanyRep;
import com.restapi.lab.Models.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="companies")
public class CompanyController {
    @Autowired
    private CompanyRep companyRep;

    @RequestMapping(method = RequestMethod.POST,produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestBody Company company){
        try{
            companyRep.save(company);
        }
        catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.PUT,produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> update(@PathVariable("id")Integer id, @RequestBody Company company){
        try{
            company.setId(id);
            companyRep.save(company);
        }
        catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        if (companyRep.findById(id).isPresent()) {
            companyRep.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Such id is absent");
        }
    }

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public @ResponseBody
    Optional<Company> getemployee(@PathVariable("id") Integer id) {
        return companyRep.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Company> getAllemployees() {
        return companyRep.findAll();
    }
}
