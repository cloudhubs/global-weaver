package harvester.service;

import edu.baylor.ecs.seer.common.domain.HarvesterData;
import edu.baylor.ecs.seer.common.domain.LocalWeaverResult;
import edu.baylor.ecs.seer.common.domain.LocalWeaverResultType;
import harvester.config.YAMLConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class HarvesterServiceImpl implements HarvesterService {

    @Autowired
    private YAMLConfig yamlConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public HarvesterData getData(LocalWeaverResultType resType) {

        HarvesterData harvest = new HarvesterData();

        List<String> servers = yamlConfig.getServers();

        ArrayList<LocalWeaverResult> arrayListLocalWeaverResult = new ArrayList<>();
        for ( int i = 0; i < servers.size(); i++ ) {
            try {
                String data = restTemplate.getForObject(
                        "http://localhost:" + servers.get(i) + "/local-weaver/" + resType.getResultType(),
                        String.class);
                LocalWeaverResult localWeaverResult = new LocalWeaverResult();
                localWeaverResult.setData(data);
                localWeaverResult.setModuleId(i);
                localWeaverResult.setType(resType);
                arrayListLocalWeaverResult.add(localWeaverResult);
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }
        harvest.setData(arrayListLocalWeaverResult);
        harvest.setStatus(200);
        harvest.setMessage("OK");

        return harvest;
    }

}
