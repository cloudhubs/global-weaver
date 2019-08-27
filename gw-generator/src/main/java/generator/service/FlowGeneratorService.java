package generator.service;

import edu.baylor.ecs.seer.common.context.SeerContext;
import edu.baylor.ecs.seer.common.context.SeerMsContext;
import edu.baylor.ecs.seer.common.entity.SeerFlowMethodRepresentation;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FlowGeneratorService {

    public SeerContext generateAllFlowSources(SeerContext seerContext){
        for (SeerMsContext ms: seerContext.getMsContexts()){
            if (ms.getFlow() != null && !ms.getFlow().getSeerFlowMethods().isEmpty()){
                for(SeerFlowMethodRepresentation mr : ms.getFlow().getSeerFlowMethods()){

                    if(mr.getSource() == null){
                        continue;
                    }

                    String classShort = mr.getClassName().substring(mr.getClassName().lastIndexOf(".") + 1);
                    String filename = classShort + "_" + mr.getMethodName();
                    boolean isWindows = System.getProperty("os.name")
                            .toLowerCase().startsWith("windows");

                    String directory = "";
                    if(isWindows) {
                        directory = seerContext.getRequest().getProductsDirectory() + "flow\\";
                    } else {
                        directory = seerContext.getRequest().getProductsDirectory() + "flow/";
                    }
                    String png = generateFlowSource(filename, mr.getSource(), directory);
                    mr.setResult(png);
                }
            }
        }
        return seerContext;
    }

    private String generateFlowSource(String fileName, String source, String directory){

        String dot = directory + fileName + ".dot";
//        String png = directory + fileName + ".png";
        String png = directory + fileName + ".svg";

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
