package data.service;

import edu.baylor.ecs.seer.common.context.SeerContext;
import edu.baylor.ecs.seer.common.context.SeerMsContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoundedContextService {

    @Autowired
    private EntityModelSourceService entityModelSourceService;

    @Autowired
    private BytecodeFlowSourceService bytecodeFlowSourceService;

    public SeerContext getBoundedContextSources(SeerContext seerContext){

        for (SeerMsContext ms: seerContext.getMsContexts()) {
            if (ms.getEntity().getEntities() != null && ms.getEntity().getEntities().size() > 0){
                String umlSource = entityModelSourceService.generateUmlSource(ms.getEntity().getEntities());
                ms.getEntity().setBoundedContextSource(umlSource);
            }

            if (ms.getFlow().getSeerFlowMethods() != null && ms.getFlow().getSeerFlowMethods().size() > 0){
                bytecodeFlowSourceService.buildAllFlowSource(ms);
            }
        }

        return seerContext;
    }
}
