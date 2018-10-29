package edu.baylor.ecs.seer.similaritydetection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ContextAcquireService {

//    @Autowired
//    private RestTemplate restTemplate;

    public String getSimilarities(){
        return "";
    }

    public void acquireContextFromAllMicroservices(){

    }

    public String getContextFromMicroservice(){
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL("http://127.0.0.1:18767/evaluator/json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        return result.toString();

    }

    public void saveContextToStructure(){
        //create output directory

        //
    }
}
