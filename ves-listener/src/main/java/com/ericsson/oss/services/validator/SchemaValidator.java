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

import java.util.Set;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.oss.services.exception.VesException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;

public class SchemaValidator {

    private static final Logger log = LoggerFactory.getLogger(SchemaValidator.class);

    public boolean conformsToSchema(final JSONObject payload, final JsonSchema schema) {
        try {
            final ObjectMapper mapper = new ObjectMapper();

            final String content = payload.toString();
            final JsonNode node = mapper.readTree(content);
            final Set<ValidationMessage> messageSet = schema.validate(node);

            if (messageSet.isEmpty()) {
                return true;
            }

            log.warn("Schema validation failed for event: {}", payload);
            messageSet.forEach(it -> log.error(it.getMessage()));

            return false;
        } catch (final Exception e) {
            throw new VesException("Unable to validate against schema", e);
        }
    }
}
