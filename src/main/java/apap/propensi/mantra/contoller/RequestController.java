package apap.propensi.mantra.contoller;

import apap.propensi.mantra.model.*;
import apap.propensi.mantra.helper.CustomUnitPair;
import apap.propensi.mantra.helper.RequestUnitHelper;
import apap.propensi.mantra.service.*;
import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

@Controller
@RequestMapping("/request")
public class RequestController {
    final RequestService requestService;
    final UnitService unitService;
    final UserService userService;
    final DriverService driverService;
    final CustomerService customerService;

    @Autowired
    private SuratService suratService;

    public RequestController(RequestService requestService, UnitService unitService, UserService userService, DriverService driverService, CustomerService customerService) {
        this.requestService = requestService;
        this.unitService = unitService;
        this.userService = userService;
        this.driverService = driverService;
        this.customerService = customerService;
    }

    @GetMapping(value = "/add")
    private String addRequestFormPage(Model model){
        RequestModel requestModel = new RequestModel();
        model.addAttribute("requestModel", requestModel);
        return "request/add-request";
    }

    @PostMapping("add")
    private String addRequestSubmit(@ModelAttribute RequestModel requestModel, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("requestModel", requestModel);
        return "redirect:/request/add-unit";
    }

    @GetMapping(value = "/add-unit")
    private String addUnitRequestFormPage(@ModelAttribute("requestModel") RequestModel requestModel, Model model) {
        if (requestModel.getTujuan() == null) {
            return "redirect:/request/add";
        }

        List<CustomUnitPair> availableUnit = unitService.getUnitAvailable(requestModel.getDepartDate(), requestModel.getReturnDate());

        for (CustomUnitPair unit: availableUnit) { //Debugging Hardcode
            System.out.println(unit.getJenis() + " " + unit.getJumlah().toString());
        }

        List<CustomUnitPair> selectedUnit = new ArrayList<>();
        selectedUnit.add(new CustomUnitPair("", (long) 0));


        RequestUnitHelper requestUnitHelper = new RequestUnitHelper(requestModel, selectedUnit);
        System.out.println(requestUnitHelper.getRequestModel().getAlasan()); //Debugging Hardcode

        model.addAttribute("availableUnit", availableUnit);
        model.addAttribute("requestUnitHelper", requestUnitHelper);
        return "request/add-request-unit";
    }

    @PostMapping(value = "add-unit", params = {"save"})
    private String addUnitRequestSubmit(@ModelAttribute RequestUnitHelper requestUnitHelper, Principal principal, RedirectAttributes redirectAttributes) {
        // Process the form and store the message in a session variable
        System.out.println("Request Berhasil Diajukan!");
        UserModel user = userService.getUserByUsername(principal.getName());
        requestService.saveRequest(requestUnitHelper.getRequestModel(), requestUnitHelper.getSelectedUnit(), user);
        redirectAttributes.addFlashAttribute("successMessage", "Request Berhasil Diajukan!");

        return "redirect:/request/viewall";
    }

    @PostMapping(value = "add-unit", params = {"addRow"})
    private String addUnitRow(@ModelAttribute RequestUnitHelper requestUnitHelper, Model model) {
        requestUnitHelper.getSelectedUnit().add(new CustomUnitPair("", (long) 0));
        List<CustomUnitPair> availableUnit = unitService.getUnitAvailable(requestUnitHelper.getRequestModel().getDepartDate(), requestUnitHelper.getRequestModel().getReturnDate());
        model.addAttribute("availableUnit", availableUnit);
        System.out.println(requestUnitHelper.getRequestModel().getAlasan()); //Debugging Hardcode
        model.addAttribute("requestUnitHelper", requestUnitHelper);
        return "request/add-request-unit";
    }

    @PostMapping(value = "add-unit", params = {"deleteRow"})
    private String deleteUnitRow(@ModelAttribute RequestUnitHelper requestUnitHelper, @RequestParam("deleteRow") Integer row, Model model) {
        requestUnitHelper.getSelectedUnit().remove(row.intValue());
        List<CustomUnitPair> availableUnit = unitService.getUnitAvailable(requestUnitHelper.getRequestModel().getDepartDate(), requestUnitHelper.getRequestModel().getReturnDate());
        model.addAttribute("availableUnit", availableUnit);
        model.addAttribute("requestUnitHelper", requestUnitHelper);
        return "request/add-request-unit";
    }

    @PostMapping(value = "add-unit", params = {"back"})
    private String backToFormRequest(@ModelAttribute RequestUnitHelper requestUnitHelper, Model model) {
        model.addAttribute("requestModel", requestUnitHelper.getRequestModel());
        return "request/add-request";
    }

