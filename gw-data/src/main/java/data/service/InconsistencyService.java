package data.service;

import data.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InconsistencyService extends DataService {

    @Autowired
    InconsistencyService self;

    /**
     * This method will find all of the inconsistencies with attributes on instance fields in Entity objects
     * @return A warning message denoting all of the inconsistencies
     */
    public String process() {
        // Setup return value
        String ret = "";

        // Get the harvest from the local-weavers
        HarvesterData harvest = self.getModelData();

        // Aggregate the EntityModels from all of the modules
        List<EntityModel> entityModels = self.aggregateModelData(harvest.getData());

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

}
