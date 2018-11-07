package harvester.service;

import harvester.domain.HarvesterData;
import harvester.domain.LocalWeaverResultType;

public interface HarvesterService {

    HarvesterData getData(LocalWeaverResultType resType);

}
