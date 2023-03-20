package apap.propensi.mantra.contoller;

//import apap.propensi.mantra.service.RoleService;
import apap.propensi.mantra.model.CustomerModel;
import apap.propensi.mantra.model.DriverModel;
import apap.propensi.mantra.model.UserModel;
import apap.propensi.mantra.service.CustomerService;
import apap.propensi.mantra.service.DriverService;
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
    private DriverService driverService;

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
        return "login-page";
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        UserModel user = userService.getUserByUsername(principal.getName());
        if (user.getRole().getName() == "Customer") {
            CustomerModel customerModel = customerService.getCustomerByUsername(principal.getName());
            if (customerModel == null) {
                model.addAttribute("instansi", "");
            } else {
                model.addAttribute("instansi",customerModel.getAsalInstansi());
            }
        }

        if (user.getRole().getName() == "Driver") {
            DriverModel driverModel = driverService.getDriverByUsername(principal.getName());
            if (driverModel == null) {
                model.addAttribute("sim", "");
            } else {
                model.addAttribute("sim",driverModel.getSim());
            }
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @RequestMapping("/blank")
    public String blank() { return "blank";}
}
