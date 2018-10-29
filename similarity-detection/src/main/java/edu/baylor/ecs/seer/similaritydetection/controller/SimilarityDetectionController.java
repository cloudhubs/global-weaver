package edu.baylor.ecs.seer.similaritydetection.controller;

import edu.baylor.ecs.seer.similaritydetection.service.MossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SimilarityDetectionController {

    @Autowired
    private MossService mossService;

    @RequestMapping(value = "/similarity-detection")
    @GetMapping
    public void compare() throws Exception{
        mossService.getResutls();
    }

}