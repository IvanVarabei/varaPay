package com.varabei.ivan.model.service;

public interface MailService {
    void sendEmail(String mailTo, String subject, String message);
}
