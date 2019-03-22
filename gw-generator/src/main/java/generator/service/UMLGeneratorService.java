package generator.service;

import edu.baylor.ecs.seer.common.context.SeerContext;
import edu.baylor.ecs.seer.common.context.SeerMsContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class UMLGeneratorService {

    @Autowired
    private RandomService randomService;

    public SeerContext generateAllUMLSources(SeerContext seerContext){
        for (SeerMsContext ms: seerContext.getMsContexts()){
            if (ms.getEntity().getBoundedContextSource() != null){
                String png = generateUMLSource(ms.getModuleName(), ms.getEntity().getBoundedContextSource(), seerContext.getRequest().getProductsDirectory());
                ms.getEntity().setBoundedContextResult(png);
            }
        }
        String png = generateUMLSource("global", seerContext.getContextMap().getBoundedContextSource(), seerContext.getRequest().getProductsDirectory());
        seerContext.getGlobal().setUmlDiagramResult(png);
        return seerContext;
    }

    public String generateUMLSource(String fileName, String source, String directory){

    //public SeerContext generateUMLSource(SeerContext seerContext) {
        // Generate random string
        //String randomAppendix = randomService.randomString();
        // Generate name of the UML .dot file
        String dot = directory + fileName + ".dot";
        String png = directory + fileName + ".png";
        // Generate the absolute path including directory
        //String umlDiagramPath = seerContext.getGlobal().getUmlDiagramDirectory() + generatedName;

        // Write output
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(dot, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println(source);
        writer.close();

        // Generate png

        String command = "dot -T png -o " + png + " " + dot;
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read the output from command

//        BufferedReader reader =
//                new BufferedReader(new InputStreamReader(proc.getInputStream()));
//
//        String line = "";
//
//        try {
//            while((line = reader.readLine()) != null) {
//                System.out.print(line + "\n");
//            }
//        } catch (IOException e){
//            e.printStackTrace();
//        }


        try {
            proc.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return png;
    }

}
