package apap.propensi.mantra.service;

import apap.propensi.mantra.model.Mail;

public interface EmailService {
    void send(Mail mail);
}
