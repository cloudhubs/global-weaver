package data.service;

import edu.baylor.ecs.seer.common.context.SeerContext;
import edu.baylor.ecs.seer.common.context.SeerEntityContext;
import edu.baylor.ecs.seer.common.context.SeerMsContext;
import edu.baylor.ecs.seer.common.entity.EntityModel;
import edu.baylor.ecs.seer.common.entity.SeerField;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContextMapService {

    private List<SeerField> seerFields;

    public SeerContext getContextMap(SeerContext seerContext){
        seerContext.setContextMap(iterateMsContexts(seerContext));
        return seerContext;
    }

    //get all ms entities in array

    private SeerEntityContext iterateMsContexts(SeerContext seerContext){
        List<EntityModel> finalEntities = new ArrayList<>();
        List<SeerMsContext> entityContexts = seerContext.getMsContexts();
        for (int i = 0; i < entityContexts.size(); i++) {
            for (int j = 0; j < entityContexts.size(); j++) {
                if (j != i){
                    List<EntityModel> entitiesI = entityContexts.get(i).getEntity().getEntities();
                    List<EntityModel> entitiesJ = entityContexts.get(j).getEntity().getEntities();
                    for (int k = 0; k < entitiesI.size(); k++) {
                        for (int m = 0; m < entitiesJ.size(); m++) {
                            if (entitySame(entitiesI.get(i), entitiesJ.get(j))){
                                EntityModel entityModel = mergeEntities(entitiesI.get(i));
                                if (entityModel == null) {
                                    finalEntities.add(entitiesI.get(i));
                                    finalEntities.add(entitiesJ.get(j));
                                }
                                finalEntities.add(entityModel);
                            }
                        }
                    }
                }
            }
        }
        SeerEntityContext seerEntityContext = new SeerEntityContext();
        seerEntityContext.setEntities(finalEntities);
        return seerEntityContext;
    }

    /**
     *
     * @param e1
     * @param e2
     * @return
     */
    private boolean entitySame(EntityModel e1, EntityModel e2){

        int e1FieldsSize = e1.getFields().size();
        int e2FieldsSize = e2.getFields().size();
        int sameFields = 0;
        this.seerFields = new ArrayList<>();

        for (int i = 0; i < e1.getFields().size(); i++){
            for (int j = 0; j < e2.getFields().size(); j++) {
                boolean isSame = fieldSame(e1.getFields().get(i), e2.getFields().get(j));
                if (isSame){
                    sameFields += 1;
                    seerFields.add(e1.getFields().get(i));
                    break;
                } else {
                    seerFields.add(e1.getFields().get(i));
                    seerFields.add(e2.getFields().get(j));
                }
            }
        }

        double match1 = sameFields / e1FieldsSize;
        double match2 = sameFields / e2FieldsSize;

        // more then 3 -> 80 % match
        if (e1FieldsSize > 3 && e2FieldsSize > 3){
            return match1 > 0.8 || match2 > 0.8;
        } else {
            // less then 3 -> 100 % match
            return match1 > 1 || match2 > 1;
        }

    }

    private boolean fieldSame(SeerField s1, SeerField s2){

        if (s1.isId() & s2.isId()){
            return false;
        }
        if (s1.getType().equals(s2.getType())){
            return true;
        }
        if (s2.getColumnName().equals(s2.getColumnName())){
            return true;
        }

        int actualMatch = 0;
        int maxMatch = 5;

        if (s1.isUpdatable() & s2.isUpdatable()){
            actualMatch += 1;
        }

        if (s1.isNotNull() & s2.isNotNull()){
            actualMatch += 1;
        }

        if (s1.getMin() == s2.getMin()){
            actualMatch += 1;
        }

        if (s2.getMax() == s2.getMax()){
            actualMatch += 1;
        }

        return (actualMatch / maxMatch) > 0.7;

    }

    private EntityModel mergeEntities(EntityModel e1){
        EntityModel newEntityModel = new EntityModel();
        newEntityModel.setFields(this.seerFields);
        newEntityModel.setClassName(e1.getClassName());
        newEntityModel.setClassNameShort(e1.getClassNameShort());
        return newEntityModel;
    }

}
