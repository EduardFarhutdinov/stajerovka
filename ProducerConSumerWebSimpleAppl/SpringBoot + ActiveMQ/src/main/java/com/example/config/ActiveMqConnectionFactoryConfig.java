package com.example.config;

import com.example.model.User;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import javax.jms.ConnectionFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJms
public class ActiveMqConnectionFactoryConfig {
    @Value("${jsa.activemq.broker.url}")
    String brokerURL;

    @Value("${jsa.activemq.broker.username}")
    String userName;

    @Value("${jsa.activemq.broker.password}")
    String password;

    @Bean
    public ConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerURL);
        connectionFactory.setUserName(userName);
        connectionFactory.setPassword(password);

        return connectionFactory;
    }

    @Bean
    public MappingJackson2MessageConverter jacksonJmsMessageConverer(){
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTypeIdPropertyName("_typeId");
        Map<String,Class<?>> typeIdMappings = new HashMap<String, Class<?>>();
        typeIdMappings.put("user", User.class);
        converter.setTypeIdMappings(typeIdMappings);
//        converter.setTargetType(MessageType.TEXT);
//        converter.setTypeIdPropertyName("_type");

        return converter;
    }
    @Bean
    public JmsListenerContainerFactory<?> jsaFactory(ConnectionFactory connectionFactory,
                                                     DefaultJmsListenerContainerFactoryConfigurer configurer){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setMessageConverter(jacksonJmsMessageConverer());
        configurer.configure(factory,connectionFactory);
        return factory;

    }
    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate template = new JmsTemplate();
        template.setMessageConverter(jacksonJmsMessageConverer());
        template.setConnectionFactory(connectionFactory());
        return template;
    }

}
