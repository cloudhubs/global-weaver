package harvester.service;

import harvester.domain.HarvesterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HarvesterServiceImpl implements HarvesterService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public HarvesterData getSecurityData() {
        return new HarvesterData();
//        restTemplate.getForObject(
//                "http://ts-contacts-service:12347/contacts/findAll",
//                HarvesterData.class);

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
