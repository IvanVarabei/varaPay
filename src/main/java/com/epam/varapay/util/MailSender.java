package com.epam.varapay.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailServiceImpl {
    private static final Logger log = LogManager.getLogger(MailServiceImpl.class);
    private static final MailServiceImpl instance = new MailServiceImpl();
    private static Session session;
    private static final String MAIL_PROPERTIES_FILE = "mail.properties";
    private static final String MAIL_USER_NAME_KEY = "mail.user.name";
    private static final String MAIL_PASSWORD_KEY = "mail.user.password";
    private static final String CONTENT_TYPE = "text/html";

    private MailServiceImpl() {
    }

    public static MailServiceImpl getInstance() {
        return instance;
    }

    static {
        try (InputStream is = MailServiceImpl.class.getClassLoader().getResourceAsStream(MAIL_PROPERTIES_FILE)) {
            Properties properties = new Properties();
            properties.load(is);
            String userName = properties.getProperty(MAIL_USER_NAME_KEY);
            String userPassword = properties.getProperty(MAIL_PASSWORD_KEY);
            session = Session.getDefaultInstance(properties,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(userName, userPassword);
                        }
                    });
        } catch (IOException e) {
            log.error("could`t read mail.properties", e);
        }
    }

    public void sendEmail(String mailTo, String subject, String message) {
        MailSender sender = new MailSender(mailTo, subject, message);
        sender.send();
    }

    private static class MailSender {
        private MimeMessage message;
        private String sendToEmail;
        private String mailSubject;
        private String mailText;

        public MailSender(String sendToEmail, String mailSubject, String mailText) {
            this.sendToEmail = sendToEmail;
            this.mailSubject = mailSubject;
            this.mailText = mailText;
        }

        public void send() {
            try {
                initMessage();
                Transport.send(message);
            } catch (AddressException e) {
                log.error(String.format("Invalid address: %s", sendToEmail), e);
            } catch (MessagingException e) {
                log.error("Error generating or sending message: ", e);
            }
        }

        private void initMessage() throws MessagingException {
            message = new MimeMessage(session);
            message.setSubject(mailSubject);
            message.setContent(mailText, CONTENT_TYPE);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));
        }
    }
}
