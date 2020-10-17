package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.model.service.MailService;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailServiceImpl implements MailService {
    Properties properties = new Properties();
    {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("mail.properties");
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendVerificationCode(String mailTo, String code) {
        String subject = "Verification code";
        MailSender sender = new MailSender(mailTo, subject, code, properties);
        sender.send();
    }

    @Override
    public void recoverPassword(String mailTo, String newPassword) {
        String subject = "New password";
        MailSender sender = new MailSender(mailTo, subject, newPassword, properties);
        sender.send();
    }

    public static Session createSession(Properties configProperties) {
        String userName = configProperties.getProperty("mail.user.name");
        String userPassword = configProperties.getProperty("mail.user.password");
        return Session.getDefaultInstance(configProperties,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, userPassword);
                    }
                });
    }

    public class MailSender {
        private MimeMessage message;
        private String sendToEmail;
        private String mailSubject;
        private String mailText;
        private Properties properties;

        public MailSender(String sendToEmail, String mailSubject, String mailText,
                          Properties properties) {
            this.sendToEmail = sendToEmail;
            this.mailSubject = mailSubject;
            this.mailText = mailText;
            this.properties = properties;
        }

        public void send() {
            try {
                initMessage();
                Transport.send(message);
            } catch (AddressException e) {
                System.err.println("Invalid address: " + sendToEmail + "  " + e);
            } catch (MessagingException e) {
                System.err.println("Error generating or sending message: " + e);
            }
        }

        private void initMessage() throws MessagingException {
            Session mailSession = createSession(properties);
            mailSession.setDebug(true);
            message = new MimeMessage(mailSession);
            message.setSubject(mailSubject);
            message.setContent(mailText, "text/html");
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));
        }
    }
}
