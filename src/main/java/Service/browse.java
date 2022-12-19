/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

/**
 *
 * @author HICHAM
 */

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Properties;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Named
@SessionScoped
public class browse implements Serializable {

    public String browser() {
        Context context = null;
        ConnectionFactory factory = null;
        Connection connection = null;
        String factoryName = "ConnectionFactory";
        String queueName = null;
        Queue queue = null;
        Session session = null;
        QueueBrowser browser = null;
        Properties initialProperties = new Properties();
        String test="";
initialProperties.put(InitialContext.INITIAL_CONTEXT_FACTORY,
"org.exolab.jms.jndi.InitialContextFactory");
initialProperties.put(InitialContext.PROVIDER_URL,
"tcp://192.168.56.1:3035");


        queueName = "queue1";

        try {
            // create the JNDI initial context.
            context = new InitialContext(initialProperties);

            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup("ConnectionFactory");

            // look up the Queue
            queue = (Queue) context.lookup("queue1");

            // create the connection
            connection = factory.createConnection();

            // create the session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // create the browser
            browser = session.createBrowser(queue);

            // start the connection
            connection.start();

            Enumeration messages = browser.getEnumeration();
            while (messages.hasMoreElements()) {
                Message message = (Message) messages.nextElement();
                if (message instanceof TextMessage) {
                    TextMessage text = (TextMessage) message;
                    System.out.println("Browsed: " + text.getText());
                    test += "Browsed: " + text.getText();
                } else if (message != null) {
                    System.out.println("Browsed non text message");
                }
            }
        } catch (JMSException exception) {
            exception.printStackTrace();
        } catch (NamingException exception) {
            exception.printStackTrace();
        } finally {
            // close the context
            if (context != null) {
                try {
                    context.close();
                } catch (NamingException exception) {
                    exception.printStackTrace();
                }
            }

            // close the connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException exception) {
                    exception.printStackTrace();
                }
            }
        }
        return test;
    }
}
