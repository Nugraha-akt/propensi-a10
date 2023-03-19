package apap.propensi.mantra.contoller;


import apap.propensi.mantra.model.Role;
import apap.propensi.mantra.model.UserModel;
import apap.propensi.mantra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(value = "/viewall")
    public String listUser(Model model) {
        List<UserModel> listUser = userService.getListUser();

        model.addAttribute("listUser", listUser);
        return "viewall-user";
    }

    @GetMapping(value = "/add")
    private String addUserFormPage(Model model){
        UserModel user = new UserModel();

        Role[] listRole = Role.values();

        model.addAttribute("user", user);
        model.addAttribute("listRole", listRole);
        return "form-add-user";
    }

    @PostMapping(value = "/add")
    private String addUserSubmit(@ModelAttribute UserModel user, Model model){
        userService.addUser(user);

        model.addAttribute("user", user);
        return "redirect:/user/viewall";
    }

    @GetMapping("/detail")
    public String viewUserDetail(@RequestParam(value = "uuid") String uuid, Model model) {
        if (userService.getUserByUuid(uuid) == null) {
            model.addAttribute("uuid", uuid);
            return "user-not-found";
        }
        UserModel user = userService.getUserByUuid(uuid);

        model.addAttribute("user", user);
        return "view-user-detail";
    }
}
