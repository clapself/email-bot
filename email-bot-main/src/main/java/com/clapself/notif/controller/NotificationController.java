package com.clapself.notif.controller;

import com.clapself.notif.entities.EmailData;
import com.clapself.notif.entities.User;
import com.clapself.notif.handler.EmailHandler;
import com.clapself.notif.repository.EmailRepository;
import com.clapself.notif.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping( "/api" )
@CrossOrigin(origins="http://localhost:3000")
public class NotificationController {

    @Autowired
    EmailHandler emailHandler;
    EmailRepository emailRepository;
    UserRepository userRepository;

    @PostMapping("/sendnotif")
 public void sendNotification(@Valid @RequestBody User user) {
    //first get user details, get template, message
    //populate email data object AND save it
    ///based on template id invoke the corresponding method in emailHandle
String toName=user.getName();
String recipient=user.getEmail();

        emailHandler.createAndSendRestPasswordTokenEmail(toName, recipient);
   }

    @PostMapping(value="/sendnotifs")
    public void sendNotification(@Valid @RequestBody List<User> users) {
        users.forEach(user->{
            String toName=user.getName();
            String recipient=user.getEmail();

            emailHandler.createAndSendRestPasswordTokenEmail(toName, recipient);
        });

    }




}