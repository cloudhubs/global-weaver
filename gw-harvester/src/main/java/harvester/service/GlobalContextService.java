package harvester.service;

import edu.baylor.ecs.seer.common.context.SeerContext;
import harvester.builder.GlobalBuilder;
import harvester.builder.GlobalDirector;
import org.springframework.stereotype.Service;

@Service
public class GlobalContextService {

    public SeerContext analyzeSeerContext(SeerContext seerContext){
        GlobalDirector globalDirector = new GlobalDirector();
        globalDirector.build(new GlobalBuilder(), seerContext);
        return seerContext;
    }

}
