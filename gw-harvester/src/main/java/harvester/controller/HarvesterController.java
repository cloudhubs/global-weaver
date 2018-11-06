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
    private HarvesterService harvesterService;

    @RequestMapping(path = "/handshake", method = RequestMethod.GET)
    public String home() {
        return "OK - handshake";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/security", method = RequestMethod.GET)
    public HarvesterData getSecurity(){
        HarvesterData harvesterData = harvesterService.getData(LocalWeaverResultType.SECURITY.getResultType());
        return harvesterData;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/dataModel", method = RequestMethod.GET)
    public HarvesterData getDataModel(){
        HarvesterData harvesterData = harvesterService.getData(LocalWeaverResultType.DATA_MODEL.getResultType());
        return harvesterData;
    }


}
