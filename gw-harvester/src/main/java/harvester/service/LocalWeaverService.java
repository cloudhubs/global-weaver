package harvester.service;

import edu.baylor.ecs.seer.common.context.SeerContext;
import edu.baylor.ecs.seer.common.context.SeerRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocalWeaverService {

    @Autowired
    private RestService restService;

    public SeerContext getSeerContext(SeerContext seerContext){
        SeerRequestContext req = seerContext.getRequest();
        String localPath = req.getPathToCompiledMicroservices();
        if (localPath != ""){
            RestTemplate restTemplate = new RestTemplate();
//            Quote quote = restTemplate.getForObject(
//                    req.getLocalWeaverHttp(), Quote.class);
            seerContext = restService.postContext(req.getLocalWeaverHttp(), seerContext);
        } else {
            //ToDo: remote call
            List<String> msPaths = new ArrayList<String>();
            for (String p: msPaths
                 ) {
//                SeerContext local = analyzeContext(p.getFirst(), p.getSecond());
//                List<SeerMsContext> seerMsContexts = seerContext.getMsContexts();
//                seerMsContexts.addAll(local.getMsContexts());
//                seerContext.setMsContexts(seerMsContexts);
            }
        }

        // Now analyze seer context
        // call entity analyzer
        // call security analyzer


        return seerContext;
    }

}
