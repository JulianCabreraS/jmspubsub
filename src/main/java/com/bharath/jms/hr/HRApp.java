package com.bharath.jms.hr;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class HRApp {
    public static void main(String[] args) throws NamingException {
        InitialContext initialContext = new InitialContext();
        Topic topic = (Topic) initialContext.lookup("topic/empTopic");
        try(
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
                JMSContext jmsContext = connectionFactory.createContext())
        {
            Employee employee = new Employee(123,
                    "Bharath",
                    "Thippireddy",
                    "Software Architect",
                    "bharat@com",
                    "5537779484");
            JMSProducer producer = jmsContext.createProducer();
            producer.send(topic,employee);
            System.out.println("Message Sent");

        }

    }
}
