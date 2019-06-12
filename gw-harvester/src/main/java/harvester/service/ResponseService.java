package harvester.service;

import edu.baylor.ecs.seer.common.context.SeerContext;
import edu.baylor.ecs.seer.common.context.SeerMsContext;
import edu.baylor.ecs.seer.common.context.SeerResponseContext;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResponseService {

    private final static int SUCCESS = 200;

    public SeerContext buildResponse(SeerContext context){
        SeerResponseContext responseContext = new SeerResponseContext();
        responseContext.setModulesScanned(context.getMsContexts().size());
        responseContext.setRequestCompleted(new Timestamp(System.currentTimeMillis()));
        List<String> msNames = new ArrayList<>();
        for(SeerMsContext msContext : context.getMsContexts()){
            msNames.add(msContext.getModuleName());
        }
        responseContext.setMicroservicesNames(msNames);
        this.success(responseContext);
        context.setResponse(responseContext);

        return context;
    }

    private void success(SeerResponseContext responseContext){
        responseContext.setStatus(SUCCESS);
        responseContext.setMessage("Success");
    }
}
