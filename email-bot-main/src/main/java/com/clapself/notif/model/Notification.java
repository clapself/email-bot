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
package com.clapself.notif.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;

/**
 * This class represents an email or SMS notification.
 */
public class Notification implements Serializable {

    /**
     * Serial version number
     */
    private static final long serialVersionUID = 6024334513681669235L;
    /**
     * Constant for equals to
     */
    private static final String EQUALSTO = "=";
    /**
     * Constant for dot
     */
    public static final String DOT = ".";
    /**
     * Constant for semicolon
     */
    public static final String SEMI_COLON = ";";
    /**
     * This is the end delimiter of the key string in template.
     */
    private static final String END_BLOCK_DELIMITER = "]";
    /**
     * This is the constant for the opening bracket.
     */
    private static final String OPENING_BRACKET = "[";
    /**
     * This is the constant for comma ("," ).
     */
    public static final String COMMA = ",";
    /**
     * This is the constant for double backward slashes ("\\" ).
     */
    public static final String SLASH = "\\";
    /**
     * This is the constant for colon (":" ).
     */
    public static final String COLON = ":";
    /**
     * This is the constant for status NW.
     */
    public static final String STATUS_NEW = "NW";
    /**
     * This is the constant for priority 'PMO'.
     */
    public static final String PRIORITY_PMO = "PMO";
    /**
     * Constant String for email alert type.
     */
    public final static String ALERT_TYPE_EMAIL = "EMAIL";
    /**
     * Constant String for SMS alert type.
     */
    public final static String ALERT_TYPE_SMS = "SMS";

    public final static int SMS_CONTENT_LENGTH = 130;
    //-------------------------------------------------------------------------
    //  Data Member(s)
    //-------------------------------------------------------------------------
    /**
     * <code>fromEmail</code> holds the email address of sender
     */
    protected String fromEmail;

    /**
     * <code>toEmail</code> holds the email address of recipient
     */
    protected String recipient;

    /**
     * <code>fromName</code> holds the name of sender
     */
    protected String fromName;

    /**
     * <code>toName</code> holds the name of receiver
     */
    protected String toName;

    /**
     * <code>subject</code> holds the subject of message.
     */
    protected String subject;

    /**
     * <code>message</code> holds the <code>String</code> message to be sent.
     */
    protected String message;

    /**
     * <code>parameters</code> holds the <code>String</code> parameters to be
     * used to construct message.
     */
    protected String parameters = "";

    /*
     * String containing repeating parameters.
     */
    protected String repeatingParameters = "";

    /*
     * A map containing the repeating blocks.
     */
    protected HashMap repeatingBlocks = null;

    /*
     * A map containing the non repeating blocks.
     */
    protected HashMap nonRepeatingParametersTable = null;

    /*
     * String containing the non repeating parameters.
     */
    protected String nonRepeatingParameters = "";

    /**
     * <code>attachments</code> holds path of all files to be send
     */
    protected ArrayList<String> attachments;

    /**
     * <code>ccList</code> holds the cc list of recipients.
     */
    protected ArrayList<String> ccList;

    /**
     * <code>bccList</code> holds the bcc list of recipients.
     */
    protected ArrayList<String> bccList;

    /**
     * <code>dateSent</code> holds sending date.
     */
    protected Date dateSent;

    /**
     * <code>emailType</code> holds type of email.
     */
    protected String emailTypeId;

    /**
     * <code>status</code> holds status of email.
     */
    protected String status;

    /**
     * <code>format</code> holds format of notification.
     */
    protected String format;

    /**
     * <code>modTime</code> holds time of modification.
     */
    protected String modTime;

    /**
     * <code>id</code> holds unique identification number.
     */
    protected int notificationid;

    /**
     * Variable to hold the CountryCode.
     */
    protected String countryCode = "IN";


    //-------------------------------------------------------------------------
    //  Constructor(s)
    //-------------------------------------------------------------------------
    /**
     * Default constructor which initializes the object variables to null.
     */
    public Notification() {
        notificationid = 0;
        dateSent = null;
        emailTypeId= null;
        status = null;
        modTime = null;
        fromEmail = null;
        recipient = null;
        fromName = null;
        toName = null;
        subject = null;
        message = null;
        attachments = new ArrayList();
        ccList = new ArrayList();
        bccList = new ArrayList();
        repeatingBlocks = new HashMap();
        nonRepeatingParametersTable = new HashMap();
        format = ALERT_TYPE_EMAIL;
    }

