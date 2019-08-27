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
        String png = directory + fileName + ".svg";
        // Generate the absolute path including directory
        //String umlDiagramPath = seerContext.getGlobal().getUmlDiagramDirectory() + generatedName;

        // Make the directory if it doesn't exist
        File dir = new File(directory);
        dir.mkdirs();

        File file = new File(dot);
        try {
            PrintWriter pw = new PrintWriter(file, "UTF-8");
            pw.println(source);
            pw.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Generate png
        boolean isWindows = System.getProperty("os.name")
                .toLowerCase().startsWith("windows");

        String command = "";
        if(isWindows) {
            command = "cmd.exe /c dot -T png -o " + png + " " + dot;
        } else {
//            command = "dot -T png -o " + png + " " + dot;
            command = "dot -Tsvg " + dot + " -o " + png;

        }
        
        Process proc = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            proc = runtime.exec(command);
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

        if (proc != null) {
            try {
                proc.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return png;
    }

}
