/**
 * Copyright 2021 Clapself
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.clapself.notif.service;

import com.clapself.notif.model.Notification;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Value("${emailtemplate.location}")
    private String templatePath;
    
    @Autowired
    private JavaMailSender mailSender;

    public static int noOfQuickServiceThreads = 20;

    /**
     * Error message for notification manager exception.
     */
    protected static final String TEMPLATE_NOT_FOUND_ERROR = "NotificationService:NotificationManagerException: Template Not Found Error";

    private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(noOfQuickServiceThreads);

    /**
     * Class variable for template map instance.
     */
    protected static HashMap templates = new HashMap();

    public NotificationService() {
    }

    public Notification processEmail(Notification notification) throws NotificationManagerException {
        String templateDirPath = templatePath;
        String templateFileName = null;

        if (templateDirPath != null) {
            templateFileName = templateDirPath + getTemplateById(notification.getEmailTypeId());
        } else {
            templateFileName = getTemplateById(notification.
                    getEmailTypeId());
        }

        notification = processMessage(notification,
                templateFileName);
        
        return notification;
    }

    /**
     * Gets template data from the hash table in the memory using typeId as key.
     *
     * @param emailType
     * @return
     * @throws NotificationManagerException
     */
    protected String getTemplateById(String emailType)
            throws NotificationManagerException {
        return emailType;
//        if (templates.containsKey(emailType)) {
//            return templates.get(emailType).toString();
//        } else {
//            throw new NotificationManagerException(TEMPLATE_NOT_FOUND_ERROR);
//        }
    }

    // Commenting the following code as it doesn't set the "Unsubscribe header withouth which the emails will get flagged as SPAM
    
//    public void sendTextEmail(String toEmail, String subject, String text)
//            throws MailException, RuntimeException {
//
//        SimpleMailMessage mail = new SimpleMailMessage();
//        mail.setFrom("clapself@clapself.net");
//        mail.setTo(toEmail);
//        mail.setSubject(subject);
//        mail.setText(text);
//   
//
//        try {
//            mailSender.send(mail);
//        } catch (MailException e) {
//            System.out.println("Failed to send the email: " + e.getMessage());
//        }
//
//    }
//
//    public void sendAsyncEmail(String toEmail, String subject, String text)
//            throws MailException, RuntimeException {
//   
//        SimpleMailMessage mail = new SimpleMailMessage();
//        mail.setFrom("clapself@clapself.net");
//        mail.setTo(toEmail);
//        mail.setSubject(subject);
//        mail.setText(text);
//
//        quickService.submit(() -> {
//            try {
//                mailSender.send(mail);
//            } catch (MailException e) {
//                System.out.println("Failed to send the email: " + e.getMessage());
//            }
//        });
//    }

    public void sendEmail(Notification notification) throws NotificationManagerException, MessagingException, InterruptedException {

        Notification notif = processEmail(notification);
        
        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mail, true);
        mail.addHeaderLine(templatePath);
        
       /**
        * /user/unsubscribe?usernameoremail=<USERNAME OR EMAIL>
        * mail.addHeader("List-Unsubscribe", "<mailto:unsubscribe@clapself.com?subject=unsubscribe>");
        * String unsub = "<mailto: unsubscribe@clapself.com?subject=unsubscribe>, <https://www.clapself.com/user/unsubscribe?usernameoremail=";
        * unsub += notification.getRecipient() + ">";
        * mail.addHeader("List-Unsubscribe", String.format("<{0}>", unsub));
        */
 
        
        messageHelper.setFrom(notif.getFromEmail());
        
        try {   
            InternetAddress addr = new InternetAddress(notif.getFromEmail(), notif.getFromName());
        
            messageHelper.setFrom(addr);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            //Ignore as it will default to from email
        }
    
        messageHelper.setTo(notif.getRecipient());
        messageHelper.setSubject(notif.getSubject());
        messageHelper.setText(notif.getMessage(), true);

        System.out.println("Sending email...");
        System.out.println("From: " + notif.getFromEmail() + "--" + notif.getFromName());
        System.out.println("To: " + notif.getRecipient() + "--" + notif.getToName());
        System.out.println("Subject: " + notif.getSubject());
        System.out.println("Message: " + notif.getMessage());
       
        
        quickService.submit(() -> {
            try {
                System.out.println("Sendingggg email...");
                mailSender.send(mail);
                System.out.println("Email sent...");
            } catch (MailException e) {
                e.printStackTrace();
                System.out.println("Failed to send the email: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error sending the email: " + e.getMessage());
            }
        });
        
        Thread.sleep(20000);
         System.out.println("Email senttttt...");
    }


    protected Notification processMessage(Notification notification,
            String templateFileName) throws NotificationManagerException {
        
        NotificationTemplateProcessor templateProcessor = new NotificationTemplateProcessor();
        Notification processedNotification;
        
        try {
            notification.setParameters(notification.getParameters());
        } catch (Exception e) {
            throw new NotificationManagerException(e.getMessage());
        }
        
        processedNotification = templateProcessor.processTemplate(
                templateFileName, notification);

        if (notification.getFormat().equals(Notification.ALERT_TYPE_SMS)) {
            notification.setMessage(processedNotification.getMessage());
        } else {
            notification.setMessage(processedNotification.getMessage());
        }

        if (processedNotification.getFromEmail() != null) {
            notification.setFromEmail(processedNotification.getFromEmail());
        }

        if (processedNotification.getFromName() != null) {
            notification.setFromName(processedNotification.getFromName());
        }

        if (processedNotification.getRecipient() != null) {
            notification.setRecipient(processedNotification.getRecipient());
        }

        if (processedNotification.getToName() != null) {
            notification.setToName(processedNotification.getToName());
        }

        if (processedNotification.getSubject() != null) {
            notification.setSubject(processedNotification.getSubject());
        }

        return notification;
    }
}
