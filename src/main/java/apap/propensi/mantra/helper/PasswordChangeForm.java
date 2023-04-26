package apap.propensi.mantra.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeForm {
    private String oldPassword;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and be at least 8 characters long.")
    private String newPassword;
    private String newPasswordConfirm;
}
