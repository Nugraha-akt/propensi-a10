package apap.propensi.mantra.contoller;

import apap.propensi.mantra.model.DriverModel;
import apap.propensi.mantra.model.RequestModel;
import apap.propensi.mantra.model.UserModel;
import apap.propensi.mantra.service.DriverService;
import apap.propensi.mantra.service.RequestService;
import apap.propensi.mantra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String listDriverOrderByStatus(Model model) {
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
            List<RequestModel> listRequest = driver.getListRequest();
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
    public String nonaktifkanBerhasilDriver(@PathVariable String uuid, Model model) {
        DriverModel driver = driverService.getDriverByUuid(uuid);
        driver.setStatus(4);
        DriverModel nonaktifDriver = driverService.updateDriver(driver);
        model.addAttribute("driver", nonaktifDriver);
        return "driver/nonaktifkan";
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
    public String aktifkanBerhasilDriver(@PathVariable String uuid, Model model) {
        DriverModel driver = driverService.getDriverByUuid(uuid);
        driver.setStatus(2);
        DriverModel aktifDriver = driverService.updateDriver(driver);
        model.addAttribute("driver", aktifDriver);
        return "driver/aktifkan";
    }

    @GetMapping("/ringkasan")
    public String ringkasanDriver(Model model) {
        Integer sumSedangDitugaskan = driverService.getSumSedangDitugaskan();
        Integer sumBelumDitugaskan = driverService.getSumBelumDitugaskan();
        Integer sumSudahDitugaskan = driverService.getSumSudahDitugaskan();
        Integer sumTidakAktif = driverService.getSumTidakAktif();
        model.addAttribute("sumSedangDitugaskan", sumSedangDitugaskan);
        model.addAttribute("sumBelumDitugaskan", sumBelumDitugaskan);
        model.addAttribute("sumSudahDitugaskan", sumSudahDitugaskan);
        model.addAttribute("sumTidakAktif", sumTidakAktif);
        return "driver/ringkasan";
    }

    @GetMapping("/update/{uuid}")
    public String updateDriver(@PathVariable String uuid, Model model){
        DriverModel driver = driverService.getDriverByUuid(uuid);
        if (driver != null){
            if (driver.getStatus()==2 || driver.getStatus()==3) {
                List<RequestModel> listRequestMulai = requestService.getListRequestMulai();
                model.addAttribute("listRequest", listRequestMulai);
                model.addAttribute("driver", driver);
                return "driver/form-update";
            } else if (driver.getStatus()==1) {
                String statusPerjalanan = requestService.getRequestByDriverUuid(uuid).getStatus();
                if (!statusPerjalanan.equals("SAMPAI")){
                    model.addAttribute("driver", driver);
                    return "driver/belum-sampai";
                }
                else {
                    model.addAttribute("driver", driver);
                    return "driver/validasi-sampai";
                }
            }
        }
        return "driver/404";
    }

    @GetMapping("/update-tugaskan/{uuid}/{id}")
    public String updateSedangDitugaskan(@PathVariable String uuid, @PathVariable Long id, Model model){
        DriverModel driver = driverService.getDriverByUuid(uuid);
        RequestModel request = requestService.getRequestById(id);
        if (driver != null){
            model.addAttribute("driver", driver);
            model.addAttribute("request", request);
            return "driver/validasi-tugaskan";
        }
        return "driver/404";
    }

    @PostMapping("/update-sedang/{uuid}/{id}")
    public String updateBerhasilDriver(@PathVariable String uuid,@PathVariable Long id, Model model) {
        DriverModel driver = driverService.getDriverByUuid(uuid);
        RequestModel request = requestService.getRequestById(id);
        driver.setStatus(1);
        request.setDriver(driver);
        DriverModel updateDriver = driverService.updateDriver(driver);
        RequestModel updateRequest = requestService.updateRequest(request);
        model.addAttribute("driver", updateDriver);
        model.addAttribute("request", updateRequest);
        return "driver/update";
    }

    @PostMapping("/update-sudah/{uuid}")
    public String updateSudahDitugaskanDriver(@PathVariable String uuid, Model model) {
        DriverModel driver = driverService.getDriverByUuid(uuid);
        driver.setStatus(3);
        DriverModel sudahDitugaskanDriver = driverService.updateDriver(driver);
        model.addAttribute("driver", sudahDitugaskanDriver);
        return "driver/update";
    }
}
