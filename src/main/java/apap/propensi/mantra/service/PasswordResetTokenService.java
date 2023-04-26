package apap.propensi.mantra.service;

import apap.propensi.mantra.model.PasswordResetToken;

public interface PasswordResetTokenService {
    PasswordResetToken findByToken(String token);
    PasswordResetToken save(PasswordResetToken passwordResetToken);
}
