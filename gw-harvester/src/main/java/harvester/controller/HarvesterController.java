package harvester.controller;

import harvester.domain.HarvesterData;
import harvester.domain.LocalWeaverResultType;
import harvester.service.HarvesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class HarvesterController {

    @Autowired
    HarvesterService harvesterService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Harvester Service ] !";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/security", method = RequestMethod.GET)
    public HarvesterData getSecurity(){
        System.out.println("[Harvester Service][Get security data. ");
        return harvesterService.getData(LocalWeaverResultType.SECURITY.getResultType());
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/dataModel", method = RequestMethod.GET)
    public HarvesterData getDataModel(){
        System.out.println("[Harvester Service][Get data model data. ");
        return harvesterService.getData(LocalWeaverResultType.DATA_MODEL.getResultType());
    }


}
