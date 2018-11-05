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
    public ArrayList<HarvesterData> getData(String path) {

        ArrayList<HarvesterData> harvesterDataList = new ArrayList<>();

        List<String> servers = yamlConfig.getServers();

        for (String s: servers
             ) {
            String data = restTemplate.getForObject(
                    "http://localhost:"+ s + "/local-weaver/" + path,
                    String.class);
            HarvesterData harvesterData = new HarvesterData();
            ArrayList<LocalWeaverResult> arrayListLocalWeaverResult = new ArrayList<>();
            LocalWeaverResult localWeaverResult = new LocalWeaverResult();
            localWeaverResult.setData(data);
            localWeaverResult.setId(1);
            localWeaverResult.setModuleId(1);
            arrayListLocalWeaverResult.add(localWeaverResult);
            harvesterData.setData(arrayListLocalWeaverResult);
            harvesterData.setStatus(200);
            harvesterData.setMessage("OK");
            harvesterDataList.add(harvesterData);
        }


        return harvesterDataList;
    }

}
