/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LeaveRequest.LeaveRequest.serviceInterface.serviceinterfaceimpl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

/**
 *
 * @author Panji Sadewo
 */
@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration configuration;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    
    public void sendEmailService() throws Exception{
        
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        Map<String, Object> model = new HashMap();
        model.put("user", "ini isinya lho");
        configuration.setClassForTemplateLoading(this.getClass(), "/");
        
        Template t = configuration.getTemplate("email.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        
        helper.setTo("revilghost@gmail.com");
        helper.setText(text, true);
        helper.setFrom("revilghost@gmail.com");
        helper.setSubject("ini contoh lho");

        javaMailSender.send(message);
        
        
    }

}
