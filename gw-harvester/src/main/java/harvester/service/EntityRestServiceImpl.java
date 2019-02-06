package harvester.service;

import edu.baylor.ecs.seer.common.context.SeerContext;
import edu.baylor.ecs.seer.common.context.SeerRequestContext;
import org.springframework.stereotype.Service;

@Service
public class EntityRestServiceImpl extends RestService implements EntityAnalyzerService {
    @Override
    public SeerContext analyzeEntityConcern(SeerContext seerContext) {
        SeerRequestContext req = seerContext.getRequest();
        String path = req.getEntityAnalyzerInterface();
        return getContextResult(path);
    }
}
