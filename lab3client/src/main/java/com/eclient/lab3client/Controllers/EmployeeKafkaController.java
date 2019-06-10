package com.eclient.lab3client.Controllers;

import com.eclient.lab3client.Models.Requests.CreateEmployee;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "kafka/employees")
public class EmployeeKafkaController {
    @Value("${app.createEmployeetopic}")
    private String createEmployeetopic;

    @Value("${app.updateEmployeetopic}")
    private String updateEmployeetopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestBody Object employee) {
        kafkaTemplate.send(createEmployeetopic, employee.toString());

        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> update(@RequestBody Object employee) {
        kafkaTemplate.send(updateEmployeetopic, employee.toString());
        return ResponseEntity.ok().build();
    }
}
