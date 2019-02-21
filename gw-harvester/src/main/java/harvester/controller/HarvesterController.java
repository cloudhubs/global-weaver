package harvester.controller;

import edu.baylor.ecs.seer.common.context.SeerContext;
import harvester.service.GlobalContextService;
import harvester.service.LocalWeaverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HarvesterController {

    @Autowired
    private LocalWeaverService harvesterService;

    @Autowired
    private GlobalContextService globalContextService;

    @RequestMapping(path = "/handshake", method = RequestMethod.GET)
    public String home() {
        return "OK - handshake";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/analyze", method = RequestMethod.POST)
    public SeerContext analyzeMicroservices(@RequestBody SeerContext seerContext) {
        seerContext = harvesterService.getSeerContext(seerContext);
        seerContext = globalContextService.analyzeSeerContext(seerContext);
        return seerContext;
    }
}
