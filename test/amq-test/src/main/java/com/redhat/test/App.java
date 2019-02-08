package com.redhat.test;
import org.apache.qpid.amqp_1_0.jms.impl.ConnectionFactoryImpl;

import javax.jms.*;
import javax.naming.Context;
import java.util.Hashtable;

public class App {

    private static final String QUEUE = "sub.gtest.test.workorder";
    //private static final String QUEUE = "gtest.event.notifier";

    public static void main(String[] args) {
        try{
            new App().runTest();}
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void runTest() throws Exception {

        Hashtable<Object, Object> env = new Hashtable<Object, Object>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");
        javax.naming.Context context = new javax.naming.InitialContext(env);

        ConnectionFactory factory = (ConnectionFactory) context.lookup("myFactoryLookup");

        Connection connection = factory.createConnection();

        System.out.println("Getting Configs");

        try {

            connection.start();

            System.out.println("Connection Started");

            Session session=connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);

            Destination destination = session.createQueue(QUEUE);

            MessageConsumer messageConsumer = session.createConsumer(destination);

            System.out.println("Consumer Created. Reading message");

            Message message = messageConsumer.receive();

            if (message != null) {
                System.out.println("Message Read");
                message.setIntProperty("JMS_AMQP_ACK_TYPE", 2);
                message.acknowledge();
            }

            messageConsumer.close();
            connection.close();
            System.out.println("Connection Closed");
        }
        catch (Exception exp) {
            exp.printStackTrace();
            connection.close();
            System.out.println("Connection Closed");
        }
    }
}