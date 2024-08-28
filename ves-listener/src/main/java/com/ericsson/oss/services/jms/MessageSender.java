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

package com.ericsson.oss.services.jms;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

import com.ericsson.oss.services.exception.VesExceptionEnum;
import com.ericsson.oss.services.exception.EventException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageSender {

    private static final Logger log = LoggerFactory.getLogger(MessageSender.class);

    @Resource(lookup = "java:jboss/exported/jms/queue/VES_events")
    private Queue queue;

    @Inject
    private JMSContext context;

    public final void send(String messageText) {
        try {
            log.info("Received message: {} ", messageText);

            context.createProducer().send(queue, messageText);

            log.info("Sent jms message: {}", messageText);
        } catch (Exception e) {
            throw new EventException(VesExceptionEnum.JMS_CONNECTION_EXCEPTION);
        }
    }
}