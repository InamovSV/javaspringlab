package com.eclient.lab3client.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "kafka/tasks")
public class TaskKafkaController {
    @Value("${app.createTaskTopic}")
    private String createEmployeetopic;

    @Value("${app.updateTaskTopic}")
    private String updateEmployeetopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestBody Object task) {
        kafkaTemplate.send(createEmployeetopic, task.toString());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> update(@RequestBody Object task) {
        kafkaTemplate.send(updateEmployeetopic, task.toString());
        return ResponseEntity.ok().build();
    }
}
