package data.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.domain.EntityModel;
import data.domain.HarvesterData;
import data.domain.LocalWeaverResult;
import data.domain.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ValidationService extends DataService {

    @Autowired
    ValidationService self;

    public String process(){
        // Retrieve the harvest from all of the modules
        HarvesterData harvest = getModelData();

        // Create a set of every possible entity name along with their package
        Set<String> entityNames = new HashSet<>();
        for(EntityModel model : self.aggregateModelData(harvest.getData())){
            entityNames.add(model.getClassName());
        }

        // Retrieve the bytecode harvest
        harvest = self.getBytecodeData();

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
