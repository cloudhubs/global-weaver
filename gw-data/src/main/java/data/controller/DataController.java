package data.controller;

import data.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DataController {

    @Autowired
    DataService dataService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Data Service ] !";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/data", method = RequestMethod.GET)
    public String getAllContacts(){
        System.out.println("[Data Service][Find entity model inconsistencies]");
        return dataService.processModelData();
    }

}
