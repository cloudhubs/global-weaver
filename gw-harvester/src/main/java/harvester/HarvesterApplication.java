package harvester;

import harvester.config.Spring;
import harvester.config.YAMLConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {"harvester"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@IntegrationComponentScan
public class HarvesterApplication {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(HarvesterApplication.class, args);
    }

}
//public class HarvesterApplication implements CommandLineRunner {
//
//    @Autowired
//    private YAMLConfig yamlConfig;
//
//    @Autowired
//    private Spring spring;
//
//    private Logger log = LoggerFactory.getLogger(HarvesterApplication.class);
//
//    public static void main(String[] args) throws Exception {
//        SpringApplication.run(HarvesterApplication.class, args);
//    }
//
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder.build();
//    }
//
//    @Override
//    public void run(String... strings) throws Exception {
//        log.info("Environment: " + yamlConfig.getEnvironment());
//        log.info("Reachable server ports: " + yamlConfig.getServers());
//    }
//}
