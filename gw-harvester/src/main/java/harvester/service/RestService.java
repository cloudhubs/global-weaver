package harvester.service;

import edu.baylor.ecs.seer.common.context.SeerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    @Autowired
    private RestTemplate restTemplate;

    public SeerContext analyzeContext(String ip, Integer port){
        return restTemplate.getForObject(
                ip + port.toString(),
                SeerContext.class);
    }

    public SeerContext getContextResult(String ipInterface){
        return restTemplate.getForObject(
                ipInterface,
                SeerContext.class);
    }
}
