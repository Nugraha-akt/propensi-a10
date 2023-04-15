package apap.propensi.mantra.contoller;

import apap.propensi.mantra.model.*;
import apap.propensi.mantra.service.CustomerService;
import apap.propensi.mantra.service.DriverService;
import apap.propensi.mantra.service.RequestService;
import apap.propensi.mantra.service.UserService;
import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/request")
public class RequestController {
    @Autowired
    RequestService requestService;

    @Autowired
    DriverService driverService;

    @Autowired
    UserService userService;

    @Autowired
    CustomerService customerService;


    @GetMapping(value = "/viewall")
    public String listAllRequest(Model model, Principal principal) {
        String role = userService.getUserByUsername(principal.getName()).getRole().getName();
        List<RequestModel> listRequest = new ArrayList<>();

        if (role.equals("Admin") || role.equals("Manager")) {
            // Handle untuk semua request yang ada di database

            listRequest = requestService.getListAllRequest();

        } else if (role.equals("Customer")) {
            // Handle untuk semua request yang dimiliki customer

            CustomerModel customer = customerService.getCustomerByUsername(principal.getName());
            listRequest = customer.getListRequest();

        } else if (role.equals("Driver")) {
            // Handle untuk semua request yang dimiliki driver

            DriverModel driver = driverService.getDriverByUsername(principal.getName());
            List<PairUnitDriverModel> listPairUnitDriver = driver.getListPairRequest();

            for (int i=0; i<listPairUnitDriver.size(); i++) {
                listRequest.add(listPairUnitDriver.get(i).getRequest());
            }
        }


        model.addAttribute("listRequest", listRequest);
        model.addAttribute("role", role);

        return "request/viewall-request";
    }

    @GetMapping("/detail")
    public String viewRequestDetailPage(@RequestParam(value = "id") Long id, Model model) {
        if (requestService.getRequestById(id) == null) {
            model.addAttribute("id", id);
            return "request/request-not-found";
        }

        RequestModel request = requestService.getRequestById(id);

        model.addAttribute("request", request);
        return "request/view-request-detail";
    }


    @GetMapping("/summary")
    public String summaryRequestPage(Model model) {
        Integer jumlahAktif = requestService.getListRequestAktif().size();
        Integer jumlahNonAktif = requestService.getListAllRequest().size() - jumlahAktif;

        model.addAttribute("jumlahAktif", jumlahAktif);
        model.addAttribute("jumlahNonAktif", jumlahNonAktif);

        return "request/request-summary";
    }


}
