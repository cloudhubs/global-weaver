package security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import security.domain.HarvesterData;
import security.domain.SecurityData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void getSecurityData() {
        HarvesterData data;

        /*
        data = restTemplate.getForObject(
                "http://localhost:7002/data",
                HarvesterData.class);
         */
        /* For use with updated DTO

        String json = data.someGetJSONMethod();

         */

        // Testing only
        String json = "{\"seer.ecs.baylor.edu.securitytestsimple.UserAccessible.UserMethodBad1()\":[\"User\",\"Admin\"],\"seer.ecs.baylor.edu.securitytestsimple.UserAccessible.UserMethod1()\":[\"User\",\"Admin\"],\"seer.ecs.baylor.edu.securitytestsimple.AdminAccessible.AdminMethod2()\":[\"Admin\"],\"seer.ecs.baylor.edu.securitytestsimple.AdminAccessible.AdminMethod1()\":[\"Admin\"]} @{\"seer.ecs.baylor.edu.securitytestsimple.UserAccessible.UserMethodBad1()\":[\"seer.ecs.baylor.edu.securitytestsimple.AdminAccessible.AdminMethod1()\"],\"seer.ecs.baylor.edu.securitytestsimple.UserAccessible.UserMethod1()\":[],\"seer.ecs.baylor.edu.securitytestsimple.AdminAccessible.AdminMethod2()\":[\"seer.ecs.baylor.edu.securitytestsimple.UserAccessible.UserMethod1()\"],\"seer.ecs.baylor.edu.securitytestsimple.AdminAccessible.AdminMethod1()\":[]}";

        String[] jsons = json.split("@");

        Map<String, List<String>> roles = null, nodes = null;
        try {
            roles = new ObjectMapper().readValue(jsons[0], Map.class);
            nodes = new ObjectMapper().readValue(jsons[1], Map.class);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        Stack<String> stack;

    }

}
