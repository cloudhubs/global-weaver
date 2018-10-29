package edu.baylor.ecs.seer.similaritydetection.service;

import it.zielke.moji.SocketClient;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

@Service
public class MossService {

    public URL getResutls() throws Exception{
        // a list of students' source code files located in the prepared
        // directory.
        Collection<File> files = getFilesToBeCompared();

        // a list of base files that was given to the students for this
        // assignment.
        Collection<File> baseFiles = getBaseFiles();

        //get a new socket client to communicate with the Moss server
        //and set its parameters.
        SocketClient socketClient = new SocketClient();

        //set your Moss user ID
        socketClient.setUserID("260576700");
        //socketClient.setOpt...

        //set the programming language of all student source codes
        socketClient.setLanguage("java");

        //initialize connection and send parameters
        socketClient.run();

        // upload all base files
        for (File f : baseFiles) {
            socketClient.uploadBaseFile(f);
        }

        //upload all source files of students
        for (File f : files) {
            socketClient.uploadFile(f);
        }

        //finished uploading, tell server to check files
        socketClient.sendQuery();

        //get URL with Moss results and do something with it
        return socketClient.getResultURL();

    }

    public Collection<File> getFilesToBeCompared(){
        return FileUtils.listFiles(new File(
                "/Users/svacina/Dev/01Research/centralized-prespective/global-weaver/similarity-detection/output"), new String[] { "java" }, true);
    }

    public Collection<File> getBaseFiles(){
        return FileUtils.listFiles(new File(
                "/Users/svacina/Dev/01Research/centralized-prespective/global-weaver/similarity-detection/input"), new String[] { "java" }, true);
    }

}
