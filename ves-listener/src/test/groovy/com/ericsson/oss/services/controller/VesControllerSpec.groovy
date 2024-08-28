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

package com.ericsson.oss.services.controller

import static org.mockito.Mockito.when

import javax.jms.JMSContext
import javax.jms.JMSProducer
import javax.ws.rs.core.Response

import org.mockito.Mockito

import com.ericsson.cds.cdi.support.rule.ImplementationInstance
import com.ericsson.cds.cdi.support.rule.MockedImplementation
import com.ericsson.cds.cdi.support.rule.ObjectUnderTest
import com.ericsson.cds.cdi.support.spock.CdiSpecification

import spock.lang.Unroll

class VesControllerSpec extends CdiSpecification {

    @MockedImplementation
    JMSProducer jmsProducer;

    @ImplementationInstance
    JMSContext jmsContext = Mockito.mock(JMSContext.class);

    @ObjectUnderTest
    private VesController vesController;

    @Unroll
    def "Test sending single VES events with namespace"(final String namespace, final int httpStatus) {

        when(jmsContext.createProducer()).thenReturn(jmsProducer);

        given: "A single VES event is sent"
        final String jsonVesEvent = getVesEvent(namespace)
        final Response response = vesController.processAnyEvent(jsonVesEvent)

        expect:
        response.getStatus() == httpStatus

        where:
        namespace                   | httpStatus
        "3GPP-FaultSupervision"     | 202
        "3GPP-Heartbeat"            | 400
    }



    private String getVesEvent(final String namespace) {
        return "{\n" +
                "  \"event\": {\n" +
                "    \"commonEventHeader\": {\n" +
                "      \"domain\": \"stndDefined\",\n" +
                "      \"eventId\": \"1678271515731561767\",\n" +
                "      \"eventName\": \"stndDefined_Vscf:Acs-Ericcson_ProcessingErrorAlarm\",\n" +
                "      \"startEpochMicrosec\": 1678271515731562201,\n" +
                "      \"lastEpochMicrosec\": 1678271515731562201,\n" +
                "      \"priority\": \"Normal\",\n" +
                "      \"reportingEntityName\": \"ibcx0001vm002oam001\",\n" +
                "      \"sequence\": 0,\n" +
                "      \"sourceName\": \"scfx0001vm002cap001\",\n" +
                "      \"version\": \"4.1\",\n" +
                "      \"vesEventListenerVersion\": \"7.2\",\n" +
                "      \"stndDefinedNamespace\": " + namespace + ",\n" +
                "      \"timeZoneOffset\": \"UTC-05.00\"\n" +
                "    },\n" +
                "    \"stndDefinedFields\": {\n" +
                "      \"schemaReference\": \"https://forge.3gpp.org/rep/sa5/MnS/-/blob/Rel-18/OpenAPI/TS28532_FaultMnS.yaml#/components/schemas/NotifyNewAlarm\",\n" +
                "      \"data\": {\n" +
                "        \"additionalInformation\": {\n" +
                "          \"1\": {\n" +
                "            \"P\": {\n" +
                "              \"I\": \"2c5c4d18-dedd-48fa-89ff-4e88003d9cd7\",\n" +
                "              \"N\": \"eric-ran-cu-cp-rc-singlepod-rc-1-0\"\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        \"additionalText\": \"Connection lost for Service Discovery Interfaces listed in Additional Information.\",\n" +
                "        \"alarmId\": \"9cf9a4a0-5271-490d-87ce-3727d823f32c\",\n" +
                "        \"alarmType\": \"PROCESSING_ERROR_ALARM\",\n" +
                "        \"eventTime\": \"2023-03-08T10:31:55.731560233Z\",\n" +
                "        \"href\": \"https://customerA.com/SubNetwork=customerA-sub-network/MeContext=customerA-me-context/ManagedElement=1/GNBCUCPFunction=1\",\n" +
                "        \"notificationId\": 1336781711,\n" +
                "        \"notificationType\": \"notifyNewAlarm\",\n" +
                "        \"perceivedSeverity\": \"MAJOR\",\n" +
                "        \"probableCause\": 307,\n" +
                "        \"systemDN\": \"ManagedElement=1,MnsAgent=FM\"\n" +
                "      },\n" +
                "      \"stndDefinedFieldsVersion\": \"1.0\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }
}
