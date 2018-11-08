package security.controller;

import security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This is the REST controller for this module. It exposes two endpoints: /welcome and /security/{id}, which
 * allow a user to view a basic welcome page or to get security data based on the given id.
 */
@RestController
public class SecurityController {

    /**
     * This is an autowired security service to allow access to the REST service functionality.
     */
    @Autowired
    SecurityService securityService;

    /**
     * This endpoint will define a basic welcome page.
     *
     * @return A welcome string
     */
    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Security Service ] !";
    }

    /**
     * This endpoint provides the security information about the given id.
     *
     * @param id The id about which to get security info
     * @return The info obtained
     * @throws Exception Any exceptions from the call
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/security/{id}", method = RequestMethod.GET)
    public String getAllContacts(@PathVariable String id) throws Exception {
        System.out.println("[Admin Basic Info Service][Find All Contacts by admin: " + id);
        String roleDefTest = "S  uperAd min   \n" +
                "Super   Admin->Admin\n" +
                "SuperAdmin->Revi ewer\n" +
                "Admin->  User\n" +
                "User->Guest\n" +
                "Admin ->Moderator\n" +
                "Moderator<->Reviewer";
        return securityService.getSecurityData(roleDefTest);
    }

}
