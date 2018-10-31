package data.controller;

import data.service.SecurityService;
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
    @RequestMapping(path = "/data/{id}", method = RequestMethod.GET)
    public void getAllContacts(@PathVariable String id){
        System.out.println("[Admin Basic Info Service][Find All Contacts by admin: " + id);
        securityService.getSecurityData();
    }

}
