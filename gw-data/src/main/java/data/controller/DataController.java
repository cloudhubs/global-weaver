package data.controller;

import data.service.DataService;
import data.service.InconsistencyService;
import data.service.ValidationService;
import edu.baylor.ecs.seer.common.context.SeerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data")
public class DataController {

    private InconsistencyService inconsistencyService;
    private ValidationService validationService;
    private DataService dataService;

    @Autowired
    public DataController(InconsistencyService inconsistencyService,
                          ValidationService validationService,
                          DataService dataService) {
        this.inconsistencyService = inconsistencyService;
        this.validationService = validationService;
        this.dataService = dataService;
    }

//    @CrossOrigin(origins = "*")
//    @RequestMapping(path = "/inconsistencies", method = RequestMethod.GET)
//    public String findInconsistencies(){
//        return inconsistencyService.process();
//    }
//
//    @CrossOrigin(origins = "*")
//    @RequestMapping(path = "/validation", method = RequestMethod.GET)
//    public String findValidationPoints(){
//        return validationService.process();
//    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/entityModel", method = RequestMethod.POST)
    public SeerContext getEntityModel(@RequestBody SeerContext seerContext){
        return dataService.getContextSources(seerContext);
    }

}
