package similarity.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParsingResultService {

    /**
     * HTML to be parsed is in resources
     * @param resultPage
     * @return
     */
    public List<String> getResults(List<String> resultPage) {
        List<String> parsedResults = new ArrayList<>();
        for (String row: resultPage
             ) {
            // ToDo
        }
        return parsedResults;
    }
}
