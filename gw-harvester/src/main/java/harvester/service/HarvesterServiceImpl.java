package harvester.service;

import edu.baylor.ecs.seer.common.context.SeerContext;
import edu.baylor.ecs.seer.common.context.SeerRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HarvesterServiceImpl implements HarvesterService {

    @Autowired
    private RemoteSourceService remoteSourceService;

    @Autowired
    private LocalSourceService localSourceService;



    @Override
    public SeerContext collectContextData(SeerContext seerContext) {

        SeerRequestContext req = seerContext.getRequest();

        if (req.isUseRemote()){
            seerContext = this.collectDataFromRemote(seerContext);
            seerContext = this.collectDataFromEntityAnalyzer(seerContext);
        } else {
            seerContext = this.collectDataFromLocal(seerContext);
        }



        return this.collectDataFromSecurityAnalyzer(seerContext);

    }

    @Override
    public SeerContext collectDataFromLocal(SeerContext seerContext) {
        return localSourceService.collectDataFromLocalSource(seerContext);
    }

    @Override
    public SeerContext collectDataFromRemote(SeerContext seerContext) {
        return remoteSourceService.collectDataFromRemoteSource(seerContext);
    }



}
