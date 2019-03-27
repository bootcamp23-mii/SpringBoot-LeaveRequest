
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl;

/**
 *
 * @author AdhityaWP
 */


import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;


@Service
public class MailService {
    private JavaMailSender javaMailSender;
    
    @Autowired
    private Configuration configuration;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String destination,String Subject,String title,String user,String content,String link) throws MessagingException, MalformedTemplateNameException, IOException, TemplateException{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String,Object> model = new HashMap<>();
        
        model.put("user", user);
        model.put("title", title);
        model.put("message", content);
        model.put("link", link);
        configuration.setClassForTemplateLoading(this.getClass(), "/");
        
        Template t = configuration.getTemplate("email.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        
        helper.setTo(destination);
        helper.setText(text,true);
        helper.setFrom("lgg121770@gmail.com");
        helper.setSubject(Subject);
        
        javaMailSender.send(message);
    }

}