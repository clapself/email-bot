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

public class NotificationType implements Serializable {
    //------------------------------------------------------------------------
    //    Data Member(s)
    //------------------------------------------------------------------------

    /**
     * Serial version number.
     */
    private static final long serialVersionUID = -5546566193545455957L;

    /**
     * This is the unique identifier.
     */
    private int typeId = 0;

    /**
     * This is the description of the notification.
     */
    private String notifTypeDesc = null;

    /**
     * This is the date and time of modification of database table.
     */
    private String modTime = null;

    //------------------------------------------------------------------------
    //    Constructor(s)
    //------------------------------------------------------------------------
    /**
     * Default Constructor.
     */
    public NotificationType() {
    }

    //------------------------------------------------------------------------
    //    Accessor(s)
    //------------------------------------------------------------------------
    /**
     * Gets the unique identifier for the <code>NOTIFICATIONTYPE</code>
     * table.
     *
     * @return The unique identifier.
     */
    public int getTypeId() {
        return typeId;
    }

    /**
     * Sets the unique identifier for the <code>NOTIFICATIONTYPE</code>
     * table.
     *
     * @param typeId The unique identifier.
     */
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    /**
     * Gets the description of the notification.
     *
     * @return The description of the notification.
     */
    public String getNotifTypeDesc() {
        return notifTypeDesc;
    }

    /**
     * Sets the description of the notification.
     *
     * @param notifTypeDesc The description of the notification.
     */
    public void setNotifTypeDesc(String notifTypeDesc) {
        this.notifTypeDesc = notifTypeDesc;
    }

    /**
     * Gets the last modification date and time of database table.
     *
     * @return The last modification date and time of database table.
     */
    public String getModTime() {
        return modTime;
    }

    /**
     * Sets the last modification date and time of database table.
     *
     * @param modTime The last modification date and time of database table.
     */
    public void setModTime(String modTime) {
        this.modTime = modTime;
    }
}
