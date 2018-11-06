package similarity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import similarity.service.MossService;
import similarity.service.SimilarityService;

import java.net.URL;

@RestController
@RequestMapping("/")
public class SimilarityDetectionController {

    @Autowired
    private SimilarityService similarityService;

    @Autowired
    private MossService mossService;

    @RequestMapping(value = "/similarity-detection")
    @GetMapping
    public void getSimilaritiesInWeavers() {
        similarityService.getSimilaritiesInWeavers();
    }

    @RequestMapping(value = "/moss")
    @GetMapping
    public URL callMoss() throws Exception {
        return mossService.getResults();
    }

}