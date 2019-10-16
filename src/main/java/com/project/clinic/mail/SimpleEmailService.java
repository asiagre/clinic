package com.project.clinic.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(Mail mail) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(mail.getReceiverEmail());
            mailMessage.setSubject(mail.getSubject());
            mailMessage.setText(mail.getMessage());
            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}
