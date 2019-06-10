package com.eclient.lab3client.Controllers;

import com.eclient.lab3client.Models.Requests.CreateEmployee;
import com.netflix.discovery.EurekaClient;
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@RefreshScope
public abstract class BaseController {
    @Autowired
    @Qualifier("eurekaClient")
    protected EurekaClient eurekaClient;

    @Bean
    protected RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Value("${brokers}")
//    private String brokers;
//
//    @Value("${value-deserializer}")
//    private Object valueDeserializer;
//
//    private Properties props;
//
//    private KafkaProducer<String, CreateEmployee> employeeProducer;

//    public BaseController(){
//        props = new Properties();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueDeserializer);
//
//        employeeProducer = new KafkaProducer<String, CreateEmployee>(props);
//        employeeProducer.send(new ProducerRecord<String, CreateEmployee>("", ));
//    }
}
