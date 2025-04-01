package com.gaurav.emailSending.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.xml.transform.Templates;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendWelcomeEmail(String to,String name,String activationLink) throws MessagingException {
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);
        helper.setTo(to);
        helper.setSubject("Welcome to Our App!");

        Context context=new Context();
        context.setVariable("name",name);
        context.setVariable("activationLink",activationLink);

        String htmlContent= templateEngine.process("welcome-email",context);
        helper.setText(htmlContent,true);

        mailSender.send(message);
    }

    public void sendUpdateEmail(String to, String name,String updatedName) throws MessagingException {
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);
        helper.setTo(to);
        helper.setSubject("Profile Update Alert");
        Context context=new Context();
        context.setVariable("name",name);
        context.setVariable("name2",updatedName);
        String htmlContent=templateEngine.process("update-email",context);
        helper.setText(htmlContent,true);
        mailSender.send(message);
    }
}
