package harvester.service;

import edu.baylor.ecs.seer.common.context.SeerContext;
import edu.baylor.ecs.seer.common.context.SeerRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalWeaverService {

    @Autowired
    private RestService restService;

    public SeerContext getEntityModelDescription(SeerContext seerContext){
        SeerRequestContext req = seerContext.getRequest();
        seerContext = restService.postContext(req.getGwEntityHttp(), seerContext);
        return seerContext;
    }

    public SeerContext generateEntityModel(SeerContext seerContext){
        SeerRequestContext req = seerContext.getRequest();
        seerContext = restService.postContext(req.getGwGeneratorHttp(), seerContext);
        return seerContext;
    }

    public SeerContext generateFlowModel(SeerContext seerContext){
        SeerRequestContext req = seerContext.getRequest();
        seerContext = restService.postContext(req.getGwFlowHttp(), seerContext);
        return seerContext;
    }


}
