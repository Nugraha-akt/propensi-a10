package apap.propensi.mantra.contoller;

//import apap.propensi.mantra.service.RoleService;
import apap.propensi.mantra.model.CustomerModel;
import apap.propensi.mantra.model.DriverModel;
import apap.propensi.mantra.model.UserModel;
import apap.propensi.mantra.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Map;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private UnitService unitService;
    
    @RequestMapping("/")
    public String home() { return "index"; }

    @GetMapping("/dashboard")
    public String Dashboard(Model model, Principal principal) {
        UserModel user = userService.getUserByUsername(principal.getName());
        if (user.getRole().getName().equals("Manager") || user.getRole().getName().equals("Admin")) {
            model.addAttribute("totalCount", requestService.getTotalCount());
            model.addAttribute("countDepartThisMonth", requestService.getRequestCountForCurrentMonth());
            model.addAttribute("countActiveDriver", driverService.getCountOfActiveDrivers());
            model.addAttribute("countActiveUnit", unitService.getCountOfActiveUnits());
            return "dashboard/admin-manager-dashboard";
        }
        return "dashboard";
    }


    @GetMapping("/dashboard/customer")
    public String DashboardCustomer(Model model, Principal principal) {
        UserModel user = userService.getUserByUsername(principal.getName());
        Map<String, Long> statusCount = requestService.getCountOfRequestsByStatus(user);


        model.addAttribute("createdCount", statusCount.get("createdCount"));
        model.addAttribute("assignedCount",  statusCount.get("assignedCount"));
        model.addAttribute("inProgressCount",  statusCount.get("inProgressCount"));
        model.addAttribute("finishedCount",  statusCount.get("finishedCount"));
        return "dashboard-customer";
    }

    @GetMapping("/dashboarddriver")
    public String dashboardDriver(Model model, Principal principal) {
        UserModel user = userService.getUserByUsername(principal.getName());
        Map<String, Long> statusCount = requestService.getCountOfRequestsByStatus(user);


        model.addAttribute("createdCount", statusCount.get("createdCount"));
        model.addAttribute("assignedCount",  statusCount.get("assignedCount"));
        model.addAttribute("inProgressCount",  statusCount.get("inProgressCount"));
        model.addAttribute("finishedCount",  statusCount.get("finishedCount"));
        return "dashboarddriver";
    }

    @RequestMapping("/login")
    public String login(@ModelAttribute("successMessage") String successMessage, Model model, RedirectAttributes redirectAttributes){
        if (!successMessage.isEmpty()) {
            model.addAttribute("toastrSuccessMessage", successMessage);
            redirectAttributes.addFlashAttribute("successMessage", "");
        } else {
            model.addAttribute("toastrSuccessMessage", "");
        }
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

