package com.company.dms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.company.dms.Model.MailStructure;
import com.company.dms.utils.logger.LoggableClass;
import com.company.dms.utils.logger.LoggableMethod;

/**
 * Service class responsible for sending emails.
 */
@LoggableClass
@Service
public class MailService {
    @Value("${spring.mail.username}")
    private String fromMail;
    private final JavaMailSender javaMailSender;
    @Autowired
    public MailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    /**
     * Sends an email using the provided mail structure.
     * @param mail The recipient email address.
     * @param mailStructure The structure of the email including subject and message.
     */
    @LoggableMethod
    public void sendMail(String mail, MailStructure mailStructure){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject(mailStructure.getSubject());
        simpleMailMessage.setText(mailStructure.getMessage());
        simpleMailMessage.setTo(mail);
        javaMailSender.send(simpleMailMessage);
    }
}
