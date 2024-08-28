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

package com.ericsson.oss.services.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.oss.services.jms.MessageSender;
import com.ericsson.oss.services.model.VesEvent;
import com.ericsson.oss.services.validator.GeneralEventValidator;

public class AnyEventService {

    private static final Logger log = LoggerFactory.getLogger(AnyEventService.class);

    @Inject
    private GeneralEventValidator generalEventValidator;

    @Inject
    private MessageSender messageSender;

    public void processEvent(final VesEvent vesEvent) {
        log.info("processing SingleEvent API");
        generalEventValidator.validate(vesEvent);
        messageSender.send(vesEvent.toString());
        log.info("SingleEvent Send Successfully");

    }
}
