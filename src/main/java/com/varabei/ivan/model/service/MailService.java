package com.varabei.ivan.model.service;

public interface MailService {
    void sendVerificationCode(String email, String code);

    void recoverPassword(String email, String newPassword);
}
