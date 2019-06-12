package harvester.controller;

import edu.baylor.ecs.seer.common.SampleObject;
import edu.baylor.ecs.seer.common.context.SeerContext;
import harvester.service.GlobalContextService;
import harvester.service.HarvesterService;
import harvester.service.LocalWeaverService;
import harvester.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HarvesterController {

    @Autowired
    private HarvesterService harvesterService;

    @Autowired
    private ResponseService responseService;

    @RequestMapping(path = "/handshake", method = RequestMethod.GET)
    public String home() {
        return "OK - handshake";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/analyze", method = RequestMethod.POST, produces = "application/json; charset=UTF-8", consumes = {"text/plain", "application/json;charset=UTF-8"})
    public SeerContext analyzeMicroservices(@RequestBody SampleObject sampleObject) {
        SeerContext seerContext = new SeerContext();
        seerContext.setRequest(sampleObject.getRequest());
        seerContext = harvesterService.harvestData(seerContext);
        seerContext = responseService.buildResponse(seerContext);
        return seerContext;
    }



}
