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

package com.ericsson.oss.services.exception;

public class EventException extends RuntimeException {

    private static final long serialVersionUID = -1111700733563622059L;
    private final VesExceptionEnum apiException;

    public EventException(final VesExceptionEnum apiException, final Exception cause) {
        super(cause);
        this.apiException = apiException;
    }

    public EventException(final VesExceptionEnum apiException) {
        this.apiException = apiException;
    }

    public VesExceptionEnum getApiException() {
        return apiException;
    }
}
