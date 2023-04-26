package apap.propensi.mantra.service;

import apap.propensi.mantra.repository.PasswordResetTokenDb;
import apap.propensi.mantra.model.PasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    private final PasswordResetTokenDb passwordResetTokenDb;

    @Autowired
    public PasswordResetTokenServiceImpl(PasswordResetTokenDb passwordResetTokenDb) {
        this.passwordResetTokenDb = passwordResetTokenDb;
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenDb.findByToken(token).orElse(null);
    }

    @Override
    public PasswordResetToken save(PasswordResetToken passwordResetToken) {
        return passwordResetTokenDb.save(passwordResetToken);
    }
}
