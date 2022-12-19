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
import javax.jms.Destination;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
@Named
@SessionScoped
public class sender implements Serializable {

Context context = null;
ConnectionFactory factory = null;
Connection connection = null;
Destination destination = null;
Session session = null;
MessageProducer producer = null;

public sender() {

}

public void sendMessage(String text) {
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
producer = session.createProducer(destination);
connection.start();
TextMessage message = session.createTextMessage();
message.setText(text);
producer.send(message);
System.out.println("Sent: " + message.getText());

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
}


}
