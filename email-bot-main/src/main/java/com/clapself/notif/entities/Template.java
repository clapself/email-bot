//package com.clapself.notif.entities;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "templates")
//
//public class Template {
//
//    //    ----------------------------------------------------------
//    //    Data members
//    //    ----------------------------------------------------------
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int id;
//
//    @Column(name = "template_type", nullable = false)
//    private String templateType;
//
//    @Column(name = "version", nullable = false)
//    private String version;
//
////    ----------------------------------------------------------
////    Constructor
////    ----------------------------------------------------------
//
//    public Template() {
//        this.id = 0;
//        this.templateType = null;
//        this.version = null;
//    }
//
//    //    ----------------------------------------------------------
//    // Methods(Getter and Setters)
//    //    ----------------------------------------------------------
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getTemplateType() {
//        return templateType;
//    }
//
//    public void setTemplateType(String templateType) {
//        this.templateType = templateType;
//    }
//
//    public String getVersion() {
//        return version;
//    }
//
//    public void setVersion(String version) {
//        this.version = version;
//    }
//
//}
