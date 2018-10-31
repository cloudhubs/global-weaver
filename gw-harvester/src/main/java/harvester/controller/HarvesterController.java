package harvester.controller;

import harvester.domain.HarvesterData;
import harvester.service.HarvesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return harvesterService.getSecurityData();
    }

}
