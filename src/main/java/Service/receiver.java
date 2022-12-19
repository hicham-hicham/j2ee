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
import java.util.Properties;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Destination;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;


@Named
@SessionScoped
public class receiver implements Serializable {

Context context = null;
ConnectionFactory factory = null;
Connection connection = null;
Destination destination = null;
Session session = null;
MessageConsumer consumer = null;
String test="";

public receiver() {

}

public String receiveMessage() {
Properties initialProperties = new Properties();
initialProperties.put(InitialContext.INITIAL_CONTEXT_FACTORY,
"org.exolab.jms.jndi.InitialContextFactory");
initialProperties.put(InitialContext.PROVIDER_URL,
"tcp://192.168.56.1:3035");
try {
context = new InitialContext(initialProperties);
factory = (ConnectionFactory) context.lookup("ConnectionFactory");
destination = (Destination) context.lookup("queue1");
connection = factory.createConnection();
session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
consumer = session.createConsumer(destination);
connection.start();
Message message = consumer.receive();
if (message instanceof TextMessage) {
    System.out.println("Message is : ");
TextMessage text = (TextMessage) message;
System.out.println("Message is : " + text.getText());
test = "Message is : " + text.getText();
}else if (message != null) {
    test = "There is no messages";
}

} catch (JMSException ex) {
ex.printStackTrace();
} catch (NamingException ex) {
ex.printStackTrace();
}

if (context != null) {
try {
context.close();
} catch (NamingException ex) {
ex.printStackTrace();
}
}

if (connection != null) {
try {
connection.close();
} catch (JMSException ex) {
ex.printStackTrace();
}
}
return test;
}
}
