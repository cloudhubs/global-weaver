package generator.controller;

import edu.baylor.ecs.seer.common.context.SeerContext;
import generator.service.FlowGeneratorService;
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

    @Autowired
    private FlowGeneratorService flowGeneratorService;

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
        seerContext = umlGeneratorService.generateAllUMLSources(seerContext);
        return seerContext;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/flow", method = RequestMethod.POST)
    public SeerContext generateFlow(@RequestBody SeerContext seerContext) {
        seerContext = flowGeneratorService.generateAllFlowSources(seerContext);
        return seerContext;
    }



}
