package harvester.service;

import edu.baylor.ecs.seer.common.context.SeerContext;

public interface RemoteSourceService {

    /**
     * Iteratively calls local weaver on local microservices
     * @param seerContext
     * @return
     */
    SeerContext collectDataFromRemoteSource(SeerContext seerContext);
}
