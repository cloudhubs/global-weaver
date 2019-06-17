package data.service;

import edu.baylor.ecs.seer.common.FlowNode;
import edu.baylor.ecs.seer.common.Pair;
import edu.baylor.ecs.seer.common.context.SeerContext;
import edu.baylor.ecs.seer.common.context.SeerFlowContext;
import edu.baylor.ecs.seer.common.context.SeerMsContext;
import edu.baylor.ecs.seer.common.entity.EntityModel;
import edu.baylor.ecs.seer.common.entity.SeerField;
import edu.baylor.ecs.seer.common.entity.SeerFlowMethodRepresentation;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Generates source code for entity model
 */
@Service
public class BytecodeFlowSourceService {

    private StringBuilder stringBuilder;

    /**
     * @param ms
     * @return
     */
    public SeerMsContext buildAllFlowSource(SeerMsContext ms){
        SeerFlowContext flowContext = ms.getFlow();

        if (flowContext.getSeerFlowMethods() != null && flowContext.getSeerFlowMethods().size() > 0){
            for(SeerFlowMethodRepresentation mr : flowContext.getSeerFlowMethods()){
                if(mr.getNodes().size() == 0){
                    mr.setSource(null);
                    continue;
                }
                start();
                body(mr);
                end();
                mr.setSource(stringBuilder.toString());
            }
        }


        return ms;
    }

    public String generateFlowSource(SeerFlowMethodRepresentation methodRepresentation) {
        start();
        body(methodRepresentation);
        end();
        methodRepresentation.setSource(stringBuilder.toString());
        return stringBuilder.toString();
    }

    private void start(){
        stringBuilder = new StringBuilder();
        stringBuilder.append(
                "digraph G {\n" +
                "        fontname = \"Bitstream Vera Sans\"\n" +
                "        fontsize = 8\n" +
                " \n" +
                "        node [\n" +
                "                fontname = \"Bitstream Vera Sans\"\n" +
                "                fontsize = 8\n" +
                "        ]\n" +
                " \n" +
                "        edge [\n" +
                "                fontname = \"Bitstream Vera Sans\"\n" +
                "                fontsize = 8\n" +
                "        ]" +
                " \n" +
                " \n" +
                "        start[shape=\"box\", style=rounded];"
        );
    }

    /**
     * @param mr
     */
    private void body(SeerFlowMethodRepresentation mr){

        /* Generate FlowNodes */
        Map<Integer, FlowNode> map = mr.getNodes();
        for(FlowNode fn : map.values()){
            generateNode(fn);
        }

        /* Generate FlowNodes */
        for(FlowNode fn : map.values()){
            for(Integer i : fn.getChildren()){
                FlowNode dest = map.get(i);
                generateEdge(fn, dest);
            }
        }

        FlowNode start = null;
        try {
            start = map.values().iterator().next();
        } catch (NullPointerException | NoSuchElementException e){
            e.printStackTrace();
        }

        if(start != null) {
            stringBuilder.append("start -> ");
            stringBuilder.append(start.getId());
            stringBuilder.append("\n");
        }
    }

    private void end(){
        stringBuilder.append("}");
    }

    private void generateNode(FlowNode flowNode){

        stringBuilder.append(flowNode.getId());

        switch (flowNode.getType()){
            case "goto": stringBuilder.append("[shape=\"box\", style=\"\"];"); break;
            case "conditional": stringBuilder.append("[shape=\"diamond\", style=\"\"];"); break;
            case "method": stringBuilder.append("[shape=\"box\", style=\"\"];"); break;
            case "return": stringBuilder.append("[shape=\"box\", style=rounded];"); break;
            default: stringBuilder.append("[shape=\"box\", style=\"\"];"); break;
        }
        stringBuilder.append("\n");

        stringBuilder.append(flowNode.getId());
        stringBuilder.append("[label=\"");
        stringBuilder.append(flowNode.getRaw());
        stringBuilder.append("\"]");
        stringBuilder.append("\n");
    }

    private void generateEdge(FlowNode source, FlowNode dest){

        stringBuilder.append("\n");
        stringBuilder.append(source.getId());
        stringBuilder.append(" -> ");
        stringBuilder.append(dest.getId());
        stringBuilder.append("\n");
    }
}
