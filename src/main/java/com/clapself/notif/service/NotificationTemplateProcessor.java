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
//import clapself.util.StringHelper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class parses the notification template and does the needed replacements
 * for building the final notification.
 *
 * <pre>
 * VERSION: 1.0
 * SUBJECT: Hottest Stories of the Day
 * FROMEMAIL: {#SupportEmail#}
 * FROMNAME: Clapself Editor
 * BODY:
 * </pre>
 */
public class NotificationTemplateProcessor {

    //--------------------------------------------------------------------------
    // Data Member(s)
    //--------------------------------------------------------------------------
    /**
     * Declaration for input/output error.
     */
    private static final String GETTEMPLATE_IO_ERROR = "NotificationMonitor:getTemplate:IOException:Error during input/output";

    /**
     * Declaration for bad header error.
     */
    private static final String BAD_HEADER_ERROR = "NotificationMonitor:processHeader:ERROR: Bad header found in the template";

    /**
     * Space character
     */
    public static final String SPACE = " ";

    /**
     * This is the start delimiter of the key string in template.
     */
    private static final String START_DELIMITER = "{#";

    /**
     * This is the start delimiter of the key string in template.
     */
    private static final String START_BLOCK_DELIMITER = "[#";

    /**
     * This is the end delimiter of the key string in template.
     */
    private static final String END_BLOCK_DELIMITER = "]";

    /**
     * This is the constant for hash ("#" ).
     */
    private static final String HASH = "#";

    /**
     * This is the constant for combination of double backward slashes and end
     * brackets ("\\}" ).
     */
    private static final String SLASH_ENDBRACKET = "\\}";

    /**
     * This is the constant for newline ("\n" ).
     */
    private static final String NEWLINE = "\n";

    /**
     * This is the constant for default key value "NA".
     */
    private static final String DEFAULT_KEY_VALUE = "N.A.";

    /**
     * Regular expression to match the dynamic content.
     */
    private static final String REGEX_EMPTY_DYNAMIC_PARAM_REPLACEMENT = "([\\{\\[]#[^#]+#[\\}\\]])";

    /**
     * Regular expression to match the dynamic content.
     */
    private static final String REGEX_EMPTY_REPEATING_PARAM_REPLACEMENT = "(\\.\\[#\\.([\\n\\r]|.)*\\.\\]\\.)";

    /**
     * Regular expression for replacing the spaces before comma.
     */
    private static final String REGEX_SPACE_REMOVAL_BEFORE_COMMA = "([\\s]+,)";

    /**
     * Constant to store the keyword "VERSION".
     */
    private static final String VERSION = "VERSION:";

    /**
     * Constant to store the keyword "BODY".
     */
    private static final String BODY = "BODY:";

    /**
     * Constant to store the keyword "SUBJECT".
     */
    private static final String SUBJECT = "SUBJECT:";

    /**
     * Constant to store the keyword "FROMEMAIL".
     */
    private static final String FROMEMAIL = "FROMEMAIL:";

    /**
     * Constant to store the keyword "ATTACHMENTS".
     */
    private static final String ATTACHMENTS = "ATTACHMENTS:";

    /**
     * Constant to store the keyword "FROMNAME".
     */
    private static final String FROMNAME = "FROMNAME:";

    /**
     * Constant to store the keyword "TOEMAIL".
     */
    private static final String TOEMAIL = "TOEMAIL:";

    /**
     * Constant to store the keyword "TONAME".
     */
    private static final String TONAME = "TONAME:";
    /**
     * Declaration of Notification object to hold the notification details.
     */
    private Notification notification = null;
    /**
     * Variable to hold the version number.
     */
    private String version = null;

    //--------------------------------------------------------------------------
    // Constructor(s)
    //--------------------------------------------------------------------------
    /**
     * Default Constructor.
     */
    public NotificationTemplateProcessor() {
    }

    //--------------------------------------------------------------------------
    //    Methods(s)
    //--------------------------------------------------------------------------
    /**
     * Parses the template and replaces any dynamic parameters with the provided
     * values. It constructs a notification object with the data from the
     * template and returns it back for further handling.
     *
     * @param templateFile The location and the name of the template file.
     * @param notification Notification object
     * @return Partially populated notification object
     * @throws NotificationManagerException Thrown on any template
     * reading/processing error
     */
    public Notification processTemplate(String templateFile,
            Notification notification) throws NotificationManagerException {
        this.notification = notification;

        // Read the template into a string
        String templateContent = getTemplateContent(templateFile);

        // Break the template content into header and body components
        String header = "";

        int startIndex = templateContent.indexOf(BODY);
        int endIndex = BODY.length();

        if (startIndex > -1) {
            header = templateContent.substring(0, startIndex);
        } else {
            startIndex = 0;
        }

        String body = templateContent.substring(startIndex + endIndex);

        if (!notification.getFormat().equals(Notification.ALERT_TYPE_SMS)) {
            String updatedHeader = replace(header, notification.
                    getNonRepeatingPairs());

            updatedHeader = replaceEmptyParameters(updatedHeader);
            
            // Parse and process the header
            processHeader(updatedHeader);
        }
          
        //String dynamicParameters = _notification.getParameters();
        // Parse and process the body
        processBody(body, notification.getFormat());

        return this.notification;
    }

    /**
     * Parses the notification template header and fills the header parts of the
     * notification object.
     *
     * @param header The template header string
     * @throws NotificationManagerException Thrown on header parsing error
     */
    private void processHeader(String headerSection) throws
            NotificationManagerException {
        String[] headerLines = headerSection.split(NEWLINE);

        for (String headerLineRaw : headerLines) {
            String headerLine = headerLineRaw.trim();

//            if (!headerLine.contains(VERSION)) {
//                version = headerLine.substring(headerLine.indexOf(VERSION) + 
//                        VERSION.length());
//                throw new NotificationManagerException("Error: Invalid template."
//                        + "Version information missing.");
//            }
            
            if (headerLine.contains( SUBJECT ) && notification.getSubject() == null) {
                notification.setSubject(getHeaderPart(headerLine, SUBJECT));
            }

            if (headerLine.contains( FROMEMAIL ) && notification.getFromEmail() == null) {
                notification.setFromEmail(getHeaderPart(headerLine, FROMEMAIL));
            }

            if (headerLine.contains( FROMNAME ) && notification.getFromName() == null) {
                notification.setFromName(getHeaderPart(headerLine, FROMNAME));
            }
            
            if (headerLine.contains( TONAME ) && notification.getRecipient() == null) {
                notification.setRecipient(getHeaderPart(headerLine, TONAME));
            }

            if (headerLine.contains( ATTACHMENTS )) {
                notification.addAttachment(getHeaderPart(headerLine, ATTACHMENTS));
            }
        }
    }

    private String getHeaderPart(String headerLine, String label) throws
            NotificationManagerException {

        String headerValue = "";
        if (headerLine.contains(label)) {
            String rightHeaderPart = headerLine.substring(
                    headerLine.indexOf(label) + label.length());
            if (rightHeaderPart != null) {
                headerValue = rightHeaderPart.trim();
            }
        }

        return headerValue;
    }

    /**
     * Parses the processes the notification message body.
     *
     * @param body The notification message body as read from the template
     * @param dynamicParameters The dynamic parameter string containing
     * name-value pairs
     * @throws NotificationManagerException Thrown on any template
     * reading/processing error
     */
    private void processBody(String body, String format) throws NotificationManagerException {

        String processedBodyContent = replace(body, notification.
                getNonRepeatingPairs());

        if (notification.getRepeatingBlocks().size() > 0) {
            processedBodyContent = processRepeatingBlocks(processedBodyContent,
                    format);
        }

        processedBodyContent = replaceEmptyParameters(processedBodyContent);

        notification.setMessage(processedBodyContent);
    }

    /*
     * Handles the replacement of a repeating list block with proper values.
     * @param   repeatingBlock  Block to be replaced with the proper values.
     * @return Block string replaced with proper values for the keys.
     */
    private String replaceRepeatingBlocks(String repeatingBlock, String format) {
        HashMap listIds = notification.getRepeatingBlocks();

        Iterator<Entry<String, String>> listIdIter = listIds.entrySet().iterator();

        ArrayList itemList = null;

        /**
         * TODO: Following while loop doesn't make sense. Needs review.
         */
        while (listIdIter.hasNext()) {
            Entry<String, String> listId = listIdIter.next();

            if (repeatingBlock.contains(listId.getKey())) {
                itemList = (ArrayList) notification.getRepeatingBlocks().get(
                        listId);
                break;
            }
        }

        StringBuilder replacedBodyContent = new StringBuilder();

        if (itemList != null) {
            int listSize = itemList.size();
            for (int index = 0; index < listSize; index++) {
                String replacementContent = repeatingBlock;

                HashMap itemTable = (HashMap) itemList.get(index);
                String replacedContent = replace(replacementContent, itemTable);

                int length = replacedBodyContent.length() + replacedContent.length();

                if (format != null && format.trim().length() != 0
                        && format.equals(Notification.ALERT_TYPE_SMS)
                        && length > Notification.SMS_CONTENT_LENGTH) {
                    return replacedBodyContent.toString();
                }

                if (index == listSize - 1) {
                    replacedBodyContent.append(replacedContent);
                } else {
                    replacedBodyContent.append(replacedContent).append("\n\n");
                }
            }
        }

        return replacedBodyContent.toString();
    }

    /*
     * Handles the replacement for the keys (Non List blocks) with proper values.
     * @param   content  Content containing the keys to be replaced with the values.
     * @param   pairsTable Hashtable containing the keys and values.
     * @return  String replaced with the values for the keys.
     */
    private String replace(String content, HashMap<String, String> pairsTable) {

        for (Map.Entry<String, String> entry : pairsTable.entrySet()) {
            String key = entry.getKey();
            String replacement = entry.getValue();

            if (replacement == null) {
                replacement = "";
            } else {
                replacement = replacement.replaceAll("\\$", "\\\\\\$");
            }

            if (key != null) {
                content = content.replaceAll(
                        Notification.SLASH + START_DELIMITER + key + HASH + SLASH_ENDBRACKET,
                        replacement);
            }
        }
        return content;
    }

    /**
     * Contains the processing logic for the repeating list blocks.
     *
     * @param bodyContent String containing the list blocks to be replaced for
     * values.
     * @return String after replacing the list blocks with the proper values.
     */
    private String processRepeatingBlocks(String bodyContent, String format) {
        String blockContent;

        int indexOfBlockStart = 0;
        int indexOfBlockEnd = 0;

        //String key = null;
        while (bodyContent.indexOf(
                Notification.DOT + START_BLOCK_DELIMITER + Notification.DOT) > indexOfBlockStart) {
            indexOfBlockStart = bodyContent.indexOf(
                    Notification.DOT + START_BLOCK_DELIMITER + Notification.DOT);
            indexOfBlockEnd = bodyContent.indexOf(
                    Notification.DOT + END_BLOCK_DELIMITER + Notification.DOT);

            blockContent = bodyContent.substring(indexOfBlockStart + 4,
                    indexOfBlockEnd);

            //key = SLASH + START_BLOCK_DELIMITER + blockContent + SLASH + END_BLOCK_DELIMITER;
            String replacedBlockContent = replaceRepeatingBlocks(blockContent,
                    format);
            String beginPart = bodyContent.substring(0, indexOfBlockStart);
            String endPart = bodyContent.substring(indexOfBlockEnd + 3);

            bodyContent = beginPart + replacedBlockContent + endPart;
        }

        return bodyContent;
    }

    /**
     * Reads the template file and return a string containing the template file
     * content.
     *
     * @param filePath Name of the template with the path.
     * @return String containing the content of the template file.
     * @throws NotificationManagerException if unable to read the template file.
     */
    private String getTemplateContent(String filePath) throws
            NotificationManagerException {
        StringBuilder templateData = new StringBuilder("");

        try {
            BufferedReader fileData = new BufferedReader(new FileReader(
                    filePath));

            String readLine;

            while ((readLine = fileData.readLine()) != null) {
                templateData.append(readLine);
                templateData.append(NEWLINE);
            }
        } catch (IOException ioE) {
            throw new NotificationManagerException(GETTEMPLATE_IO_ERROR + ioE.
                    getMessage());
        }

        return templateData.toString();
    }

    /**
     * Replaces the empty dynamic parameters with space and also trims the
     * spaces before comma.
     *
     * @param content The content to check for empty dynamic parameters.
     * @return The replaces content.
     */
    private String replaceEmptyParameters(String content) {
        String processed = content;
        if (processed != null) {
            processed = processed.replaceAll(
                    REGEX_EMPTY_DYNAMIC_PARAM_REPLACEMENT, SPACE);
            processed = processed.replaceAll(
                    REGEX_EMPTY_REPEATING_PARAM_REPLACEMENT, SPACE);
            processed = processed.replaceAll(REGEX_SPACE_REMOVAL_BEFORE_COMMA,
                    Notification.COMMA);
        }

        return processed;
    }
}
