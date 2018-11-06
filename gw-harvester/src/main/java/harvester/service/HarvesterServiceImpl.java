package harvester.service;

import harvester.config.YAMLConfig;
import harvester.domain.HarvesterData;
import harvester.domain.LocalWeaverResult;
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
    public HarvesterData getData(String path) {

        HarvesterData harvest = new HarvesterData();

        List<String> servers = yamlConfig.getServers();

        ArrayList<LocalWeaverResult> arrayListLocalWeaverResult = new ArrayList<>();
        for (String s: servers) {
            String data = restTemplate.getForObject(
                    "http://localhost:"+ s + "/local-weaver/" + path,
                    String.class);
            LocalWeaverResult localWeaverResult = new LocalWeaverResult();
            localWeaverResult.setData(data);
            localWeaverResult.setId(1);
            localWeaverResult.setModuleId(1);
            arrayListLocalWeaverResult.add(localWeaverResult);
        }
        harvest.setData(arrayListLocalWeaverResult);
        harvest.setStatus(200);
        harvest.setMessage("OK");

        return harvest;
    }

}
