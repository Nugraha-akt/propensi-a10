package apap.propensi.mantra.contoller;

import apap.propensi.mantra.model.PasswordReset;
import apap.propensi.mantra.model.PasswordResetToken;
import apap.propensi.mantra.model.UserModel;
import apap.propensi.mantra.service.PasswordResetTokenService;
import apap.propensi.mantra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Locale;

@Controller
@RequestMapping("/reset-password")
public class ResetPasswordController {
    private final PasswordResetTokenService tokenService;
    private final MessageSource messageSource;
    private final UserService userService;

    private static final String REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    @Autowired
    public ResetPasswordController(PasswordResetTokenService tokenService, MessageSource messageSource, UserService userService) {
        this.tokenService = tokenService;
        this.messageSource = messageSource;
        this.userService = userService;
    }

    @GetMapping
    public String viewPage(@RequestParam(name = "token", required = false) String token,
                           Model model){
        PasswordResetToken passwordResetToken = tokenService.findByToken(token);
        if(passwordResetToken == null){
            model.addAttribute("error", messageSource.getMessage("TOKEN_NOT_FOUND", new Object[]{}, Locale.ENGLISH));
        }else if(passwordResetToken.getExpirationDate().isBefore(LocalDateTime.now())){
            model.addAttribute("error", messageSource.getMessage("TOKEN_EXPIRED", new Object[]{}, Locale.ENGLISH));
        }else{
            model.addAttribute("token", passwordResetToken.getToken());
        }
        return "auth/reset-page";
    }

    @PostMapping
    public String resetPassword(@Valid @ModelAttribute("passwordReset") PasswordReset passwordReset,
                                BindingResult result,
                                RedirectAttributes attributes){

        if(result.hasErrors() || !passwordReset.getPassword().matches(REGEX)){
            attributes.addFlashAttribute("passwordReset", passwordReset);
            if (result.hasErrors()) {
                attributes.addFlashAttribute("error", messageSource.getMessage("PASSWORDS_NOT_EQUAL", new Object[]{}, Locale.ENGLISH));
            } else {
                attributes.addFlashAttribute("error", messageSource.getMessage("PASSWORD_CONSTRAINTS", new Object[]{}, Locale.ENGLISH));
            }
            return "redirect:/reset-password?token=" + passwordReset.getToken();
        }
        PasswordResetToken token = tokenService.findByToken(passwordReset.getToken());
        UserModel user = token.getUser();
        user.setPassword(passwordReset.getPassword());
        userService.updatePassword(user);
        attributes.addFlashAttribute("successMessage", "Reset Password Berhasil!");
        return "redirect:/login";
    }

    @ModelAttribute("passwordReset")
    public PasswordReset passwordReset(){
        return new PasswordReset();
    }

}
