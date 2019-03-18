package data.service;

import edu.baylor.ecs.seer.common.Pair;
import edu.baylor.ecs.seer.common.context.SeerContext;
import edu.baylor.ecs.seer.common.context.SeerMsContext;
import edu.baylor.ecs.seer.common.entity.EntityModel;
import edu.baylor.ecs.seer.common.entity.SeerField;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates source code for entity model
 */
@Service
public class EntityModelSourceService {

    private StringBuilder stringBuilder;

    /**
     * Deprecated
     * @param seerContext
     * @return
     */
    public SeerContext buildAllUml(SeerContext seerContext){
        //global UML
        //-- run merging algorithm

        // local UML
        for (SeerMsContext ms: seerContext.getMsContexts()
             ) {
            if (ms.getEntity().getEntities() != null && ms.getEntity().getEntities().size() > 0){
                start();
                body(ms.getEntity().getEntities());
                end();
                ms.getEntity().setBoundedContextSource(stringBuilder.toString());
            }
        }

        return seerContext;
    }

    public String generateUmlSource(List<EntityModel> entities) {
        start();
        body(entities);
        end();
        return stringBuilder.toString();
    }


    private void start(){
        stringBuilder = new StringBuilder();
        stringBuilder.append("digraph G {\n" +
                "        fontname = \"Bitstream Vera Sans\"\n" +
                "        fontsize = 8\n" +
                " \n" +
                "        node [\n" +
                "                fontname = \"Bitstream Vera Sans\"\n" +
                "                fontsize = 8\n" +
                "                shape = \"record\"\n" +
                "        ]\n" +
                " \n" +
                "        edge [\n" +
                "                fontname = \"Bitstream Vera Sans\"\n" +
                "                fontsize = 8\n" +
                "        ]" +
                " \n"+
                " \n"
        );
    }

    /**
     * edge [
     *                 arrowhead = "none"
     *
     *                 headlabel = "0..*"
     *                 taillabel = "0..*"
     *         ]
     * @param listEntitiesModel
     */
    private void body(List<EntityModel> listEntitiesModel){

        for (EntityModel em: listEntitiesModel
             ) {
            generateEntity(em);
        }

        stringBuilder.append("" +
//                        "Team [\n" +
//                "                label = \"{Team|+ name : string\\l+ code : int\\l|+ die() : void\\l}\"\n" +
//                "        ]\n" +
//                " \n" +
//                "        subgraph clusterContestImpl {\n" +
//                "                label = \"Package contest.impl\"\n" +
//                " \n" +
//                "                Member [\n" +
//                "                        label = \"{Member||+ login() : void\\l}\"\n" +
//                "                ]\n" +
//                " \n" +
//                "                Contest [\n" +
//                "                        label = \"{Contest||+ start() : void\\l}\"\n" +
//                "                ]\n" +
//                "        }\n" +
                " \n" +
                "        edge [\n" +
                "                arrowhead = \"none\"\n" +
                "                 headlabel = \"0..*\" " +
                "                 taillabel = \"0..*\" " +
                "        ]\n" +
                " \n" +
               // "        Member -> Team\n" +
               // "        Contest -> Team\n" +
                " \n" +
              //  "        edge [\n" +
              //  "                arrowhead = \"none\"\n" +
              //  " \n" +
              //  "                headlabel = \"0..*\"\n" +
              //  "                taillabel = \"0..*\"\n" +
              //  "        ]\n" +
                " \n" //+
               // "        Member -> Contest"
        );

        for (EntityModel em: listEntitiesModel
        ) {
            generateEdge(em);
        }

    }

    private void end(){
        stringBuilder.append("}");
    }

    private void generateEntity(EntityModel entityModel){
        stringBuilder.append(entityModel.getClassNameShort());
        stringBuilder.append("[\n" +
                " label = \"{");
        stringBuilder.append(entityModel.getClassNameShort());
        stringBuilder.append("|");
        for (SeerField seerField: entityModel.getFields()
             ) {
            String type = seerField.getType();
            String name = seerField.getName();
            String fieldString = String.format("+ %1$s : %2$s\\l", name, type);
            stringBuilder.append(fieldString);
        }
//        stringBuilder.append("+ type : string\\l");
//        stringBuilder.append("+ name : string\\l");
        // methods
        stringBuilder.append(" \n");

        //stringBuilder.append("|+ login() : void\\l");
                        stringBuilder.append(
                        "}\"\n" +
                "  ]\n");
        stringBuilder.append(" \n");
        stringBuilder.append(" \n");
        stringBuilder.append(" \n");
    }

    private void generateEdge(EntityModel entityModel){

        List<String> names = new ArrayList<>();
        List<String> types = new ArrayList<>();
        List<String> edgeDescriptions = new ArrayList<>();

        for (SeerField seerField: entityModel.getFields()
        ) {
            if (seerField.getSeerEntityRelation() != null){
                if (!types.contains(seerField.getType())){
                    names.add(entityModel.getClassNameShort());
                    types.add(seerField.getType());

                    switch (seerField.getSeerEntityRelation()){
                        case MANYTOONE:
                            edgeDescriptions.add(" \n" +
                                    "        edge [\n" +
                                    "                arrowhead = \"none\"\n" +
                                    "                 headlabel = \"0..1\" " +
                                    "                 taillabel = \"0..*\" " +
                                    "        ]\n" +
                                    " \n");
                            break;
                        case ONETOMANY:
                            edgeDescriptions.add(" \n" +
                                    "        edge [\n" +
                                    "                arrowhead = \"none\"\n" +
                                    "                 headlabel = \"0..*\" " +
                                    "                 taillabel = \"0..1\" " +
                                    "        ]\n" +
                                    " \n");
                            break;
                        case MANYTOMANY:
                            edgeDescriptions.add(" \n" +
                                    "        edge [\n" +
                                    "                arrowhead = \"none\"\n" +
                                    "                 headlabel = \"0..*\" " +
                                    "                 taillabel = \"0..*\" " +
                                    "        ]\n" +
                                    " \n");
                            break;
                    }


                }
            }
        }

        stringBuilder.append("\n");

//        for (SeerField seerField: entityModel.getFields()
//             ) {
//            if (seerField.getSeerEntityRelation() != null){
//
//                stringBuilder.append(" \n" +
//                        "        edge [\n" +
//                        "                arrowhead = \"none\"\n" +
//                        "                 headlabel = \"0..*\" " +
//                        "                 taillabel = \"0..*\" " +
//                        "        ]\n" +
//                        " \n");
//
//                stringBuilder.append("\n");
//                stringBuilder.append("" + entityModel.getClassNameShort() + " -> " + seerField.getType());
//                stringBuilder.append("\n");
//            }
//        }

        for (int i = 0; i < names.size(); i++) {
            stringBuilder.append("\n");
            stringBuilder.append(edgeDescriptions.get(i));
            stringBuilder.append("\n");
            stringBuilder.append(names.get(i) + " -> " + types.get(i));
            stringBuilder.append("\n");
        }

        stringBuilder.append("\n");
    }



}
