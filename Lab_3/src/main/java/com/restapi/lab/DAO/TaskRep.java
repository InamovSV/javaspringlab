package com.restapi.lab.DAO;

import com.restapi.lab.Models.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRep extends CrudRepository<Task,Integer> {
}
