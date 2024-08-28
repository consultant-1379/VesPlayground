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

package com.ericsson.jmsconsumer;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JmsConsumer {

    private static final String CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String QUEUE_NAME = "java:/jms/queue/VES_events";
    private static final String WILDFLY_URL = "http-remoting://ves-listener:8080";
    private static final String WILDFLY_USER = "guest";
    private static final String WILDFLY_PASSWORD = "guest";
    private Context jndiContext = null;
    private JMSContext jmsContext = null;

    public static void main(String[] args) {
        final JmsConsumer consumer = new JmsConsumer();

        consumer.receiveMessages();
    }

    private void receiveMessages() {
        log.info("Press q to exit...");
        try {
            final Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
            env.put(Context.PROVIDER_URL, WILDFLY_URL);
            env.put(Context.SECURITY_PRINCIPAL, WILDFLY_USER);
            env.put(Context.SECURITY_CREDENTIALS, WILDFLY_PASSWORD);

            jndiContext = new InitialContext(env);

            final ConnectionFactory cf = (ConnectionFactory) jndiContext.lookup(CONNECTION_FACTORY);
            log.info("Found Connection Factory...");
            final Destination queue = (Destination) jndiContext.lookup(QUEUE_NAME);
            log.info("Found Queue...");

            jmsContext = cf.createContext(WILDFLY_USER, WILDFLY_PASSWORD);
            log.info("Created jms context...");
            TextMessage message = null;
            final JMSConsumer consumer = jmsContext.createConsumer(queue);
            log.info("Connected to queue...");
            while (System.in.read() != 'q') {
                message = (TextMessage) consumer.receive(1000);
                if (message != null) {
                    log.info("Received message: '" + message.getText() + "'");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jmsContext != null) {
                jmsContext.close();
            }

            if (jndiContext != null) {
                try {
                    jndiContext.close();

                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
