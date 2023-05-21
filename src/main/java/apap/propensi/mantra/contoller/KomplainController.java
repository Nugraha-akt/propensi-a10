package apap.propensi.mantra.contoller;

import apap.propensi.mantra.model.*;
import apap.propensi.mantra.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/komplain")
public class KomplainController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerServiceService customerServiceService;

    @Autowired
    private KomplainService komplainService;

    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public String addComplaintPage(@RequestParam(value = "id") Long id, Model model) {
        if (requestService.getRequestById(id) == null) {
            model.addAttribute("id", id);
            return "request/request-not-found";
        }
        RequestModel request = requestService.getRequestById(id);

        if (!request.getStatus().equals("Finished")) {
            return "error/404";
        }

        KomplainModel komplain = new KomplainModel();
        List<KategoriKomplain> listKategori = Arrays.asList(KategoriKomplain.values());

        model.addAttribute("komplain", komplain);
        model.addAttribute("request", request);
        model.addAttribute("listKategori", listKategori);

        return "komplain/form-add-komplain";
    }

    @PostMapping("/add")
    public String addComplaintSubmitPage(@ModelAttribute KomplainModel komplain, Principal principal, RedirectAttributes redirectAttributes) {
        RequestModel request = requestService.getRequestById(komplain.getRequest().getId());
        komplain.setRequest(request);
        komplain.setCustomer(customerService.getCustomerByUsername(principal.getName()));

        request.setKomplain(komplain);
        komplainService.addKomplain(komplain);
        requestService.updateRequest(request);

        redirectAttributes.addFlashAttribute("successMessage", "Komplain berhasil diajukan!");
        return "redirect:/request/viewall";
    }

    @GetMapping("/respond")
    public String respondComplaintFormPage(@RequestParam(value = "id") Long id, Model model, RedirectAttributes redirectAttributes) {
        if (komplainService.getKomplainById(id) == null) {
            model.addAttribute("errorMsg", "Komplain dengan id " + id + " tidak ditemukan");
            return "komplain/komplain-error";
        }
        KomplainModel komplain = komplainService.getKomplainById(id);

        if (komplain.getRespon() != null) {
            model.addAttribute("errorMsg", "Komplain sudah direspon");
            return "komplain/komplain-error";
        }

        model.addAttribute("komplain", komplain);
        return "komplain/form-respon-komplain";
    }

    @PostMapping("/respond")
    public String respondComplaintSubmitPage(@ModelAttribute KomplainModel komplainModel, Principal principal, RedirectAttributes redirectAttributes) {
        KomplainModel komplain = komplainService.getKomplainById(komplainModel.getId());
        komplain.setRespon(komplainModel.getRespon());
        komplain.setRespondedAt(LocalDateTime.now());
        komplain.setCustomerService(customerServiceService.getCustomerServiceByUsername(principal.getName()));
        komplain.setStatus(1);

        komplainService.updateKomplain(komplain);

        return "redirect:/komplain/viewall";
    }

    @GetMapping("/ringkasan")
    public String ringkasanKomplain(Model model) {
        Integer sumNew = komplainService.getSumNew();
        Integer sumClosed = komplainService.getSumClosed();
        Integer sumLayanan = komplainService.getSumLayanan();
        Integer sumUnit = komplainService.getSumUnit();
        Integer sumDriver = komplainService.getSumDriver();
        Integer sumWaktu = komplainService.getSumWaktu();
        Integer sumLainnya = komplainService.getSumLainnya();
        System.out.println(sumLainnya);
        model.addAttribute("sumNew", sumNew);
        model.addAttribute("sumClosed", sumClosed);
        model.addAttribute("sumLayanan", sumLayanan);
        model.addAttribute("sumUnit", sumUnit);
        model.addAttribute("sumDriver", sumDriver);
        model.addAttribute("sumWaktu", sumWaktu);
        model.addAttribute("sumLainnya", sumLainnya);
        return "komplain/ringkasan";
    }

    @GetMapping(value = "/viewall")
    public String listAllRequest(Model model, Principal principal) {
        String role = userService.getUserByUsername(principal.getName()).getRole().getName();
        List<KomplainModel> listKomplain = new ArrayList<>();

        if (role.equals("Customer Service")) {
            listKomplain = komplainService.getListKomplainOrderedByStatus();
        } else if (role.equals("Admin") || role.equals("Manager")) {
            List<KomplainModel> listKomplainNew = komplainService.getListKomplainNew();
            List<KomplainModel> listKomplainClosed = komplainService.getListKomplainClosed();
            for (int i=0; i < listKomplainClosed.size(); i++) {
                listKomplain.add(listKomplainClosed.get(i));
            }
            for (int i=0; i < listKomplainNew.size(); i++) {
                listKomplain.add(listKomplainNew.get(i));
            }
        }
        else if (role.equals("Customer")) {
            CustomerModel customer = customerService.getCustomerByUsername(principal.getName());
            listKomplain = customer.getListKomplain();
            Collections.reverse(listKomplain);
        }

        model.addAttribute("listKomplain", listKomplain);
        model.addAttribute("role", role);

        return "komplain/viewall";
    }

    @GetMapping("/detail/{id}")
    public String detailKomplain(@PathVariable Long id, Model model, Principal principal){
        String role = userService.getUserByUsername(principal.getName()).getRole().getName();
        KomplainModel komplain = komplainService.getKomplainById(id);
        RequestModel request = requestService.getRequestById(komplain.getRequest().getId());
        request.getListPairRequest();
        model.addAttribute("role", role);
        model.addAttribute("request", request);
        model.addAttribute("komplain", komplain);
        return "komplain/detail";
    }
}
