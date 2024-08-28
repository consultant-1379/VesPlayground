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

package com.ericsson.oss.services.controller;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.oss.services.exception.EventException;
import com.ericsson.oss.services.model.VesEvent;
import com.ericsson.oss.services.service.AnyEventService;

@javax.ws.rs.Path("/")
public class VesController implements VesInterface {

    @Inject
    private AnyEventService singleEventService;

    private static final Logger log = LoggerFactory.getLogger(VesController.class);

    @Override
    public Response processAnyEvent(final String event) {
        log.info("Received Event {}", event);
        final VesEvent vesEvent = new VesEvent(new JSONObject(event));
        try {
            singleEventService.processEvent(vesEvent);
        } catch (final EventException e) {
            return Response.status(e.getApiException().getHttpStatusCode()).entity(e.getApiException().toJSON().toString())
                    .build();
        }

        return Response.accepted("Successfully send event").build();
    }

}
