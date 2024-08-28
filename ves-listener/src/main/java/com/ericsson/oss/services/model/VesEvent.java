/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2023
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

package com.ericsson.oss.services.model;

import org.json.JSONObject;

public class VesEvent {

    private JSONObject event;

    public VesEvent(final JSONObject event) {
        this.event = event;
    }

    public void setEvent(final JSONObject event) {
        this.event = event;
    }

    public JSONObject getEvent() {
        return event;
    }

    @Override
    public String toString() {
        return event.toString();
    }

}
