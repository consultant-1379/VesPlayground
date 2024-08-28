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

import org.json.JSONObject;

import com.google.common.base.CaseFormat;

public enum VesExceptionEnum {

    SCHEMA_VALIDATION_FAILED(ExceptionType.SERVICE_EXCEPTION, "SVC0002",
            "Bad Parameter (JSON does not conform to schema)", 400),
    JMS_CONNECTION_EXCEPTION(ExceptionType.SERVICE_EXCEPTION, "SVC0003",
            "Internal Server Error (Exception occured while connecting to queue)", 500);

    private final int httpStatusCode;
    private final ExceptionType type;
    private final String code;
    private final String details;

    VesExceptionEnum(final ExceptionType type, final String code, final String details, final int httpStatusCode) {
        this.type = type;
        this.code = code;
        this.details = details;
        this.httpStatusCode = httpStatusCode;
    }

    public JSONObject toJSON() {
        final JSONObject exceptionTypeNode = new JSONObject();
        exceptionTypeNode.put("messageId", code);
        exceptionTypeNode.put("text", details);

        final JSONObject requestErrorNode = new JSONObject();
        requestErrorNode.put(type.toString(), exceptionTypeNode);

        final JSONObject rootNode = new JSONObject();
        rootNode.put("requestError", requestErrorNode);
        return rootNode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;

    }

    public enum ExceptionType {
        SERVICE_EXCEPTION;

        @Override
        public String toString() {
            return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name());
        }
    }

}
