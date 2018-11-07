package data.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private RestTemplate restTemplate;
    //ToDo: ip, port, interface to config
    @Override
    public HarvesterData getModelData() {
        return restTemplate.getForObject(
                "http://localhost:7002/data",
                HarvesterData.class);

    }
    //ToDo: add comments also to those nested for loops
    @Override
    public String processModelData() {
        String ret = "";
        HarvesterData harvest = getModelData();

        List<EntityModel> entityModels = aggregateModelData(harvest.getData());

        for(int i = 0; i < entityModels.size() - 1; i++){
            EntityModel primary = entityModels.get(i);
            for(int j = i; j < entityModels.size(); j++){
                EntityModel secondary = entityModels.get(j);

                if(primary.getSimpleClassName().equals(secondary.getSimpleClassName()) && !primary.getClassName().equals(secondary.getClassName())){
                    for(InstanceVariableModel primaryInstanceModel : primary.getInstanceVariables()){
                        List<Pair<String, String>> primaryAttributes = primaryInstanceModel.getAttributes();
                        for(InstanceVariableModel secondaryInstanceModel : secondary.getInstanceVariables()){
                            List<Pair<String, String>> secondaryAttributes = secondaryInstanceModel.getAttributes();

                            if(primaryInstanceModel.getVariableName().equals(secondaryInstanceModel.getVariableName())) {
                                for (Pair<String, String> primaryAttributePair : primaryAttributes) {
                                    for (Pair<String, String> secondaryAttributePair : secondaryAttributes) {
                                        if (primaryAttributePair.getFirst().equals(secondaryAttributePair.getFirst())) {
                                            if(!primaryAttributePair.getSecond().equals(secondaryAttributePair.getSecond())){
                                                ret = ret.concat(primary.getClassName() + "-"
                                                        + primaryInstanceModel.getVariableName() + "-"
                                                        + primaryAttributePair.getFirst() + ":"
                                                        + primaryAttributePair.getSecond() + " != "
                                                        + secondary.getClassName() + "-"
                                                        + secondaryInstanceModel.getVariableName() + "-"
                                                        + secondaryAttributePair.getFirst() + ":"
                                                        + secondaryAttributePair.getSecond());
                                                ret = ret.concat("\n");
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

        return ret;
    }

    //ToDo: comment
    private List<EntityModel> aggregateModelData(List<LocalWeaverResult> data){
        List<EntityModel> models = new ArrayList<>();
        for(LocalWeaverResult result : data){

            List<EntityModel> tempModels = null;
            try{
                tempModels = new ObjectMapper().readValue(result.getData(), new TypeReference<List<EntityModel>>(){});
            } catch (Exception e){
                System.out.println(e.toString());
            }

            if(tempModels != null){
                models.addAll(tempModels);
            }

        }
        return models;
    }

}
