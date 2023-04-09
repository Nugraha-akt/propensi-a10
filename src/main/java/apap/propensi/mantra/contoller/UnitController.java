package apap.propensi.mantra.contoller;

import apap.propensi.mantra.model.Role;
import apap.propensi.mantra.model.UnitModel;
import apap.propensi.mantra.model.UserModel;
import apap.propensi.mantra.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/unit")
public class UnitController {

    @Autowired
    private UnitService unitService;

    @GetMapping(value = "/viewall")
    public String listUnit(Model model) {
        List<UnitModel> listUser = unitService.getListUnit();

        model.addAttribute("listUnit", listUser);
        return "viewall-unit";
    }

    @GetMapping(value = "/add")
    private String addUnitFormPage(Model model){
        UnitModel unit = new UnitModel();
        int errorstatus = 0;

        model.addAttribute("unit", unit);
        model.addAttribute("errorstatus", errorstatus);
        return "form-add-unit";
    }

    @PostMapping(value = "/add")
    private String addUnitSubmit(@ModelAttribute UnitModel unit, Model model){
        if(unitService.getUnitByPlat(unit.getPlatNomor())!=null){
            int errorstatus = 1;
            model.addAttribute("unit", unit);
            model.addAttribute("errorstatus", errorstatus);
            return "form-add-unit";
        }
        unitService.addUnit(unit);
        return "redirect:/unit/viewall";
    }
    @GetMapping(value = "/view/{platNomor}")
    private String viewUnitPage(@PathVariable("platNomor") String plat, Model model){
        UnitModel unit = unitService.getUnitByPlat(plat);


        model.addAttribute("unit", unit);
        return "view-unit";
    }

    @GetMapping(value = "/update/{platNomor}")
    private String UpdateUnitFormPage(@PathVariable("platNomor") String plat, Model model){
        UnitModel unit = unitService.getUnitByPlat(plat);


        model.addAttribute("unit", unit);
        return "form-update-unit";
    }

    @PostMapping(value = "/update/{platNomor}")
    private String UpdateUnitSubmitPage(@PathVariable("platNomor") String plat,UnitModel unit, Model model){
        if(unitService.getUnitByPlat(unit.getPlatNomor())!=null){
            if(!unitService.getUnitById(unit.getId()).getPlatNomor().equals(unit.getPlatNomor())){
                int errorstatus = 1;
                model.addAttribute("unit", unit);
                model.addAttribute("errorstatus", errorstatus);
                return "form-update-unit";
            }
        }
        unitService.addUnit(unit);
        return "redirect:/unit/viewall";
    }
    @GetMapping(value = "/delete/{platNomor}")
    private String DeleteUnitFormPage(@PathVariable("platNomor") String plat, Model model){
        UnitModel unit = unitService.getUnitByPlat(plat);
//        if(unit.getRequest()==null){
//            unitService.deleteUnit(unit);
//        }
        return "redirect:/unit/viewall";
    }
}
