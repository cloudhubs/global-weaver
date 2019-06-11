package harvester.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import edu.baylor.ecs.seer.common.context.SeerContext;
import edu.baylor.ecs.seer.common.context.SeerRequestContext;
import edu.baylor.ecs.seer.common.flow.SeerFlowMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class RestService {

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder builder){
        this.restTemplate = builder.build();
    }

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
        String json = null;
        try {
            json = restTemplate.postForObject(ipInterface, seerContext, String.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addKeyDeserializer(SeerFlowMethod.class, new KeyDeserializer() {
            @Override
            public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                return null;
            }
        });
        objectMapper.registerModule(simpleModule);

        SeerContext context = null;
        try {
            context = objectMapper.readValue(json, SeerContext.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return context;
    }

//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        // Do any additional configuration here
//        return builder.build();
//    }
}
