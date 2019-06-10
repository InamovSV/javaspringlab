package com.restapi.lab.DAO;

import com.restapi.lab.Models.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRep extends CrudRepository<Company,Integer> {
}