    //-------------------------------------------------------------------------
    //  Method(s)
    //-------------------------------------------------------------------------
    /**
     * Resets the data members of object to null.
     */
    public void reset() {
        notificationid = 0;
        dateSent = null;
        emailTypeId = null;
        status = null;
        modTime = null;
        fromEmail = null;
        recipient = null;
        fromName = null;
        toName = null;
        subject = null;
        message = null;
        attachments.clear();
        ccList.clear();
        bccList.clear();
        format = ALERT_TYPE_EMAIL;
    }

    /**
     * Dumps the data members to sys out.
     */
    public void dump() {
        System.out.println("NotificationId:" + notificationid);
        System.out.println("DateSent:" + dateSent);
        System.out.println("EmailTypeId:" + emailTypeId);
        System.out.println("Status:" + status);
        System.out.println("ModTime:" + modTime);
        System.out.println("FromEmail:" + fromEmail);
        System.out.println("ToRecipient:" + recipient);
        System.out.println("FromName:" + fromName);
        System.out.println("ToName:" + toName);
        System.out.println("Subject:" + subject);
        System.out.println("Parameters:" + getParameters());
        System.out.println("Message:" + getMessage());
        System.out.println("Format:" + format);
    }

    //-------------------------------------------------------------------------
    //  Accessor(s)
    //-------------------------------------------------------------------------
    /**
     * Gets the unique identification number from the database.
     *
     * @return	unique identification number from the database.
     */
    public int getNotificationId() {
        return notificationid;
    }

    /**
     * Sets unique identification number from the database.
     *
     * @param id	unique identification number from the database.
     */
    public void setNotificationId(int id) {
        this.notificationid = id;
    }

    /**
     * Gets the date when notification is sent
     *
     * @return	sending date.
     */
    public Date getDateSent() {
        return dateSent;
    }

    /**
     * Sets the sending date of email.
     *
     * @param date	sending date of email.
     */
    public void setDateSent(Date date) {
        this.dateSent = date;
    }

    /**
     * Gives the email type.
     *
     * @return	email type.
     */
    public String getEmailTypeId() {
        return emailTypeId;
    }

    /**
     * Sets the email type.
     *
     * @param type	the email type.
     */
    public void setEmailTypeId(String type) {
        this.emailTypeId = type;
    }

    /**
     * Gives the email status.
     *
     * @return	email status.
     */
    public String getEmailStatus() {
        return status;
    }

    /**
     * Sets the email status.
     *
     * @param status	the email status.
     */
    public void setEmailStatus(String status) {
        this.status = status;
    }

    /**
     * Gives the notification format.
     *
     * @return	notification format.
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets the notification format.
     *
     * @param format	the notification format.
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Gives the modification time.
     *
     * @return	_modTime the modification time.
     */
    public String getModTime() {
        return modTime;
    }

    /**
     * Sets the modification time.
     *
     * @param modTime	The modification time.
     */
    public void setModTime(String modTime) {
        this.modTime = modTime;
    }

    /**
     * Gives the name of the sender
     *
     * @return	The name of the sender
     */
    public String getFromName() {
        return fromName;
    }

    /**
     * Sets the name of the sender, supplied as parameter.
     *
     * @param name The name of the sender
     */
    public void setFromName(String name) {
        this.fromName = name;
    }
    
    /**
     * Sets the receiver's name supplied as parameter.
     *
     * @param name	receiver's name to be set.
     */
    public void setToName(String name) {
        this.toName = name;
    }

    /**
     * Gives the receiver's name.
     *
     * @return	receiver's name.
     */
    public String getToName() {
        return toName;
    }
    
    /**
     * Gets the address of sender.
     *
     * @return fromEmail Senders Email address.
     */
    public String getFromEmail() {
        return fromEmail;
    }

