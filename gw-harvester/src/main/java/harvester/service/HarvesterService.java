package harvester.service;


import edu.baylor.ecs.seer.common.context.SeerContext;

public interface HarvesterService {
    /**
     * Decides what procedures will be called and in which order
     */
    SeerContext collectContextData(SeerContext seerContext);

    /**
     * Calls local weaver on localhost
     */
    SeerContext collectDataFromLocal(SeerContext seerContext);

}
