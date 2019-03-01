package generator.controller;

import edu.baylor.ecs.seer.common.context.SeerContext;
import generator.service.GeneratorService;
import generator.service.UMLGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/generator")
public class GeneratorController {

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private UMLGeneratorService umlGeneratorService;

    @RequestMapping(value = "/graph")
    @GetMapping
    public List<String> getGraph() {
        List<String> graph = null;
        try {
            graph = generatorService.generateGraph();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // graph picture
        return graph;

    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/uml", method = RequestMethod.POST)
    public SeerContext generateUml(@RequestBody SeerContext seerContext) {
        seerContext = umlGeneratorService.generateUML(seerContext);
        return seerContext;
    }



}
