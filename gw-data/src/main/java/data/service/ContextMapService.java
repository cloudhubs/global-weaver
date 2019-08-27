package data.service;

import edu.baylor.ecs.seer.common.context.SeerContext;
import edu.baylor.ecs.seer.common.context.SeerEntityCluster;
import edu.baylor.ecs.seer.common.context.SeerEntityContext;
import edu.baylor.ecs.seer.common.context.SeerMsContext;
import edu.baylor.ecs.seer.common.entity.EntityModel;
import edu.baylor.ecs.seer.common.entity.SeerField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContextMapService {

    @Autowired
    private EntityModelSourceService entityModelSourceService;

    private List<SeerField> seerFields;

    List<EntityModel> allEntities = new ArrayList<>();

    SeerEntityCluster tmpSeerEntityCluster = new SeerEntityCluster();

    List<SeerEntityCluster> seerEntityClusters = new ArrayList<>();

    // MAIN METHOD
    public SeerContext getContextMap(SeerContext seerContext){
        this.allEntities = new ArrayList<>();
        seerContext = electClosestNeighbour(seerContext);
        List<SeerEntityCluster> clusters = clusterEntities(seerContext);
        seerContext.setContextMap(mergeClusters(clusters));
        String umlSource = entityModelSourceService.generateUmlSource(seerContext.getContextMap().getEntities());
        seerContext.getContextMap().setBoundedContextSource(umlSource);
//        seerContext.getContextMap().setEntities(null);
        seerContext = optimizeForJackson(seerContext);
        return seerContext;
    }

    private SeerContext optimizeForJackson(SeerContext seerContext) {
        for (int i = 0; i < seerContext.getContextMap().getEntities().size(); i++){
            seerContext.getContextMap().getEntities().get(i).setAdjEntityModels(null);
        }
        for (int i = 0; i < seerContext.getMsContexts().size(); i++) {
            for (int j = 0; j < seerContext.getMsContexts().get(i).getEntity().getEntities().size(); j++) {
                seerContext.getMsContexts().get(i).getEntity().getEntities().get(j).setAdjEntityModels(null);
            }
        }
        return seerContext;
    }

    // 3. MERGE CLUSTERS
    private SeerEntityContext mergeClusters(List<SeerEntityCluster> entityClusters) {
        List<EntityModel> finalEntities = new ArrayList<>();
        for (int i = 0; i < entityClusters.size(); i++) {
            finalEntities.add(mergeEntities(entityClusters.get(i).getEntityModels()));
        }
        SeerEntityContext seerEntityContext = new SeerEntityContext();
        seerEntityContext.setEntities(finalEntities);
        return seerEntityContext;
    }

    private EntityModel mergeEntities(List<EntityModel> entityModels) {
        EntityModel finalEntity = entityModels.get(0);
        if (entityModels.size() > 1){
            // all other entity models to be merged
            for (int i = 0; i < entityModels.size() - 1; i++){

                List<SeerField> seerFields = entityModels.get(i + 1).getFields();
                //List<SeerField> fieldsToAdd = new ArrayList<>();
                // fields of entity model
                for (int j = 0; j < seerFields.size(); j++){
                    // base entity seer fields

                    for (int k = 0; k < finalEntity.getFields().size(); k++){
                        System.out.println("Are same: " + finalEntity.getFields().get(k).getName() + " " + seerFields.get(j).getName());
                        boolean same = fieldSame(finalEntity.getFields().get(k), seerFields.get(j));
                        if (same) {
                            seerFields.get(j).setPaired(true);
                            break;
                            //finalEntity.getFields().add(seerFields.get(j));
                            //fieldsToAdd.add(seerFields.get(j));
                        }
                    }
                }
                for (int j = 0; j < seerFields.size(); j++){
                    if (!seerFields.get(j).isPaired()) {
                        finalEntity.getFields().add(seerFields.get(j));
                    }
                }
//                finalEntity.getFields().addAll(fieldsToAdd);
            }
        }
        return finalEntity;
    }

    // 2. CLUSTER ENTITIES
    private List<SeerEntityCluster> clusterEntities(SeerContext seerContext) {

        List<SeerMsContext> entityContexts = seerContext.getMsContexts();

        // put all entities from ms context to one place
        for (int i = 0; i < entityContexts.size(); i++) {
            List<EntityModel> entities = entityContexts.get(i).getEntity().getEntities();
            for (int j = 0; j < entities.size(); j++) {
                //if (entities.get(j).isMerged()){
                    allEntities.add(entities.get(j));
                //}
            }
        }

        // find connected components
        for(int i = 0; i < allEntities.size(); i++) {
            if(!allEntities.get(i).isVisited()) {
                allEntities.get(i).setVisited(true);
                List<EntityModel> merged = new ArrayList<>();
                DFSUtil(i);
                // cluster is populated by DFSUtil, so we can push
                // the tmp cluster to all clusters and
                // erase the content of the tmp cluster
                seerEntityClusters.add(new SeerEntityCluster(this.tmpSeerEntityCluster.getEntityModels()));
                this.tmpSeerEntityCluster = new SeerEntityCluster();
            }
        }

        return seerEntityClusters;

    }

    private void DFSUtil(int i) {
        if(this.tmpSeerEntityCluster.getEntityModels() == null){
            this.tmpSeerEntityCluster.setEntityModels(new ArrayList<>());
        }

        this.allEntities.get(i).setVisited(true);
        this.tmpSeerEntityCluster.getEntityModels().add(this.allEntities.get(i));
        for (EntityModel entityModel : allEntities.get(i).getAdjEntityModels()) {
            i = getIndexFromAllFields(entityModel);
            if (i != -1 && !allEntities.get(i).isVisited()){
                DFSUtil(i);
            }
        }
    }

    private int getIndexFromAllFields(EntityModel entityModel) {
        for (int i = 0; i < this.allEntities.size(); i++){
            if (this.allEntities.get(i).getModuleName().equals(entityModel.getModuleName()) &&
            this.allEntities.get(i).getClassName().equals(entityModel.getClassName())) {
                return i;
            }
        }
        return -1;
    }


    // 1. ELECTION
    private SeerContext electClosestNeighbour(SeerContext seerContext){
        List<SeerMsContext> entityContexts = seerContext.getMsContexts();
        for (int i = 0; i < entityContexts.size(); i++) {
            for (int j = 0; j < entityContexts.size(); j++) {
                if (j != i){
                    List<EntityModel> entitiesI = entityContexts.get(i).getEntity().getEntities();
                    List<EntityModel> entitiesJ = entityContexts.get(j).getEntity().getEntities();
                    for (int k = 0; k < entitiesI.size(); k++) {
                        for (int m = 0; m < entitiesJ.size(); m++) {
                            if (entitySame(entitiesI.get(k), entitiesJ.get(m))){
                                setCandidates(entitiesI.get(k), entitiesJ.get(m));
                            }
                        }
                    }
                }
            }
        }
        return seerContext;
    }

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
//                    break;
                } else {
                    seerFields.add(e1.getFields().get(i));
                    seerFields.add(e2.getFields().get(j));
                }
            }
        }

        double match1 = (double) e1FieldsSize / sameFields;
        double match2 = (double) e2FieldsSize / sameFields;

        // more then 3 -> 80 % match
        if (e1FieldsSize > 3 && e2FieldsSize > 3){
            return match1 > 0.8 && match2 > 0.8;
        } else {
            // less then 3 -> 100 % match
            return match1 > 0.9 && match2 > 0.9;
        }

    }

    private boolean fieldSame(SeerField s1, SeerField s2){

        if (s1.isId() & s2.isId()){
            return false;
        }
        if (s1.getType().equals(s2.getType())){
            return true;
        }

        int actualMatch = 0;
        int maxMatch = 5;

        if (s1.getColumnName() != null && s2.getColumnName() != null & s2.getColumnName().equals(s2.getColumnName())){
            actualMatch += 1;
        }

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

    private void setCandidates(EntityModel src, EntityModel dest) {
        if (!src.getAdjEntityModels().contains(dest)){
            src.setAdjEntityModel(dest);
        }
        if (!dest.getAdjEntityModels().contains(src)){
            dest.setAdjEntityModel(src);
        }

    }

    /**
     * Deprecated
     * @param seerContext
     * @return
     */
    private List<EntityModel> mergeAllEntities(SeerContext seerContext) {
        List<EntityModel> finalEntities = new ArrayList<>();
        List<EntityModel> tmpEntities = new ArrayList<>();
        List<SeerMsContext> entityContexts = seerContext.getMsContexts();
        for (int i = 0; i < entityContexts.size(); i++) {
            tmpEntities.addAll(entityContexts.get(i).getEntity().getEntities());
        }
        for (int i = 0; i < tmpEntities.size(); i++) {

        }

        return finalEntities;

    }

    /**
     * Deprecated
     * @param e1
     * @return
     */
    private EntityModel mergeEntity(EntityModel e1){
        EntityModel newEntityModel = new EntityModel();
        newEntityModel.setFields(this.seerFields);
        newEntityModel.setClassName(e1.getClassName());
        newEntityModel.setClassNameShort(e1.getClassNameShort());
        return newEntityModel;
    }

}
