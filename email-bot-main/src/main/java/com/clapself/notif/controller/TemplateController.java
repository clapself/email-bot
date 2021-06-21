//package com.clapself.notif.controller;
//import java.util.List;
//
//import com.clapself.notif.entities.Template;
//import com.clapself.notif.repository.TemplateRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//
//
//
//@RestController
//@RequestMapping( "/api" )
//@CrossOrigin(origins="http://localhost:3000")
//public class TemplateController {
//    @Autowired
//    TemplateRepository templateRepository;
//
//    @GetMapping("/templates")
//    public List<Template> getTemplates(){
//
//        return this.templateRepository.findAll();
//    }
//
//    @GetMapping("/templates/{id}")
//    public ResponseEntity<Template> getUsersById(@PathVariable(value = "id") Long templateID)
//            throws ResourceNotFoundException {
//        Template template = templateRepository.findById(templateID).orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + templateID));
//        return ResponseEntity.ok().body(template);
//    }
//
//}
