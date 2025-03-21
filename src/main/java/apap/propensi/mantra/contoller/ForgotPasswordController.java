package apap.propensi.mantra.contoller;

import apap.propensi.mantra.model.Mail;
import apap.propensi.mantra.model.PasswordForgot;
import apap.propensi.mantra.model.PasswordResetToken;
import apap.propensi.mantra.model.UserModel;
import apap.propensi.mantra.service.EmailService;
import apap.propensi.mantra.service.PasswordResetTokenService;
import apap.propensi.mantra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/forgot-password")
public class ForgotPasswordController {
    private final UserService userService;
    private final MessageSource messageSource;
    private final PasswordResetTokenService passwordResetTokenService;
    private final EmailService emailService;

    @Autowired
    public ForgotPasswordController(UserService userService, MessageSource messageSource, PasswordResetTokenService passwordResetTokenService, EmailService emailService) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.passwordResetTokenService = passwordResetTokenService;
        this.emailService = emailService;
    }

    @GetMapping
    public String viewPage(){
        return "auth/forgot-page";
    }

    @PostMapping
    public String processPasswordForgot(@Valid @ModelAttribute("passwordForgot") PasswordForgot passwordForgot,
                                        BindingResult result,
                                        Model model,
                                        RedirectAttributes attributes,
                                        HttpServletRequest request){
        if(result.hasErrors()){
            return "auth/forgot-page";
        }
        UserModel user = userService.getUserByEmail(passwordForgot.getEmail());
        if(user == null){
            model.addAttribute("emailError", messageSource.getMessage("EMAIL_NOT_FOUND", new Object[]{}, Locale.ENGLISH));
            return "auth/forgot-page";
        }
        // proceed to send email with link to reset password to this email address
        PasswordResetToken token = new PasswordResetToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpirationDate(LocalDateTime.now().plusMinutes(30));
        token = passwordResetTokenService.save(token);
        if(token == null){
            model.addAttribute("tokenError", messageSource.getMessage("TOKEN_NOT_SAVED", new Object[]{}, Locale.ENGLISH));
            return "auth/forgot-page";
        }
        Mail mail = new Mail();
        mail.setFrom("no-reply@mantra.transcorp");
        mail.setTo(user.getEmail());
        mail.setSubject("Password reset request");

        Map<String, Object> mailModel = new HashMap<>();
        mailModel.put("token", token);
        mailModel.put("user", user);
        mailModel.put("signature", "localhost:8080");
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        mailModel.put("resetUrl", url + "/reset-password?token=" + token.getToken());
        mail.setModel(mailModel);
        /* send email using emailService
        if email sent successfully redirect with flash attributes
         */
        emailService.send(mail);
        attributes.addFlashAttribute("success", messageSource.getMessage("PASSWORD_RESET_TOKEN_SENT", new Object[]{}, Locale.ENGLISH));
        return "redirect:/forgot-password";
    }

    @ModelAttribute("passwordForgot")
    public PasswordForgot passwordForgot(){
        return new PasswordForgot();
    }
}
