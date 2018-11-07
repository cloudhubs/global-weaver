package data.controller;

import data.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    DataService dataService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Data Service ] !";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/inconsistencies", method = RequestMethod.GET)
    public String findInconsistencies(){
        System.out.println("[Data Service][Find entity model inconsistencies]");
        return dataService.processModelDataInconsistencies();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/validation", method = RequestMethod.GET)
    public String findValidationPoints(){
        System.out.println("[Data Service][Find entity validation points]");
        return dataService.processModelDataValidation();
    }

}
