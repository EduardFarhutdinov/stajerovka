package com.example.jms.producer;

import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Enumeration;

@Component
public class JmsProducer {
    @Autowired
    JmsTemplate jmsTemplate;
    @Value("${jsa.activemq.queue.producer}")
    String queue;

    public void send(User user) {
        jmsTemplate.convertAndSend(queue, user, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                message.setStringProperty("USER","WEB");
                return message;
            }
        });
    }
}
