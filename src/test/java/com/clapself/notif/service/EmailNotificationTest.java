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
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailNotificationTest {
    
    @Autowired
    private NotificationService notificationService;
    
    @Value("${emailtemplate.emailresetpassword}")
    private String emailResetPasswordTemplate;  
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    public EmailNotificationTest() {
    }
    
    @Test
    public void createAndSendRestPasswordTokenEmail() {

        String fromName = "Clapself";
        Notification notification = new Notification();

        notification.setRecipient("testemail@testcompany.com");
        notification.setToName("Email Test");
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
              //fail("Error: notificationService not initialized. Check your settings");
            }
        } catch (Exception e) {
            String error = "Error in email for reset password token " + e.getMessage();
            System.out.println(error);
            fail(error);
        }
    }    
}
