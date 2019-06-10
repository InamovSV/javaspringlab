package com.eclient.lab3client.Controllers;

import com.eclient.lab3client.Models.Employee;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping(path = "employees")
public class EmployeeController extends BaseController{

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestBody Object employee) {

        ResponseEntity<?> resp = restTemplate().postForEntity(getCrudServiceUrl(), new HttpEntity(employee), Object.class);

        return resp;
    }

    @RequestMapping(method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> update(@RequestBody Object employee) {

        ResponseEntity<?> resp = restTemplate().postForEntity(getCrudServiceUrl(), new HttpEntity(employee), Object.class);

        return resp;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() throws ParseException {

        String url = getCrudServiceUrl();
        ResponseEntity<String> resp = restTemplate().getForEntity(url, String.class);
        JSONArray json = (JSONArray) new JSONParser().parse(resp.getBody());
        Gson g = new Gson();
        Employee[] array = g.fromJson(resp.getBody(), Employee[].class);
        ArrayList<Employee> list = new ArrayList<>(Arrays.asList(array));
        return ResponseEntity.status(resp.getStatusCode()).body(list.stream().filter(x -> !x.getIsDeleted()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getemployee(@PathVariable("id") Integer id) throws ParseException {

        ResponseEntity<String> resp = restTemplate().getForEntity(getCrudServiceUrl() + "/" + id, String.class);

        JSONObject json = (JSONObject) new JSONParser().parse(resp.getBody());

        boolean isDeleted = (boolean)json.get("isDeleted");

        System.out.println(isDeleted);
        if(!isDeleted){
            return ResponseEntity.status(resp.getStatusCode()).body(resp.getBody());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        System.out.println(id);
        ResponseEntity<String> resp = restTemplate().getForEntity(getCrudServiceUrl() + "/" + id, String.class);

        try{
            JSONObject json = (JSONObject) new JSONParser().parse(resp.getBody());

            json.put("id", id);
            json.put("isDeleted", true);

            ResponseEntity resp1 = restTemplate().exchange(
                    getCrudServiceUrl(),
                    HttpMethod.PUT,
                    new HttpEntity(json),
                    ResponseEntity.class
            );
            System.out.println(resp1.toString());
            return resp1;
        } catch (Exception ex){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    String getCrudServiceUrl() {
        return eurekaClient.getNextServerFromEureka("crud_service", false).getHomePageUrl() + "/employees";
    }
}
