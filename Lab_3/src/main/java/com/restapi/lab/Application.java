package com.restapi.lab;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;


@SpringBootApplication
@EnableEurekaClient
public class Application {

    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        java.lang.Class.forName("org.postgresql.Driver");
        SpringApplication.run(Application.class, args);
    }
}

@RefreshScope
@RestController
class ServiceInstanceRestController {

    @Autowired
    private Environment env;

    Properties getProperties() {
        Properties props = new Properties();
        CompositePropertySource bootstrapProperties = (CompositePropertySource)  ((AbstractEnvironment) env).getPropertySources().get("bootstrapProperties");
        for (String propertyName : bootstrapProperties.getPropertyNames()) {
            props.put(propertyName, bootstrapProperties.getProperty(propertyName));
        }

        return props;
    }

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${app.shared.attribute}")
    private String sharedAttribute;

    @Value("${app.service-name}")
    private String serviceName;

    @GetMapping("/service")
    public String getServiceName() {
        return "service name [" + this.serviceName + "]";
    }

    @GetMapping("/services")
    public ResponseEntity<?> getServiceNames() {
        getProperties().forEach((x, y) -> System.out.println(x + " - " + y));
        JSONObject jsonProps = new JSONObject(getProperties());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonProps.toString());
    }

    @GetMapping("/shared")
    public String getSharedAttribute() {
        return " application.yml [" + this.sharedAttribute + "]";
    }

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }


}
