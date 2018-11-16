package data.controller;

import data.service.DataService;
import data.service.InconsistencyService;
import data.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//ToDo: Short description of endpoints
@RestController
@RequestMapping("/data")
public class DataController {

    private InconsistencyService inconsistencyService;
    private ValidationService validationService;

    @Autowired
    public DataController(InconsistencyService inconsistencyService, ValidationService validationService) {
        this.inconsistencyService = inconsistencyService;
        this.validationService = validationService;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/inconsistencies", method = RequestMethod.GET)
    public String findInconsistencies(){
        return inconsistencyService.process();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/validation", method = RequestMethod.GET)
    public String findValidationPoints(){
        return validationService.process();
    }

}
