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

package com.ericsson.oss.services.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion.VersionFlag;

public class VesUtility {

    private static final Logger log = LoggerFactory.getLogger(VesUtility.class);

    private VesUtility() {

    }

    public static Properties getProperties(final String fileName) {
        final Properties props = new Properties();
        try (InputStream inputStream = getResource(fileName)) {
            props.load(inputStream);
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
        return props;
    }

    public static JsonSchema getJsonSchema() {
        log.debug("creating CommonEvent JsonScehma");
        final InputStream is = getResource("CommonEventFormat_30.2.1_ONAP.json");
        final JsonSchemaFactory factory = JsonSchemaFactory.getInstance(VersionFlag.V4);
        final JsonSchema schema = factory.getSchema(is);
        log.debug("CommonEvent JsonScehma created successfully");
        return schema;
    }

    private static InputStream getResource(final String fileName) {
        return VesUtility.class.getClassLoader().getResourceAsStream(fileName);
    }
}
