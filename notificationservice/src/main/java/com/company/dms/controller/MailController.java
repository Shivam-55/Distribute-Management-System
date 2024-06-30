package com.company.dms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.company.dms.Model.MailStructure;
import com.company.dms.service.MailService;
import com.company.dms.utils.logger.LoggableClass;
import com.company.dms.utils.logger.LoggableMethod;

/**
 * Controller class to handle mail-related endpoints.
 */
@LoggableClass
@RestController
@RequestMapping("/mail")
public class MailController {
    private final MailService mailService;
    @Autowired
    public MailController(MailService mailService){
        this.mailService = mailService;
    }
    /**
     * Endpoint to send mail.
     * @param mail The email address to send the mail to.
     * @param mailStructure The structure of the mail to be sent.
     * @return A message indicating the successful sending of the mail.
     */
    @LoggableMethod
    @PostMapping("/send/{mail}")
    public String sendMail(@PathVariable String mail, @RequestBody MailStructure mailStructure){
        mailService.sendMail(mail,mailStructure);
        return "successfully send the mail" ;
    }
}
