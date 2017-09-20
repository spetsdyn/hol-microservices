/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pointsmsgclient.point;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author spetsiotis
 */
@RequestScoped
public class SendPointsBean {

    private static final Logger log = Logger.getLogger(SendPointsBean.class.getName());

    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DEFAULT_DESTINATION = "jms/topic/sample-topic";

    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://localhost:8080";

    public void sendMessage() throws JMSException, NamingException {
        ConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        Destination destination = null;
        TextMessage message = null;
        Context context = null;

        try {
            // Set up the context for the JNDI lookup
            final Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
            context = new InitialContext(env);

            // Perform the JNDI lookups
            String connectionFactoryString = System.getProperty("connection.factory", DEFAULT_CONNECTION_FACTORY);
            log.log(Level.INFO, "Attempting to acquire connection factory \"{0}\"", connectionFactoryString);
            connectionFactory = (ConnectionFactory) context.lookup(connectionFactoryString);
            log.log(Level.INFO, "Found connection factory \"{0}\" in JNDI", connectionFactoryString);

            String destinationString = System.getProperty("destination", DEFAULT_DESTINATION);
            log.log(Level.INFO, "Attempting to acquire destination \"{0}\"", destinationString);
            destination = (Destination) context.lookup(destinationString);
            log.log(Level.INFO, "Found destination \"{0}\" in JNDI", destinationString);

            // Create the JMS connection, session, producer
            // make sure to add the password created in Main WFS startup
            connection = connectionFactory.createConnection("admin", "password");
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(destination);

            connection.start();

            log.info("Sending message");

            message = session.createTextMessage("something");
            producer.send(message);

        } catch (NamingException | JMSException | NumberFormatException e) {
            log.severe(e.getMessage());
            throw e;
        } finally {
            if (context != null) {
                context.close();
            }

            // closing the connection takes care of the session, producer, and consumer
            if (connection != null) {
                connection.close();
            }
        }

    }

    @Inject
    //    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
            JMSContext context;

    @NotNull
    @Pattern(regexp = "^\\d{2},\\d{2}", message = "Message format must be 2 digits, comma, 2 digits, e.g. 12,12")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Resource(lookup = "java:global/jms/pointsQueue")
    Queue pointsQueue;

    //    public void sendMessage() {
    //        System.out.println("Sending message: " + message);
    //
    //        context.createProducer().send(pointsQueue, message);
    //    }
}
