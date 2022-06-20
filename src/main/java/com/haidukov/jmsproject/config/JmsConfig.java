package com.haidukov.jmsproject.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.jms.ConnectionFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableJms
@EnableTransactionManagement
@Configuration
public class JmsConfig/* implements JmsListenerConfigurer*/ {
    private static final Logger LOGGER = LoggerFactory.getLogger(JmsConfig.class);

    public static final String PROCESSED_QUEUE = "order.processed.queue";

    public static final String CANCELED_QUEUE = "order.canceled.queue";

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String user;

    @Value("${spring.activemq.password}")
    private String password;

//    @Override
//    public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
//        List<String> queueList = Arrays.asList(PROCESSED_QUEUE, CANCELED_QUEUE);
//        int i = 0;
//        for (String queue : queueList) {
//            SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
//            endpoint.setId("myJmsEndpoint-" + i++);
//            endpoint.setDestination(queue);
//            endpoint.setMessageListener(message -> {
//                LOGGER.debug("***********************************************receivedMessage:" + message);
//            });
//            registrar.registerEndpoint(endpoint);
//            LOGGER.debug("registered the endpoint for queue" + queue);
//        }
//    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory(new ActiveMQConnectionFactory(user, password, brokerUrl));
        factory.setClientId("StoreFront");
        factory.setSessionCacheSize(100);
        return factory;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public PlatformTransactionManager jmsTransactionManager(){
        return new JmsTransactionManager(connectionFactory());
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(jacksonJmsMessageConverter());
        factory.setTransactionManager(jmsTransactionManager());
        factory.setErrorHandler(t -> {
            LOGGER.info("Handling error in listening for messages, error: " + t.getMessage());
        });
        return factory;
    }

//    @Bean
//    public DefaultMessageHandlerMethodFactory methodFactory() {
//        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
//        factory.setValidator(validatorFactory());
//        return factory;
//    }
//
//    @Bean
//    public Validator validatorFactory(){
//        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
//        factory.setProviderClass(new OrderValidator());
//        return factory;
//    }

    /**
     * Another factory for learning purpose
     */
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
        jmsTemplate.setDeliveryPersistent(true);
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }

}
