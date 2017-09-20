/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pointsmsgserver.point;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author spetsiotis
 */
@RequestScoped
public class ReceivePointsBean {
    @Inject
    //    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
            JMSContext context;

    @Resource(lookup = "jms/pointsQueue")
    Queue pointsQueue;

    public String receiveMessage() {
        try (JMSConsumer consumer = context.createConsumer(pointsQueue)) {
            String message = consumer.receiveBody(String.class);
            System.out.println("Received message: " + message);
            return message;
        }
    }

    public int getQueueSize() {
        int count = 0;
        try {
            QueueBrowser browser = context.createBrowser(pointsQueue);
            Enumeration elems = browser.getEnumeration();
            while (elems.hasMoreElements()) {
                elems.nextElement();
                count++;
            }
        } catch (JMSException ex) {
            Logger.getLogger(ReceivePointsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Getting queue size: " + count);
        return count;
    }
}
