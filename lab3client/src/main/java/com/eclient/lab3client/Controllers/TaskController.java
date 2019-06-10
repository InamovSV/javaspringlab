package com.eclient.lab3client.Controllers;

import com.eclient.lab3client.Models.Employee;
import com.eclient.lab3client.Models.Task;
import com.google.gson.Gson;
import com.netflix.client.http.HttpResponse;
import com.netflix.discovery.EurekaClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping(path = "tasks")
public class TaskController extends BaseController{

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestBody Object task) {

        ResponseEntity<?> resp = restTemplate().postForEntity(getCrudServiceUrl(), new HttpEntity(task), Object.class);

        return resp;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() throws ParseException {

        String url = getCrudServiceUrl();
        ResponseEntity<String> resp = restTemplate().getForEntity(url, String.class);
        JSONArray json = (JSONArray) new JSONParser().parse(resp.getBody());
        Gson g = new Gson();
        Task[] array = g.fromJson(resp.getBody(), Task[].class);
        ArrayList<Task> list = new ArrayList<>(Arrays.asList(array));
        return ResponseEntity.status(resp.getStatusCode()).body(list.stream().filter(x -> !x.getIsDeleted()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTasks(@PathVariable("id") Integer id) throws ParseException {

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
        ResponseEntity<String> resp = restTemplate().getForEntity(getCrudServiceUrl() + "/" + id, String.class);

        try{
            JSONObject json = (JSONObject) new JSONParser().parse(resp.getBody());

            json.put("id", id);
            json.put("isDeleted", true);

            restTemplate().put(getCrudServiceUrl(), json);
            ResponseEntity resp1 = restTemplate().exchange(
                    getCrudServiceUrl(),
                    HttpMethod.PUT,
                    new HttpEntity(json),
                    ResponseEntity.class
            );
//            System.out.println(resp1.toString());
            return resp1;
        } catch (Exception ex){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    String getCrudServiceUrl() {
        return eurekaClient.getNextServerFromEureka("crud_service", false).getHomePageUrl() + "/tasks";
    }
}
