package harvester.service;

import edu.baylor.ecs.seer.common.domain.HarvesterData;
import edu.baylor.ecs.seer.common.domain.LocalWeaverResultType;

public interface HarvesterService {

    HarvesterData getData(LocalWeaverResultType resType);

}
