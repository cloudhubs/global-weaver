package security.controller;

import edu.baylor.ecs.seer.common.domain.SecurityData;
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
     * Handshake
     *
     * @return A welcome string
     */
    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Security Service ] !";
    }

//    /**
//     * This endpoint provides the security information about the given id.
//     *
//     * //@param id The id about which to get security info
//     * @return The info obtained
//     * @throws Exception Any exceptions from the call
//     */
//    @CrossOrigin(origins = "*")
//    @RequestMapping(path = "/security", method = RequestMethod.GET)
//    public SecurityData getAllContacts() throws Exception {
//        System.out.println("[Admin Basic Info Service][Find All Contacts by admin: ");
//        String roleDefTest = "S  uperAd min   \n" +
//                "Super   Admin->Admin\n" +
//                "SuperAdmin->Revi ewer\n" +
//                "Admin->  User\n" +
//                "User->Guest\n" +
//                "Admin ->Moderator\n" +
//                "Admin<->Reviewer";
//        return securityService.getSecurityData(roleDefTest);
//    }
//

    /**
     * ToDO:
     * Controller should NEVER throw exceptions
     * Use postman to upload text file and string -> there will be two ways
     * Create service for converting input
     * Rename method "getModulesPrivilegesViolations"
     * Rename api
     * aspect logging
     * rename service -> privi
     */

    /**
     * Get privileges violations across modules
     *
     * @return privileges violations
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/security", method = RequestMethod.GET)
    public SecurityData getAllContacts() throws Exception {
        System.out.println("[Admin Basic Info Service][Find All Contacts by admin: ");
        String roleDefTest = "S  uperAd min   \n" +
                "Super   Admin->Admin\n" +
                "SuperAdmin->Revi ewer\n" +
                "Admin->  User\n" +
                "User->Guest\n" +
                "Admin ->Moderator\n" +
                "Admin<->Reviewer";
        return securityService.getSecurityData(roleDefTest);
    }



}
