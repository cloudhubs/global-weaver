package harvester.service;

import edu.baylor.ecs.seer.common.context.SeerContext;

public interface LocalSourceService {
    /**
     * Calls local weaver service which scans particular folder on localhost
     * @param seerContext
     * @return
     */
    SeerContext collectDataFromLocalSource(SeerContext seerContext);
}