    /**
     * Sets the address of sender from the parameter supplied.
     *
     * @param fromEmail Senders Email address.
     */
    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    /**
     * Gets the email/mobile of recipient.
     *
     * @return recipient recipient email/mobile.
     */
    public String getRecipient() {
        return this.recipient;
    }

    /**
     * Sets the email/mobile of recipient from the parameter supplied.
     *
     * @param recipient	Recipient email/mobile.
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * Gets the subject of message.
     *
     * @return subject Subject for the message.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject of message from the parameter supplied.
     *
     * @param subject Subject for the message.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets the <code>String</code> message to be sent.
     *
     * @return The email message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the <code>String</code> message to be sent.
     *
     * @param message	Message to be sent.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the <code>String</code> Parameters use to construct the message.
     *
     * @return Parameters use to construct the message.
     */
    public String getParameters() {
        if (parameters.equals("")) {
            parameters = getNonRepeatingParameters() + getRepeatingParameters();
        }
        return parameters;
    }

    /**
     * Sets the <code>String</code> message to be sent.
     *
     * @param parameters	Parameters use to construct the message.
     * @throws java.lang.Exception
     */
    public void setParameters(String parameters) throws Exception {
        this.parameters = parameters;
        processParameters(parameters);
    }

    /**
     * Gets the countryCode.
     *
     * @return String hold the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the countryCode
     *
     * @param countryCode hold the Country Code
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * Gets the non repeating parameters that are used to construct the message.
     *
     * @return Non repeating parameters use to construct the message.
     */
    private String getNonRepeatingParameters() {
        return nonRepeatingParameters;
    }

    /**
     * Gets the non repeating parameter pairs use to construct the message.
     *
     * @return A map containing non repeating parameter pairs used to construct
     * the message.
     */
    public HashMap getNonRepeatingPairs() {
        return nonRepeatingParametersTable;
    }

    /**
     * Gets the repeating blocks use to construct the message.
     *
     * @return A map containing repeating blocks with block key as Key and a
     * Vector of HashTables as Value each containing a block.
     */
    public HashMap getRepeatingBlocks() {
        return repeatingBlocks;
    }

    /**
     * Gets the repeating parameters used to construct the message.
     *
     * @retrun String containing repeating parameters use to construct the
     * message.
     */
    private String getRepeatingParameters() {
        // .;. 
        if (repeatingParameters.endsWith(DOT + SEMI_COLON + DOT)) {
            repeatingParameters = repeatingParameters.substring(0,
                    repeatingParameters.lastIndexOf(DOT + SEMI_COLON + DOT));
        }
        return repeatingParameters;
    }

    /**
     * Adds the specified address in the cc list.
     *
     * @param address.
     */
    public void addCc(String address) {
        ccList.add(address);
    }

    /**
     * Return the cc list containing addresses.
     *
     * @return The address List.
     */
    public ArrayList<String> getCc() {
        return ccList;
    }

    /**
     * Check whether cc list contains addresses or not.
     *
     * @return The boolean true if yes.
     */
    public boolean hasCc() {
        return ((ccList.size() > 0) ? true : false);
    }

    /**
     * Adds the specified address in the bcc list.
     *
     * @param address.
     */
    public void addBcc(String address) {
        bccList.add(address);
    }

    /**
     * Return the bcc list containing addresses.
     *
     * @return The address List.
     */
    public ArrayList<String> getBcc() {
        return bccList;
    }

    /**
     * Check whether bcc list contains addresses ot not.
     *
     * @return The boolean true if yes.
     */
    public boolean hasBcc() {
        return ((bccList.size() > 0) ? true : false);
    }

    /**
     * Makes a list of all the attachment.
     *
     * @param filePath The attachment file pathname
     */
    public void addAttachment(String filePath) {
        attachments.add(filePath);
    }

    /**
     * Gets the list containing the attachments.
     *
     * @return The list containing the attachments.
     */
    public ArrayList<String> getAttachments() {
        return attachments;
    }

    /**
     * Check whether their is any attachment or not.
     *
     * @return The boolean true if yes.
     */
    public boolean hasAttachment() {
        return ((attachments.size() > 0) ? true : false);
    }

