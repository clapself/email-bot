package com.clapself.notif.handler;

import com.clapself.notif.entities.User;
import com.clapself.notif.model.Notification;
import com.clapself.notif.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailHandler {
    @Autowired
    NotificationService notificationService;

    @Value("${emailtemplate.emailresetpassword}")
    private String emailResetPasswordTemplate;

    public void createAndSendRestPasswordTokenEmail(String toName, String recipient) {

        String fromName="Clapself";
        Notification notification = new Notification();

        notification.setRecipient(recipient);//("amit83pandey@gmail.com");//("testclaps@gmail.com");
        notification.setToName(toName);
        notification.setEmailTypeId(emailResetPasswordTemplate);
        notification.setFromName(fromName);


        notification.addNonRepeatingParameter("UserFullName", "Target User Name");
        notification.addNonRepeatingParameter("ResetPasswordToken", "justadummytoken");

        String unsubUrl = "http://www.sampleurl.com" + "/users/" + notification.getRecipient() + "/unsubscribe";
        notification.addNonRepeatingParameter("UnsubscribeUrl", unsubUrl);

        try {
            if (notificationService != null) {
                notificationService.sendEmail(notification);
            }
            else {
                //  fail("Error: notificationService not initialized. Check your settings");
            }
        } catch (Exception e) {
            e.printStackTrace();
            String error = "Error in email for reset password token " + e.getMessage();
            System.out.println(error);
//            fail(error);
        }
    }
}
