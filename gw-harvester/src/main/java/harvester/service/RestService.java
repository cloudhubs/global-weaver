package harvester.service;

import edu.baylor.ecs.seer.common.context.SeerContext;
import edu.baylor.ecs.seer.common.context.SeerRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    @Autowired
    private RestTemplate restTemplate;

    public SeerContext getContext(String ip, Integer port){
        return restTemplate.getForObject(
                ip + port.toString(),
                SeerContext.class);
    }

    public SeerContext getContext(String ipInterface){
        return restTemplate.getForObject(
                ipInterface,
                SeerContext.class);
    }

    public SeerContext postContext(String ipInterface, SeerContext seerContext){
        return restTemplate.postForObject(ipInterface, seerContext, SeerContext.class);
    }

//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        // Do any additional configuration here
//        return builder.build();
//    }
}
