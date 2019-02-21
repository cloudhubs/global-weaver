package harvester.builder;

import edu.baylor.ecs.seer.common.context.SeerContext;
import edu.baylor.ecs.seer.common.context.SeerGlobalContext;
import edu.baylor.ecs.seer.common.context.SeerMsContext;

public class GlobalBuilder {
    private SeerGlobalContext seerGlobalContext;
    private SeerContext seerContext;

    public void startNew(SeerContext seerContext){
        this.seerGlobalContext = new SeerGlobalContext();
        this.seerContext = seerContext;
    }

    public void buildGlobalCountes(){
        for (SeerMsContext ms: seerContext.getMsContexts()
             ) {
            Integer entityCounter = ms.getEntity().getEntityCounter();
            seerGlobalContext.setEntityCounter(entityCounter);
//            Integer linesOfCode = ms.getFlow().getLinesOfCode();
//            seerGlobalContext.setConfigurationLinesCounter(linesOfCode);
        }
        System.out.println(seerGlobalContext.getEntityCounter());
    }

    public void buildSeerContext(){
        seerContext.setGlobal(seerGlobalContext);
    }

    public SeerGlobalContext getSeerGlobalContext() {
        return seerGlobalContext;
    }

    public SeerContext getSeerContext() {
        return seerContext;
    }
}
