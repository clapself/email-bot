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

public class NotificationTemplate implements Serializable {
    //------------------------------------------------------------------------
    //    Data Member(s)
    //------------------------------------------------------------------------

    /**
     * Serial version number.
     */
    private static final long serialVersionUID = 4259317855241754778L;
    
    /**
     * This is the unique identifier.
     */
    private int typeId = 0;

    /**
     * The template file name
     */
    private String template = null;

    /**
     * The associated configuration file
     */
    private String config;

    /**
     * This is the date and time of modification of database data.
     */
    private String modTime = null;

    //------------------------------------------------------------------------
    //    Constructor(s)
    //------------------------------------------------------------------------
    /**
     * Default Constructor.
     */
    public NotificationTemplate() {
    }

    //------------------------------------------------------------------------
    //    Accessor(s)
    //------------------------------------------------------------------------
    /**
     * Gets the unique identifier for the <code>NOTIFICATIONTEMPLATE</code>
     * table.
     *
     * @return The unique identifier.
     */
    public int getTypeId() {
        return typeId;
    }

    /**
     * Sets the unique identifier for the <code>NOTIFICATIONTEMPLATE</code>
     * table.
     *
     * @param typeId The unique identifier.
     */
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    /**
     * Gets the template of the notification.
     *
     * @return The template of the notification.
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Sets the template of the notification.
     *
     * @param template The template of the notification.
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * Gets the configuration file for template.
     *
     * @return The configuration file for template.
     */
    public String getConfig() {
        return this.config;
    }

    /**
     * Sets the configuration file for template.
     *
     * @param config configuration file for template.
     */
    public void setConfig(String config) {
        this.config = config;
    }

    /**
     * Gets the last modification date and time of database table.
     *
     * @return The last modification date and time of database table
     */
    public String getModTime() {
        return modTime;
    }

    /**
     * Sets the last modification date and time of database table.
     *
     * @param modTime The last modification date and time of database table
     */
    public void setModTime(String modTime) {
        this.modTime = modTime;
    }
}