package harvester.builder;

import edu.baylor.ecs.seer.common.context.SeerContext;

public class GlobalDirector {

    public SeerContext build(GlobalBuilder builder, SeerContext seerContext){
        builder.startNew(seerContext);
        builder.buildGlobalCountes();
        builder.buildSeerContext();
        return builder.getSeerContext();
    }
}
