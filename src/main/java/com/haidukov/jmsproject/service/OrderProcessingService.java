package com.haidukov.jmsproject.service;

import com.haidukov.jmsproject.pojo.Order;
import com.haidukov.jmsproject.pojo.OrderState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.haidukov.jmsproject.config.JmsConfig.CANCELED_QUEUE;
import static com.haidukov.jmsproject.config.JmsConfig.PROCESSED_QUEUE;
import static com.haidukov.jmsproject.pojo.OrderState.ACCEPTED;

@Service
public class OrderProcessingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProcessingService.class);

    @Autowired
    JmsTemplate jmsTemplate;

    @Transactional
    public void processOrder(Order order, OrderState state) {
        if (ACCEPTED.equals(state)) {
            jmsTemplate.convertAndSend(PROCESSED_QUEUE, order);
        } else {
            jmsTemplate.convertAndSend(CANCELED_QUEUE, order);
        }
    }

}
