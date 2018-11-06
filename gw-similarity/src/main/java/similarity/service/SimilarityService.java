package similarity.service;

import org.springframework.stereotype.Service;
import similarity.domain.HarvesterData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class SimilarityService {

    public void compareModules(){
        //get data
        ArrayList<HarvesterData> harvesterDataArrayList = new ArrayList<>();

        int counter = 0;
        File file = new File("/Users/svacina/Dev/01Research/centralized-prespective/global-weaver/similarity-detection/output"+"module"+);
        file.getParentFile().mkdirs();
        try {
            FileWriter writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //create folder + file for each data input + insert data

        //call moss service

        //call url service to save result
    }
}
