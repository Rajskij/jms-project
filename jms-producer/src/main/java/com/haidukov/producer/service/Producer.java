package com.haidukov.producer.service;

import com.haidukov.pojo.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class Producer {
    private final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMessage(String destination, Order order){
        LOGGER.info("Send order from customer: {}, items: {} pieces, liquids: {} liters",
                order.getCustomer().getFullName(),
                order.getItem().getItemNumber(),
                order.getLiquid().getVolume());

        jmsTemplate.convertAndSend(destination, order, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                message.setStringProperty("orderId", order.getOrderId());
                message.setStringProperty("customerFullName", order.getCustomer().getFullName());
                message.setIntProperty("ItemNumber", order.getItem().getItemNumber());
                message.setIntProperty("LiquidVolume", order.getLiquid().getVolume());
                return message;
            }
        });
    }

}