    @GetMapping(value = "/viewall")
    public String listAllRequest(@ModelAttribute("successMessage") String successMessage, Model model, RedirectAttributes redirectAttributes, Principal principal) {
        String role = userService.getUserByUsername(principal.getName()).getRole().getName();
        List<RequestModel> listRequest = new ArrayList<>();

        if (!successMessage.isEmpty()) {
            System.out.println(successMessage);
            model.addAttribute("toastrSuccessMessage", successMessage);
            redirectAttributes.addFlashAttribute("successMessage", "");
        } else {
            model.addAttribute("toastrSuccessMessage", "");
        }

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

    @GetMapping("/assign")
    public String assignDriverPage(@RequestParam(value = "id") Long id, Model model) {
        if (requestService.getRequestById(id) == null) {
            model.addAttribute("id", id);
            return "request/request-not-found";
        }
        RequestModel request = requestService.getRequestById(id);

        if (!request.getStatus().equals("Created")) {
            return "error/404";
        }

        List<DriverModel> listAvailableDrivers = driverService.getListAvailableDriver();
        List<PairUnitDriverModel> listPair = request.getListPairRequest();

        model.addAttribute("listAvailableDrivers", listAvailableDrivers);
        model.addAttribute("request", request);
        model.addAttribute("listPair", listPair);
        return "request/form-assign-driver";
    }

    @PostMapping("/assign")
    public String assignDriverConfirmPage(@ModelAttribute RequestModel request, Model model) {
        RequestModel updatedRequest = requestService.getRequestById(request.getId());

        List<PairUnitDriverModel> listPair = updatedRequest.getListPairRequest();

        // verifikasi bahwa semua driver yang ter-assign, merupakan driver yang unik (tidak ada satu driver yang paired dengan >1 unit)
        for (int i=0; i < listPair.size(); i++) {
            DriverModel driver1 = driverService.getDriverByUuid(request.getListPairRequest().get(i).getDriver().getUuid());
            for (int j=i+1; j < listPair.size(); j++) {
                DriverModel driver2 = driverService.getDriverByUuid(request.getListPairRequest().get(j).getDriver().getUuid());

                if (driver1.getUuid().equals(driver2.getUuid())) {
                    model.addAttribute("id", request.getId());
                    model.addAttribute("errorMsg", "Tidak bisa assign driver ke lebih dari satu unit");
                    return "request/assign-driver-error";
                }
            }
        }

        // update semua driver pada pair, dari hanya uuid -> menjadi DriverModel
        for (int i=0; i < listPair.size(); i++) {
            PairUnitDriverModel pair = listPair.get(i);
            DriverModel driver = driverService.getDriverByUuid(request.getListPairRequest().get(i).getDriver().getUuid());
            driver.setStatus(2);
            pair.setDriver(driver);
        }
        updatedRequest.setStatus("Assigned");
        requestService.updateRequest(updatedRequest);

        model.addAttribute("message", "Berhasil assign driver ke unit!");
        return "request/success-message";
    }

    @GetMapping("/confirm")
    public String confirmRequestPage(@RequestParam(value = "id") Long id, Model model) {
        if (requestService.getRequestById(id) == null) {
            model.addAttribute("id", id);
            return "request/request-not-found";
        }

        RequestModel request = requestService.getRequestById(id);
        request.setStatus("In-Progress");
        request.setStatusPerjalanan("Started");
        requestService.updateRequest(request);

        suratService.generateSurat(request);

        model.addAttribute("message", "Request berhasil dikonfirmasi!");
        return "request/success-message";
    }

    // TODO: belum ada update success! page
    @GetMapping("/update")
    public String updateRequestFormPage(@RequestParam(value = "id") Long id, Model model) {
        if (requestService.getRequestById(id) == null) {
            model.addAttribute("id", id);
            return "request/request-not-found";
        }

        RequestModel request = requestService.getRequestById(id);

        model.addAttribute("request", request);
        return "request/form-update-request";
    }
    @PostMapping("/update")
    public String updateRequestSubmitPage(@ModelAttribute RequestModel request, Model model) {
        RequestModel updatedRequest = requestService.getRequestById(request.getId());
        updatedRequest.setStatusPerjalanan(StringTitleCase.toTitleCase(request.getStatusPerjalanan()));

        requestService.updateRequest(updatedRequest);

        return "redirect:/request/viewall";
    }

    // TODO: ganti "finish" menjadi ubah status perjalanan. Ketika diinput 'finished' maka status request akan ganti menjadi Finished
    @GetMapping("/finish")
    public String finishRequestPage(@RequestParam(value = "id") Long id, Model model) {
        if (requestService.getRequestById(id) == null) {
            model.addAttribute("id", id);
            return "request/request-not-found";
        }

        RequestModel request = requestService.getRequestById(id);
        request.setStatus("Finished");
        request.setStatusPerjalanan("Finished");
        requestService.updateRequest(request);

        model.addAttribute("message", "Request berhasil diselesaikan!");
        return "request/success-message";
    }

    @GetMapping("/detail")
    public String viewRequestDetailPage(@RequestParam(value = "id") Long id, Model model) {
        if (requestService.getRequestById(id) == null) {
            model.addAttribute("id", id);
            return "request/request-not-found";
        }

        RequestModel request = requestService.getRequestById(id);
        request.getListPairRequest();

        // TODO: perbagus htmlnya

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

    @GetMapping("/overview")
    public String overviewRequest(Model model) {

        return "request/";
    }
}

