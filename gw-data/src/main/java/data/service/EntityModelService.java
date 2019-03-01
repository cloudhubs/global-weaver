package data.service;

import edu.baylor.ecs.seer.common.context.SeerContext;
import edu.baylor.ecs.seer.common.context.SeerMsContext;
import org.springframework.stereotype.Service;

@Service
public class EntityModelService {

    private StringBuilder stringBuilder;

    public SeerContext buildUml(SeerContext seerContext){
        for (SeerMsContext ms: seerContext.getMsContexts()
             ) {
            if (ms.getEntity().getEntities() != null && ms.getEntity().getEntities().size() > 0){
                start();
                body();
                end();
                ms.getEntity().setBoundedContextSource(stringBuilder.toString());
            }
        }

        return seerContext;
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
                "        ]");
    }

    private void body(){
        stringBuilder.append("Team [\n" +
                "                label = \"{Team|+ name : string\\l+ code : int\\l|+ die() : void\\l}\"\n" +
                "        ]\n" +
                " \n" +
                "        subgraph clusterContestImpl {\n" +
                "                label = \"Package contest.impl\"\n" +
                " \n" +
                "                Member [\n" +
                "                        label = \"{Member||+ login() : void\\l}\"\n" +
                "                ]\n" +
                " \n" +
                "                Contest [\n" +
                "                        label = \"{Contest||+ start() : void\\l}\"\n" +
                "                ]\n" +
                "        }\n" +
                " \n" +
                "        edge [\n" +
                "                arrowhead = \"empty\"\n" +
                "        ]\n" +
                " \n" +
                "        Member -> Team\n" +
                "        Contest -> Team\n" +
                " \n" +
                "        edge [\n" +
                "                arrowhead = \"none\"\n" +
                " \n" +
                "                headlabel = \"0..*\"\n" +
                "                taillabel = \"0..*\"\n" +
                "        ]\n" +
                " \n" +
                "        Member -> Contest");
    }

    private void end(){
        stringBuilder.append("}");
    }

}
