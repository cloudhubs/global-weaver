package security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * This provides an interface to the yaml config file located in resources so that other classes can get info from it.
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Profile("prod")
public class YAMLConfig {

    /**
     * This is the name of the module.
     */
    private String name;

    /**
     * This is the base url of the application.
     */
    private String url;

    /**
     * This is the environment name within which this configuration is valid.
     */
    private String environment;

    /**
     * This is the list servers available in this configuration.
     */
    private List<String> servers = new ArrayList<>();

    /**
     * This is the list of endpoints, matched to the list of servers.
     */
    private List<String> endpoints = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public List<String> getServers() {
        return servers;
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }

    public List<String> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<String> endpoints) {
        this.endpoints = endpoints;
    }

}