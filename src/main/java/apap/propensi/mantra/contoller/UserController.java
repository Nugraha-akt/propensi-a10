package apap.propensi.mantra.contoller;


import apap.propensi.mantra.helper.PasswordChangeForm;
import apap.propensi.mantra.model.*;
import apap.propensi.mantra.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private CustomerServiceService customerServiceService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private CustomerService customerService;


    @GetMapping(value = "/viewall")
    public String listUser(Model model) {
        List<UserModel> listUser = userService.getListUserSortedByRole();

        model.addAttribute("listUser", listUser);
        return "user management/viewall-user";
    }

    @GetMapping(value = "/add")
    private String addUserFormPage(@RequestParam(value = "role", required = false) String role, Model model) {
        if (role == null) {
            return "user management/form-add-user";
        }

        if (role.equals("ADMIN")) {
            AdminModel user = new AdminModel();
            model.addAttribute("user", user);
            model.addAttribute("role", "Admin");
            return "user management/form-add-user";

        } else if (role.equals("MANAGER")) {
            ManagerModel user = new ManagerModel();
            model.addAttribute("user", user);
            model.addAttribute("role", "Manager");
            return "user management/form-add-user";

        } else if (role.equals("CUSTOMER")) {
            CustomerModel user = new CustomerModel();
            model.addAttribute("user", user);
            model.addAttribute("role", "Customer");
            return "user management/form-add-user";

        } else if (role.equals("DRIVER")) {
            DriverModel user = new DriverModel();
            model.addAttribute("user", user);
            model.addAttribute("role", "Driver");
            return "user management/form-add-user";

        } else if (role.equals("CUSTOMERSERVICE")) {
            CustomerServiceModel user = new CustomerServiceModel();
            model.addAttribute("user", user);
            model.addAttribute("role", "Customer Service");
            return "user management/form-add-user";

        }
        return "user management/form-add-user";
    }

    @PostMapping(value = "/add/admin")
    private String addAdminSubmitPage(@ModelAttribute AdminModel user, Model model){
        model.addAttribute("user", user);

        if (userService.getAllUsername().contains(user.getUsername())) {
            return "user management/duplicate-username";
        } else if (userService.getAllEmail().contains(user.getEmail())) {
            return "user management/duplicate-email";
        }
        adminService.addAdmin(user);

        return "redirect:/user/viewall";
    }

    @PostMapping(value = "/add/manager")
    private String addManagerSubmitPage(@ModelAttribute ManagerModel user, Model model){
        model.addAttribute("user", user);

        if (userService.getAllUsername().contains(user.getUsername())) {
            return "user management/duplicate-username";
        } else if (userService.getAllEmail().contains(user.getEmail())) {
            return "user management/duplicate-email";
        }
        managerService.addManager(user);

        return "redirect:/user/viewall";
    }

    @PostMapping(value = "/add/customerservice")
    private String addCustomerServiceSubmitPage(@ModelAttribute CustomerServiceModel user, Model model){
        model.addAttribute("user", user);

        if (userService.getAllUsername().contains(user.getUsername())) {
            return "user management/duplicate-username";
        } else if (userService.getAllEmail().contains(user.getEmail())) {
            return "user management/duplicate-email";
        }
        customerServiceService.addCustomerService(user);

        return "redirect:/user/viewall";
    }

    @PostMapping(value = "/add/driver")
    private String addDriverServiceSubmitPage(@ModelAttribute DriverModel user, Model model){
        model.addAttribute("user", user);

        if (userService.getAllUsername().contains(user.getUsername())) {
            return "user management/duplicate-username";
        } else if (userService.getAllEmail().contains(user.getEmail())) {
            return "user management/duplicate-email";
        }
        driverService.addDriver(user);

        return "redirect:/user/viewall";
    }

    @PostMapping(value = "/add/customer")
    private String addCustomerSubmitPage(@ModelAttribute CustomerModel user, Model model){
        model.addAttribute("user", user);

        if (userService.getAllUsername().contains(user.getUsername())) {
            return "user management/duplicate-username";
        } else if (userService.getAllEmail().contains(user.getEmail())) {
            return "user management/duplicate-email";
        }
        customerService.addCustomer(user);

        return "redirect:/user/viewall";
    }

    @GetMapping("/detail")
    public String viewUserDetailPage(@RequestParam(value = "uuid") String uuid, Model model) {
        if (userService.getUserByUuid(uuid) == null) {
            model.addAttribute("uuid", uuid);
            return "user management/user-not-found";
        }
        Role role = userService.getUserByUuid(uuid).getRole();

        if (role.toString().equals("ADMIN")) {
            AdminModel user = adminService.getAdminByUuid(uuid);
            model.addAttribute("user", user);

        } else if (role.toString().equals("MANAGER")) {
            ManagerModel user = managerService.getManagerByUuid(uuid);
            model.addAttribute("user", user);

        } else if (role.toString().equals("CUSTOMER")) {
            CustomerModel user = customerService.getCustomerByUuid(uuid);
            model.addAttribute("user", user);

        } else if (role.toString().equals("DRIVER")) {
            DriverModel user = driverService.getDriverByUuid(uuid);
            model.addAttribute("user", user);

        } else if (role.toString().equals("CUSTOMERSERVICE")) {
            CustomerServiceModel user = customerServiceService.getCustomerServiceByUuid(uuid);
            model.addAttribute("user", user);

        } else {
            return "user management/user-not-found";
        }

        return "user management/view-user-detail";
    }

    @GetMapping("/update/{uuid}")
    public String updateUserFormPage(@PathVariable String uuid, Model model) {
        if (userService.getUserByUuid(uuid) == null) {
            model.addAttribute("uuid", uuid);
            return "user management/user-not-found";
        }
        Role role = userService.getUserByUuid(uuid).getRole();

        if (role.toString().equals("ADMIN")) {
            AdminModel user = adminService.getAdminByUuid(uuid);
            model.addAttribute("user", user);
            return "user management/form-update-user";

        } else if (role.toString().equals("MANAGER")) {
            ManagerModel user = managerService.getManagerByUuid(uuid);
            model.addAttribute("user", user);
            return "user management/form-update-user";

        } else if (role.toString().equals("CUSTOMER")) {
            CustomerModel user = customerService.getCustomerByUuid(uuid);
            model.addAttribute("user", user);
            return "user management/form-update-user";

        } else if (role.toString().equals("DRIVER")) {
            DriverModel user = driverService.getDriverByUuid(uuid);
            model.addAttribute("user", user);
            return "user management/form-update-user";

        } else if (role.toString().equals("CUSTOMERSERVICE")) {
            CustomerServiceModel user = customerServiceService.getCustomerServiceByUuid(uuid);
            model.addAttribute("user", user);
            return "user management/form-update-user";

        }
        model.addAttribute("uuid", uuid);
        return "user management/user-not-found";
    }

    @PostMapping(value = "/update/admin")
    private String updateAdminSubmitPage(@ModelAttribute AdminModel user, Model model){
        model.addAttribute("user", user);

        if (userService.isUniqueUpdate(user).equals("duplicate-username")) {
            return "user management/duplicate-username";
        } else if (userService.isUniqueUpdate(user).equals("duplicate-email")) {
            return "user management/duplicate-email";
        }

        adminService.updateAdmin(user);

        return "redirect:/user/viewall";
    }

    @PostMapping(value = "/update/manager")
    private String updateManagerSubmitPage(@ModelAttribute ManagerModel user, Model model){
        model.addAttribute("user", user);

        if (userService.isUniqueUpdate(user).equals("duplicate-username")) {
            return "user management/duplicate-username";
        } else if (userService.isUniqueUpdate(user).equals("duplicate-email")) {
            return "user management/duplicate-email";
        }

        managerService.updateManager(user);

        return "redirect:/user/viewall";
    }

    @PostMapping(value = "/update/customerservice")
    private String updateCustomerServiceSubmitPage(@ModelAttribute CustomerServiceModel user, Model model){
        model.addAttribute("user", user);

        if (userService.isUniqueUpdate(user).equals("duplicate-username")) {
            return "user management/duplicate-username";
        } else if (userService.isUniqueUpdate(user).equals("duplicate-email")) {
            return "user management/duplicate-email";
        }

        customerServiceService.updateCustomerService(user);

        return "redirect:/user/viewall";
    }

    @PostMapping(value = "/update/driver")
    private String updateDriverSubmitPage(@ModelAttribute DriverModel user, Model model){
        model.addAttribute("user", user);

        if (userService.isUniqueUpdate(user).equals("duplicate-username")) {
            return "user management/duplicate-username";
        } else if (userService.isUniqueUpdate(user).equals("duplicate-email")) {
            return "user management/duplicate-email";
        }

        driverService.updateDriver(user);

        return "redirect:/user/viewall";
    }

    @PostMapping(value = "/update/customer")
    private String updateCustomerSubmitPage(@ModelAttribute CustomerModel user, Model model){
        model.addAttribute("user", user);

        if (userService.isUniqueUpdate(user).equals("duplicate-username")) {
            return "user management/duplicate-username";
        } else if (userService.isUniqueUpdate(user).equals("duplicate-email")) {
            return "user management/duplicate-email";
        }

        customerService.updateCustomer(user);

        return "redirect:/user/viewall";
    }

    @GetMapping("/delete/{uuid}")
    public String deleteUserConfirmationPage(@PathVariable String uuid, Model model) {
        if (userService.getUserByUuid(uuid) == null) {
            model.addAttribute("uuid", uuid);
            return "user management/user-not-found";
        }
        UserModel user = userService.getUserByUuid(uuid);
        model.addAttribute("user", user);

        return "user management/delete-user-confirm";
    }

    @GetMapping("/delete/{uuid}/confirm")
    public String deleteUserSubmitPage(@PathVariable String uuid, Model model) {
        if (userService.getUserByUuid(uuid) == null) {
            model.addAttribute("uuid", uuid);
            return "user management/user-not-found";
        }
        UserModel user = userService.getUserByUuid(uuid);
        userService.deleteUser(user);

        model.addAttribute("user", user);
        return "user management/delete-user";
    }

    @GetMapping("/change-password")
    public String changePasswordPage(Model model) {
        model.addAttribute("passwordChangeForm", new PasswordChangeForm());
        return "user management/change-password-form";
    }

    @PostMapping("/change-password")
    public String changePasswordSubmitPage(@ModelAttribute PasswordChangeForm passwordChangeForm, Principal principal, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user management/change-password-form";
        }

        UserModel user = userService.getUserByUsername(principal.getName());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // old password validation
        if (!encoder.matches(passwordChangeForm.getOldPassword(), user.getPassword())) {
            model.addAttribute("errorMsg", "Invalid password");
            return "user management/change-password-form";
        }

        // new password matches with new password confirm validation
        if (!passwordChangeForm.getNewPassword().equals(passwordChangeForm.getNewPasswordConfirm())) {
            model.addAttribute("errorMsg", "New password didn't match");
            return "user management/change-password-form";
        }

        user.setPassword(encoder.encode(passwordChangeForm.getNewPassword()));
        userService.updateUser(user);

        return "user management/change-password";
    }

    @GetMapping("/summary")
    public String summaryUserPage(Model model) {
        Integer jumlahAdmin = adminService.getListAdmin().size();
        Integer jumlahManager = managerService.getListManager().size();
        Integer jumlahDriver = driverService.getListDriver().size();
        Integer jumlahCustomerService = customerServiceService.getListCustomerService().size();
        Integer jumlahCustomer = customerService.getListCustomer().size();

        model.addAttribute("jumlahAdmin", jumlahAdmin);
        model.addAttribute("jumlahManager", jumlahManager);
        model.addAttribute("jumlahDriver", jumlahDriver);
        model.addAttribute("jumlahCustomerService", jumlahCustomerService);
        model.addAttribute("jumlahCustomer", jumlahCustomer);

        return "user management/user-summary";
    }

    @GetMapping("/update-profile/{uuid}")
    public String updateProfileFormPage(@PathVariable String uuid, Model model) {
        UserModel user = userService.getUserByUuid(uuid);
        model.addAttribute("user", user);

        return "data-diri/form-update-profile";
    }

    @PostMapping(value = "/update-profile")
    private String updateProfileSubmitPage(@ModelAttribute UserModel user, Model model){
        UserModel updatedUser = userService.getUserByUuid(user.getUuid());
        updatedUser.setNama(user.getNama());
        updatedUser.setNoTelepon(user.getNoTelepon());
        updatedUser.setEmail(user.getEmail());

        userService.updateProfile(updatedUser);
        model.addAttribute("user", updatedUser);


        return "data-diri/update-profile";
    }

}
