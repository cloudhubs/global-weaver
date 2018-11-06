package similarity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import similarity.service.MossService;
import similarity.service.SimilarityService;

import java.net.URL;

/**
 *
 */
@RestController
@RequestMapping("/")
public class SimilarityDetectionController {

    @Autowired
    private SimilarityService similarityService;

    @Autowired
    private MossService mossService;

    /**
     * REST API for deriving similarities in modules
     */
    @RequestMapping(value = "/similarity-detection")
    @GetMapping
    public void getSimilaritiesInWeavers() {
        similarityService.getSimilaritiesInWeavers();
    }

    /**
     * Calling MOSS validation service on current content in output directory
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/moss")
    @GetMapping
    public URL callMossService() throws Exception {
        return mossService.getResults();
    }

}