package similarity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import similarity.domain.HarvesterData;
import similarity.domain.LocalWeaverResult;
import java.io.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimilarityService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MossService mossService;

    @Autowired
    private DownloadService downloadService;

    @Autowired
    private ParsingResultService parsingResultService;

    /**
     * Get results of similarities in micro services
     * @return
     */
    public List<String> getSimilaritiesInWeavers(){
        ArrayList<LocalWeaverResult> localWeaverGraphs = getLocalWeaversResults();
        createOutputFiles(localWeaverGraphs);
        URL url = compareLocalWeaversOutputs();
        List<String> resultPage = downloadResultPageFromMoss(url);
        return getResultsFromPage(resultPage);
    }

    /**
     * Get data from local weavers
     * @return
     */
    private ArrayList<LocalWeaverResult> getLocalWeaversResults(){
//        ResponseEntity<HarvesterData> response = restTemplate.exchange(
//                "http://localhost:7003/security",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<HarvesterData>(){});

        HarvesterData response = restTemplate.getForObject(
                    "http://localhost:7003/flowStructure",
                    HarvesterData.class);


        //HarvesterData data = response.getBody();
        return response.getData();
    }

    /**
     * Create folder and file for each data input + insert the data
     * @param localWeaverGraphs
     */
    private void createOutputFiles(ArrayList<LocalWeaverResult> localWeaverGraphs) {
        for (int i = 0; i < localWeaverGraphs.size(); i++) {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("/Users/svacina/Dev/centralized-prespective/global-weaver/gw-similarity/output/module" + i + "/Similarity" + i + ".java"), "utf-8"))) {
                String data = localWeaverGraphs.get(i).getData();
                String dataEscaped = data.replaceAll("],\\[", "],\n[");
                writer.write(dataEscaped);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Call moss service to obtain validate local weavers graphs
     * @return
     */
    private URL compareLocalWeaversOutputs(){
        URL url = null;
        try {
            url = mossService.getResults();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Call download service to download results from URL
     * @param url
     * @return
     */
    private List<String> downloadResultPageFromMoss(URL url){
        return downloadService.getUrlSource(url);
    }

    /**
     * Call parsing result service to get results from page on moss server
     * @param resultPage
     * @return
     */
    private List<String> getResultsFromPage(List<String> resultPage){
        return parsingResultService.getResults(resultPage);
    }
}
