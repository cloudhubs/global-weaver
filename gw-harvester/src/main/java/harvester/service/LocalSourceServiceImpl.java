package harvester.service;

import edu.baylor.ecs.seer.common.context.SeerContext;
import edu.baylor.ecs.seer.common.context.SeerRequestContext;
import edu.baylor.ecs.seer.lweaver.service.DataModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalSourceServiceImpl implements LocalSourceService{

    /**
     * Local weaver service
     */
    @Autowired
    private DataModelService dataModelService;

    @Override
    public SeerContext collectDataFromLocalSource(SeerContext seerContext) {
        SeerRequestContext req = seerContext.getRequest();
        String path = req.getPathToCompiledMicroservices();
        SeerContext entityModel = dataModelService.deriveStructure(path);

    }




}
