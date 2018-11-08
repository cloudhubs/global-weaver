package data.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class DataServiceImpl implements DataService {

    // TODO -
    // URL should probably be in config
    // Break into two services
    // Look at making it into constructor injection

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DataServiceImpl dataService;

    /**
     * This method retrieves the Entity data for determining inconsistencies
     * @return HarvesterData for the Entity data
     */
    @Override
    public HarvesterData getModelData() {
        return restTemplate.getForObject(
                "http://localhost:7003/dataModel",
                HarvesterData.class);
    }

    /**
     * This method will find all of the inconsistencies with attributes on instance fields in Entity objects
     * @return A warning message denoting all of the inconsistencies
     */
    @Override
    public String processModelDataInconsistencies() {
        // Setup return value
        String ret = "";

        // Get the harvest from the local-weavers
        HarvesterData harvest = dataService.getModelData();

        // Aggregate the EntityModels from all of the modules
        List<EntityModel> entityModels = dataService.aggregateModelData(harvest.getData());

        // Standard loop to check each EntityModel with every other EntityModel
        for (int i = 0; i < entityModels.size() - 1; i++) {
            // Retrieve the primary EntityModel
            EntityModel primary = entityModels.get(i);

            // Loop through the rest of the Entity Models
            for (int j = i; j < entityModels.size(); j++) {

                // Retrieve the secondary EntityModel
                EntityModel secondary = entityModels.get(j);

                // Check to see that the simple class names are equal but the full class names aren't. Example:
                //      ClassA = ClassA
                //      packageA.ClassA != packageB.ClassA
                // This is to ensure you aren't comparing the EXACT same object
                if (primary.getSimpleClassName().equals(secondary.getSimpleClassName()) && !primary.getClassName().equals(secondary.getClassName())) {

                    // Loop through all of the primary EntityModel's instance fields
                    for (InstanceVariableModel primaryInstanceModel : primary.getInstanceVariables()) {

                        // For each instance field, retrieve the primary EntityModel's attribute list
                        List<Pair<String, String>> primaryAttributes = primaryInstanceModel.getAttributes();

                        // Loop through all of the secondary EntityModel's instance fields
                        for (InstanceVariableModel secondaryInstanceModel : secondary.getInstanceVariables()) {

                            // For each instance field, retrieve the secondary EntityModel's attribute list
                            List<Pair<String, String>> secondaryAttributes = secondaryInstanceModel.getAttributes();

                            // If they are the same name, then we need to compare their attributes
                            if (primaryInstanceModel.getVariableName().equals(secondaryInstanceModel.getVariableName())) {

                                // Loop through the primary instance field's attributes
                                for (Pair<String, String> primaryAttributePair : primaryAttributes) {

                                    // Loop through the secondat instance field's attributes
                                    for (Pair<String, String> secondaryAttributePair : secondaryAttributes) {

                                        // Comparing the key to see if they are the same attribute
                                        if (primaryAttributePair.getFirst().equals(secondaryAttributePair.getFirst())) {

                                            // If they have different values for the same key
                                            if (!primaryAttributePair.getSecond().equals(secondaryAttributePair.getSecond())) {

                                                // Add on a warning message to the return value
                                                ret = ret.concat(primary.getClassName() + "-"
                                                        + primaryInstanceModel.getVariableName() + "-"
                                                        + primaryAttributePair.getFirst() + ":"
                                                        + primaryAttributePair.getSecond() + " != "
                                                        + secondary.getClassName() + "-"
                                                        + secondaryInstanceModel.getVariableName() + "-"
                                                        + secondaryAttributePair.getFirst() + ":"
                                                        + secondaryAttributePair.getSecond());
                                                ret = ret.concat("\n");

                                                // Print some debugging info
                                                System.out.println(primaryAttributePair.getFirst());
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

        // Return the error message with all of the attribute warnings
        return ret;
    }

    /**
     * This method will parse all of the module's JSON into a list of EntityModel objects which will be aggregated into
     * a single list
     * @param data the list of results to aggreagte
     * @return a list of all entities from all modules
     */
    private List<EntityModel> aggregateModelData(List<LocalWeaverResult> data){
        // Setup return values
        List<EntityModel> models = new ArrayList<>();

        // Loop through all of the modules, extracting their LocalWeaverResult
        for(LocalWeaverResult result : data){

            // Parse the result's data (JSON string) into a List<EntityModel>
            List<EntityModel> tempModels = null;
            try{
                tempModels = new ObjectMapper().readValue(result.getData(), new TypeReference<List<EntityModel>>(){});
            } catch (Exception e){
                System.out.println(e.toString());
            }

            // Check in case the parse was unsuccessful
            if(tempModels != null){
                // Aggregate the list into the global list to be returned
                models.addAll(tempModels);
            }

        }
        // Return the global list of EntityModel objects
        return models;
    }

    /**
     * This method is an internal method to retrieve the bytecode flow graph for determining possible validation points
     * @return HarvesterData for the bytecode flow graph
     */
    private HarvesterData getBytecodeData() {
        return restTemplate.getForObject(
                "http://localhost:7003/byteFlowStructure",
                HarvesterData.class);
    }

    /**
     * This method finds possible validation points of Entity objects
     * @return A string representing all of the possible validation points
     */
    @Override
    public String processModelDataValidation() {
        // Retrieve the harvest from all of the modules
        HarvesterData harvest = getModelData();

        // Create a set of every possible entity name along with their package
        Set<String> entityNames = new HashSet<>();
        for(EntityModel model : dataService.aggregateModelData(harvest.getData())){
            entityNames.add(model.getClassName());
        }

        // Retrieve the bytecode harvest
        harvest = dataService.getBytecodeData();

        // Loop through each module of the bytecode harvest
        for(LocalWeaverResult localWeaverResult : harvest.getData()){

            // Retrieve the JSON from the module
            String inJSON = localWeaverResult.getData();

            // Convert into a list of methods (HashMap of Integer to Node)
            List<Map<Integer, Node>> methods = null;
            try {
                methods = new ObjectMapper().readValue(inJSON, new TypeReference<List<Map<Integer, Node>>>() {});
            } catch (Exception e){
                System.out.println(e.toString());
            }

            // Check to make sure the parse was successful
            if(methods != null){
                // Loop through every method and look at the bytecode
                for(Map<Integer, Node> nodes : methods) {
                    // Loop through the keyset to access every node in the map
                    for (Integer key : nodes.keySet()) {

                        // Retrieve the node
                        Node n = nodes.get(key);

                        // If it is a method node then it is eligible to be a validation point
                        if (n.getType().equals("method")) {
                            // Get the raw bytecode
                            String raw = n.getRaw();

                            // Split to extract out the method name
                            String[] exploded = raw.split(" ");

                            // Error checking against some method bytecode that isn't formatted normally
                            if (exploded.length >= 6) {
                                // Get the method definition out of the raw bytecode
                                String methodDef = exploded[5];
                                // Find out where the parameter definition starts
                                int index = methodDef.indexOf("(");

                                // Error checking against some method bytecode that isn't formatted normally
                                if (index != -1) {
                                    // Extract out just the package and method name
                                    methodDef = methodDef.substring(0, index);
                                }

                                // Extract out the class of the method call
                                String className = methodDef.substring(0, methodDef.lastIndexOf("."));

                                // Check against every possible Entity object
                                for (String entityName : entityNames) {

                                    // If it matches an Entity then it is a possible valdiation point for that Entity
                                    if (entityName.equals(className)) {
                                        System.out.println(methodDef);
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }

        // Return a message denoting validation points
        return "TODO";
    }

}
