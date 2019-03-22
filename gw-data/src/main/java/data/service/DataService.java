package data.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.baylor.ecs.seer.common.context.SeerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class DataService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BoundedContextService boundedContextService;

    @Autowired
    private ContextMapService contextMapService;

    public SeerContext getContextSources(SeerContext seerContext){
        seerContext = boundedContextService.getBoundedContextSources(seerContext);
//        return seerContext;
        return contextMapService.getContextMap(seerContext);
    }

    //ToDo: Move ip, port and interface (dataModel) to config
    /**
     * This method retrieves the Entity data for determining inconsistencies
     * @return HarvesterData for the Entity data
     */
//    public HarvesterData getModelData() {
//        return restTemplate.getForObject(
//                "http://localhost:7002/dataModel",
//                HarvesterData.class);
//    }
//
//    //ToDo: Move ip, port and interface (dataModel) to config
//    /**
//     * This method is an internal method to retrieve the bytecode flow graph for determining possible validation points
//     * @return HarvesterData for the bytecode flow graph
//     */
//    public HarvesterData getBytecodeData() {
//        return restTemplate.getForObject(
//                "http://localhost:7002/byteFlowStructure",
//                HarvesterData.class);
//    }

    /**
     * This method will parse all of the module's JSON into a list of EntityModel objects which will be aggregated into
     * a single list
     * @param data the list of results to aggreagte
     * @return a list of all entities from all modules
     */
//    public List<EntityModel> aggregateModelData(List<LocalWeaverResult> data){
//        // Setup return values
//        List<EntityModel> models = new ArrayList<>();
//
//        // Loop through all of the modules, extracting their LocalWeaverResult
//        for(LocalWeaverResult result : data){
//
//            // Parse the result's data (JSON string) into a List<EntityModel>
//            List<EntityModel> tempModels = null;
//            try{
//                tempModels = new ObjectMapper().readValue(result.getData(), new TypeReference<List<EntityModel>>(){});
//            } catch (Exception e){
//                System.out.println(e.toString());
//            }
//
//            // Check in case the parse was unsuccessful
//            if(tempModels != null){
//                // Aggregate the list into the global list to be returned
//                models.addAll(tempModels);
//            }
//
//        }
//        // Return the global list of EntityModel objects
//        return models;
//    }
//    //ToDo: Rename to something like "getEntitiesInconsistencies"
//    abstract String process();

}
