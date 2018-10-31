package harvester.service;

import harvester.domain.HarvesterData;
import harvester.domain.LocalWeaverResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class HarvesterServiceImpl implements HarvesterService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public HarvesterData getSecurityData() {
        String data = restTemplate.getForObject(
                "http://localhost:18767/local-weaver/flowStructure",
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
        return harvesterData;
    }


//    public String getSimilarities(){
//        return "";
//    }
//
//    public void acquireContextFromAllMicroservices(){
//
//    }
//
//    public String getContextFromMicroservice(){
//        StringBuilder result = new StringBuilder();
//        try {
//            URL url = new URL("http://127.0.0.1:18767/evaluator/json");
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line;
//            while ((line = rd.readLine()) != null) {
//                result.append(line);
//            }
//            rd.close();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return result.toString();
//
//    }
//
//    public void saveContextToStructure(){
//        //create output directory
//
//        //
//    }

}
