package apap.propensi.mantra.contoller;

//import apap.propensi.mantra.service.RoleService;
import apap.propensi.mantra.model.UserModel;
import apap.propensi.mantra.service.CustomerService;
import apap.propensi.mantra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

//    @Autowired
//    private RoleService roleService;
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

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        UserModel user = userService.getUserByUsername(principal.getName());
        if (user.getRole().getName() == "Customer") {
            model.addAttribute("instansi", customerService.getCustomerByUsername(principal.getName()).getAsalInstansi());
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @RequestMapping("/blank")
    public String blank() { return "blank";}
}
