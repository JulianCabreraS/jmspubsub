package com.bharath.jms.welness;

import com.bharath.jms.hr.Employee;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class WelnessApp {
    public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
        InitialContext initialContext = new InitialContext();
        Topic topic = (Topic) initialContext.lookup("topic/empTopic");
        try(
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
                JMSContext jmsContext = connectionFactory.createContext())
        {

            JMSConsumer consumer = jmsContext.createSharedConsumer(topic,"sharedConsumer");
            JMSConsumer consumer2 = jmsContext.createSharedConsumer(topic,"sharedConsumer");
            consumer.close();

            Thread.sleep(10000);

            consumer = jmsContext.createDurableConsumer(topic,"subscription1");
            Message message = consumer.receive();
            Employee employee = message.getBody(Employee.class);
            System.out.println(employee.getFirstName());
            consumer.close();
            jmsContext.unsubscribe("subscription1");

        }

    }
}
