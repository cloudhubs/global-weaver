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

    /**
     * Calls local weaver on remote server
     */
    SeerContext collectDataFromRemote(SeerContext seerContext);

    /**
     * Calls entity analyzer
     */
    SeerContext collectDataFromEntityAnalyzer(SeerContext seerContext);

    /**
     * Calls security analyzer
     */
    SeerContext collectDataFromSecurityAnalyzer(SeerContext seerContext);

}
