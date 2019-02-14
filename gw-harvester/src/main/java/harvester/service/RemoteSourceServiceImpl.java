package harvester.service;

import edu.baylor.ecs.seer.common.Pair;
import edu.baylor.ecs.seer.common.context.SeerContext;
import edu.baylor.ecs.seer.common.context.SeerMsContext;
import edu.baylor.ecs.seer.common.context.SeerRequestContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemoteSourceServiceImpl extends RestService implements RemoteSourceService {

    @Override
    public SeerContext collectDataFromRemoteSource(SeerContext seerContext) {

        SeerRequestContext req = seerContext.getRequest();
        List<Pair<String, Integer>> portsToMicroservices = req.getPortsToMicroservices();
        for (Pair<String, Integer> p: portsToMicroservices
             ) {
            SeerContext local = analyzeContext(p.getFirst(), p.getSecond());
            List<SeerMsContext> seerMsContexts = seerContext.getMsContexts();
            seerMsContexts.addAll(local.getMsContexts());
            seerContext.setMsContexts(seerMsContexts);
        }
        return seerContext;

    }

}
