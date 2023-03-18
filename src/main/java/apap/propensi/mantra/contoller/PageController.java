package apap.propensi.mantra.contoller;

import apap.propensi.mantra.service.RoleService;
import apap.propensi.mantra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String Dashboard() {
        return "dashboard";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
