package security.controller;

import security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SecurityController {

    @Autowired
    SecurityService securityService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Security Service ] !";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/security/{id}", method = RequestMethod.GET)
    public String getAllContacts(@PathVariable String id){
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
