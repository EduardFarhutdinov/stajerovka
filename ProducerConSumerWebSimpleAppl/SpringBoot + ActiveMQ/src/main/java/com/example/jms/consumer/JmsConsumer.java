package com.example.jms.consumer;

import com.example.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Component
public class JmsConsumer {
    private static final Logger logger = Logger.getLogger(JmsConsumer.class);
    @Autowired
    JmsTemplate jmsTemplate;
    @Autowired
    MessageConverter messageConverter;
    @JmsListener(destination = "${jsa.activemq.queue.producer}", containerFactory="jsaFactory")
    public void receive(User user) throws JMSException {
        logger.info(user);
    }
}