    /*
     * Adds a non repeating name value pair to the parameters.
     * @param   name    Name of the pair
     * @value   value   Value of the pair
     */
    public void addNonRepeatingParameter(String name, String value) {
        // key.=.value.;.
        nonRepeatingParameters = nonRepeatingParameters + name + DOT + EQUALSTO + DOT + value + DOT + SEMI_COLON + DOT;
    }

    /**
     * Clears non repeating parameters.
     */
    public void clearNonRepeatingParameter() {
        nonRepeatingParameters = "";
    }

    /**
     * Clears parameters.
     */
    public void clearParameter() {
        parameters = "";
    }

    /**
     * Adds the repeating parameters to the message parameters.
     * @param table Table containing name-value pairs for repeating block
     * @param blockKey  The key for the block
     */

    public void addRepeatingBlock(HashMap table, String blockKey) {
        if (table != null) {
            Set set = table.keySet();
            if (set != null) {
                Iterator it = set.iterator();

                StringBuilder notifParameters = new StringBuilder();
                notifParameters.append(DOT + OPENING_BRACKET + DOT);
                notifParameters.append(blockKey);
                notifParameters.append(DOT + COLON + DOT);
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = (String) table.get(key);
                    notifParameters.append(key);
                    notifParameters.append(DOT + EQUALSTO + DOT);
                    notifParameters.append(value);
                    if (it.hasNext()) {
                        notifParameters.append(DOT + COMMA + DOT);
                    }
                }
                notifParameters.append(DOT + END_BLOCK_DELIMITER + DOT);

                repeatingParameters = notifParameters.toString();

                if (!(getRepeatingParameters().equals(""))) {
                    repeatingParameters = getRepeatingParameters()
                            + DOT + SEMI_COLON + DOT
                            + repeatingParameters;

                }
            }
        }
    }
    
    /**
     * Processes the parameter string delimited by certain delimiters and stores
     * the name value pairs in the map.
     *
     * @param parameters The name-value pair string.
     */
    private void processParameters(String parameters) throws Exception {
        
        String[] parameterList = parameters.split(
                SLASH + DOT + SEMI_COLON + SLASH + DOT);
        
        String previousKey = null;
        ArrayList<HashMap> itemList;
        itemList = null;
        
        for (String parameterList1 : parameterList) {
            String nameValuePair = parameterList1;
            
            if (!nameValuePair.contains(DOT + OPENING_BRACKET + DOT)) {
                String[] nameValue = nameValuePair.split(
                        SLASH + DOT + EQUALSTO + SLASH + DOT);
                
                if (nameValue.length == 2) {
                    nonRepeatingParametersTable.put(nameValue[0].trim(),
                            nameValue[1].trim());
                }
            } else {
                nameValuePair = nameValuePair.replaceAll(
                        SLASH + DOT + SLASH + OPENING_BRACKET + SLASH + DOT, "");
                
                nameValuePair = nameValuePair.replaceAll(
                        SLASH + DOT + SLASH + END_BLOCK_DELIMITER + SLASH + DOT,
                        "");
                
                String key = nameValuePair.substring(0, nameValuePair.indexOf(
                        DOT + COLON + DOT));
                
                nameValuePair = nameValuePair.substring(nameValuePair.indexOf(
                        DOT + COLON + DOT) + 3);
                
                String[] subParameters = nameValuePair.split(
                        SLASH + DOT + COMMA + SLASH + DOT);
                
                HashMap subParametersTable;
                subParametersTable = new HashMap();
                
                for (String subParameter : subParameters) {
                    String[] nameValue = subParameter.split(SLASH + DOT + 
                            EQUALSTO + SLASH + DOT);
                    
                    if (nameValue.length == 2) {
                        subParametersTable.put(key + DOT + COLON + DOT + 
                                nameValue[0].trim(),
                                nameValue[1].trim());
                    }
                }

                if (key.equals(previousKey)) {
                    if (itemList != null) {
                        itemList.add(subParametersTable);
                    }
                } else {
                    itemList = new ArrayList();
                    itemList.add(subParametersTable);
                    
                    repeatingBlocks.put(key, itemList);
                }
                
                previousKey = key;
            }
        }
    }
}//end class
