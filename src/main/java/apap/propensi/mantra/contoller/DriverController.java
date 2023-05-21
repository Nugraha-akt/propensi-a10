package apap.propensi.mantra.contoller;

import apap.propensi.mantra.model.DriverModel;
import apap.propensi.mantra.model.PairUnitDriverModel;
import apap.propensi.mantra.model.UserModel;
import apap.propensi.mantra.service.DriverService;
import apap.propensi.mantra.service.RequestService;
import apap.propensi.mantra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @GetMapping("/viewall")
    public String listDriverOrderByStatus(@ModelAttribute("successMessage") String successMessage, Model model, RedirectAttributes redirectAttributes) {
        if (!successMessage.isEmpty()) {
            System.out.println(successMessage);
            model.addAttribute("toastrSuccessMessage", successMessage);
            redirectAttributes.addFlashAttribute("successMessage", "");
        } else {
            model.addAttribute("toastrSuccessMessage", "");
        }
        var auth = SecurityContextHolder.getContext().getAuthentication();
        UserModel userSession = userService.getUserByUsername(auth.getName());
        model.addAttribute("userRole", userSession.getRole().toString());
        List<DriverModel> listDriverOrderByStatus = driverService.getListDriverOrderByStatus();
        model.addAttribute("listDriver", listDriverOrderByStatus);
        return "driver/viewall";
    }

    @GetMapping("/detail/{uuid}")
    public String detailDriver(@PathVariable String uuid, Model model){
        DriverModel driver = driverService.getDriverByUuid(uuid);
        if (driver != null){
            List<PairUnitDriverModel> listRequest = driver.getListPairRequest();
//            List<RequestModel> listRequest = driver.getListRequest();
            model.addAttribute("driver", driver);
            model.addAttribute("listRequest", listRequest);
            return "driver/detail";
        }
        return "driver/404";
    }

    @GetMapping("/nonaktifkan/{uuid}")
    public String nonaktifkanDriver(@PathVariable String uuid, Model model){
        DriverModel driver = driverService.getDriverByUuid(uuid);
        if (driver != null){
            model.addAttribute("driver", driver);
            return "driver/validasi-nonaktifkan";
        }
        return "driver/404";
    }

    @PostMapping("/nonaktifkan-berhasil/{uuid}")
    public String nonaktifkanBerhasilDriver(@PathVariable String uuid, RedirectAttributes redirectAttributes) {
        DriverModel driver = driverService.getDriverByUuid(uuid);
        driver.setStatus(3);
        driverService.updateDriver(driver);
        redirectAttributes.addFlashAttribute("successMessage", "Driver Berhasil Dinonaktifkan");
        return "redirect:/driver/viewall";
    }

    @GetMapping("/aktifkan/{uuid}")
    public String aktifkanDriver(@PathVariable String uuid, Model model){
        DriverModel driver = driverService.getDriverByUuid(uuid);
        if (driver != null){
            model.addAttribute("driver", driver);
            return "driver/validasi-aktifkan";
        }
        return "driver/404";
    }

    @PostMapping("/aktifkan-berhasil/{uuid}")
    public String aktifkanBerhasilDriver(@PathVariable String uuid, RedirectAttributes redirectAttributes) {
        DriverModel driver = driverService.getDriverByUuid(uuid);
        driver.setStatus(1);
        driverService.updateDriver(driver);
        redirectAttributes.addFlashAttribute("successMessage", "Driver Berhasil Diaktifkan");
        return "redirect:/driver/viewall";
    }

    @GetMapping("/ringkasan")
    public String ringkasanDriver(Model model) {
        Integer sumAvailable = driverService.getSumAvailable();
        Integer sumUnavailable = driverService.getSumUnavailable();
        Integer sumInactive = driverService.getSumInactive();
        model.addAttribute("sumAvailable", sumAvailable);
        model.addAttribute("sumUnavailable", sumUnavailable);
        model.addAttribute("sumInactive", sumInactive);
        return "driver/ringkasan";
    }
}
