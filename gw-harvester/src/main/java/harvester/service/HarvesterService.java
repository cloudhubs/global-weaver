package harvester.service;

import edu.baylor.ecs.seer.common.context.SeerContext;
import edu.baylor.ecs.seer.common.context.SeerMsContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class HarvesterService {

    @Autowired
    private LocalWeaverService harvesterService;

    @Autowired
    private GlobalContextService globalContextService;

    @Autowired
    private GlobalWeaverService globalWeaverService;

    public SeerContext harvestData(SeerContext seerContext){
        seerContext = harvesterService.getSeerContext(seerContext);
        seerContext = globalContextService.analyzeSeerContext(seerContext);
        seerContext = globalWeaverService.getEntityModelDescription(seerContext);
        seerContext = globalWeaverService.generateEntityModel(seerContext);
        seerContext = globalWeaverService.generateFlowModel(seerContext);

        boolean isWindows = System.getProperty("os.name")
                .toLowerCase().startsWith("windows");
        if(!isWindows) {
            openUmlModel(seerContext);
        }

        return seerContext;
    }

    public void openUmlModel(SeerContext seerContext){
        String command = "open " + seerContext.getGlobal().getUmlDiagramResult();
        makeCommand(command);
        for (SeerMsContext ms: seerContext.getMsContexts()){
            command = "open " + ms.getEntity().getBoundedContextResult();
            makeCommand(command);
        }
    }

    private void makeCommand(String command){
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
