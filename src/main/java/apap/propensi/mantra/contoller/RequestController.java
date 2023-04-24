package apap.propensi.mantra.contoller;

import apap.propensi.mantra.helper.CustomUnitPair;
import apap.propensi.mantra.helper.RequestUnitHelper;
import apap.propensi.mantra.model.RequestModel;
import apap.propensi.mantra.model.UserModel;
import apap.propensi.mantra.service.RequestService;
import apap.propensi.mantra.service.UnitService;
import apap.propensi.mantra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/request")
public class RequestController {
    final RequestService requestService;
    final UnitService unitService;
    final UserService userService;

    public RequestController(RequestService requestService, UnitService unitService, UserService userService) {
        this.requestService = requestService;
        this.unitService = unitService;
        this.userService = userService;
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
        UserModel user = userService.getUserByUsername(principal.getName());
        requestService.saveRequest(requestUnitHelper.getRequestModel(), requestUnitHelper.getSelectedUnit(), user);
        redirectAttributes.addFlashAttribute("successMessage", "Request Berhasil Diajukan!");
        return "redirect:/";
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
}
