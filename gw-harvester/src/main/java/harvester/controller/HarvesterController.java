package harvester.controller;

import edu.baylor.ecs.seer.common.domain.HarvesterData;
import edu.baylor.ecs.seer.common.domain.LocalWeaverResultType;
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
        System.out.println("[Harvester Service][Get security data. ");
        HarvesterData data = harvesterService.getData(LocalWeaverResultType.SECURITY);
        return data;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/dataModel", method = RequestMethod.GET)
    public HarvesterData getDataModel(){
        System.out.println("[Harvester Service][Get data model data. ");
        return harvesterService.getData(LocalWeaverResultType.DATA_MODEL);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/flowStructure", method = RequestMethod.GET)
    public HarvesterData getFlowStructure(){
        HarvesterData harvesterData = harvesterService.getData(LocalWeaverResultType.FLOW_STRUCTURE);
        return harvesterData;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/byteFlowStructure", method = RequestMethod.GET)
    public HarvesterData getByteFlowStructure(){
        HarvesterData harvesterData = harvesterService.getData(LocalWeaverResultType.BYTE_CODE_FLOW_STRUCTURE);
        return harvesterData;
    }
}
