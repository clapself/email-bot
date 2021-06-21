package com.clapself.notif.entities;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "email_status")
public class EmailData{


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
private Integer id;
//    protected String fromEmail;
@Column( name = "to_name")
protected String toName;

    @Column( name = "recipient")
    protected String recipient;
//    protected String fromName;
//    protected String subject;
//    protected String message;
//    protected String emailTypeId;


    public EmailData() {
id=0;
//        fromEmail= null;
        recipient=null;
//        fromName=null;
        toName=null;
//        subject=null;
//        message=null;
//        emailTypeId=null;
    }


//    public String getFromEmail() {
//        return fromEmail;
//    }
//
//    public void setFromEmail(String fromEmail) {
//        this.fromEmail = fromEmail;
//    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

//    public String getFromName() {
//        return fromName;
//    }
//
//    public void setFromName(String fromName) {
//        this.fromName = fromName;
//    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

//    public String getSubject() {
//        return subject;
//    }
//
//    public void setSubject(String subject) {
//        this.subject = subject;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public String getEmailTypeId() {
//        return emailTypeId;
//    }
//
//    public void setEmailTypeId(String emailTypeId) {
//        this.emailTypeId = emailTypeId;
//    }
}


