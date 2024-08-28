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

package com.ericsson.oss.services.validator;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.oss.services.exception.EventException;
import com.ericsson.oss.services.exception.VesExceptionEnum;
import com.ericsson.oss.services.model.VesEvent;
import com.ericsson.oss.services.utils.VesUtility;

public class GeneralEventValidator {

    private static final Logger log = LoggerFactory.getLogger(GeneralEventValidator.class);

    @Inject
    private SchemaValidator schemaValidator;

    public void validate(final VesEvent vesEvent) {
        log.info("Validating VesEvent: {}", vesEvent);
        isValidEvent(vesEvent);
    }

    private void isValidEvent(final VesEvent vesEvent) {

        if (!schemaValidator.conformsToSchema(vesEvent.getEvent(), VesUtility.getJsonSchema())) {
            log.error("Error validating VesEvent");
            throw new EventException(VesExceptionEnum.SCHEMA_VALIDATION_FAILED);
        }
        log.debug("VesEvent validated Successfully");
    }

}
