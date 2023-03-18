package apap.propensi.mantra.contoller;


import apap.propensi.mantra.model.Role;
import apap.propensi.mantra.model.UserModel;
import apap.propensi.mantra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private String addUserSubmit(@ModelAttribute UserModel user, String role, Model model){
        userService.addUser(user);

        model.addAttribute("user", user);
        return "redirect:/user/viewall";
    }
}
