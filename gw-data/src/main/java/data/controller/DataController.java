package data.controller;

import data.service.EntityModelService;
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
    private EntityModelService entityModelService;

    @Autowired
    public DataController(InconsistencyService inconsistencyService,
                          ValidationService validationService,
                          EntityModelService entityModelService) {
        this.inconsistencyService = inconsistencyService;
        this.validationService = validationService;
        this.entityModelService = entityModelService;
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
        return entityModelService.buildUml(seerContext);
    }

}
