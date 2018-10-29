package edu.baylor.ecs.seer.similaritydetection.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlService {
    public String getUrlSource(URL url){
        InputStream is = null;
        BufferedReader br;
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                stringBuilder.append(line);
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }
        return stringBuilder.toString();
    }

    public void getSimilaritiesFromHtml(String html){

    }

    public void getSimilarities() throws Exception{
        //URL url = getResutls();

    }
}
