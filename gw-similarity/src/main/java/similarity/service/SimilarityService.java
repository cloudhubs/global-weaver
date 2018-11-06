package similarity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import similarity.domain.HarvesterData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class SimilarityService {

    @Autowired
    private RestTemplate restTemplate;

    public void compareModules(){
        //get data
        ArrayList<HarvesterData> harvesterDataArrayList = new ArrayList<>();

        ResponseEntity<ArrayList<HarvesterData>> response = restTemplate.exchange(
                "http://localhost:7003/security",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<HarvesterData>>(){});
        ArrayList<HarvesterData> data = response.getBody();

        for (int i = 0; i < data.size(); i++){
            File file = new File("/Users/svacina/Dev/01Research/centralized-prespective/global-weaver/similarity-detection/output/"+"module"+i+"/Similarity"+i+".java");
            file.getParentFile().mkdirs();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));


                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        //create folder + file for each data input + insert data

        //call moss service

        //call url service to save result
    }
}
