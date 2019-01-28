package harvester.controller;

import edu.baylor.ecs.seer.common.domain.HarvesterData;
import edu.baylor.ecs.seer.common.domain.LocalWeaverResultType;
import harvester.service.HarvesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class HarvesterController {

    @Autowired
    private HarvesterService harvesterService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(path = "/handshake", method = RequestMethod.GET)
    public String home() {
        return "OK - handshake";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/security", method = RequestMethod.GET)
    public HarvesterData getSecurity(){s
        return harvesterService.getData(LocalWeaverResultType.SECURITY);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/dataModel", method = RequestMethod.GET)
    public HarvesterData getDataModel(){
        return harvesterService.getData(LocalWeaverResultType.DATA_MODEL);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/flowStructure", method = RequestMethod.GET)
    public HarvesterData getFlowStructure(){
        return harvesterService.getData(LocalWeaverResultType.FLOW_STRUCTURE);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/byteFlowStructure", method = RequestMethod.GET)
    public HarvesterData getByteFlowStructure(){
        return harvesterService.getData(LocalWeaverResultType.BYTE_CODE_FLOW_STRUCTURE);
    }

    // Temp work -- should be improved later to support multiple endpoints
    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/scanDirectory/{path}", method = RequestMethod.GET)
    public String getSecurityLocalWeaver(@PathVariable String path) {
        return restTemplate.getForObject(
                "http://localhost:7002/local-weaver/flowStructure/" + path,
                String.class);
    }
}
