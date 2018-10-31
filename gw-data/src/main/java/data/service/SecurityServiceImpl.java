package data.service;

import data.domain.HarvesterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void getSecurityData() {
        restTemplate.getForObject(
                "http://localhost:7002/data",
                HarvesterData.class);

    }

}
