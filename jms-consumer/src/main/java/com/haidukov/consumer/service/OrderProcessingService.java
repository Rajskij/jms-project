package com.haidukov.consumer.service;

import com.haidukov.pojo.Order;
import com.haidukov.pojo.OrderState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.haidukov.consumer.config.JmsConfig.CANCELED_QUEUE;
import static com.haidukov.consumer.config.JmsConfig.PROCESSED_QUEUE;
import static com.haidukov.pojo.OrderState.ACCEPTED;

@Service
public class OrderProcessingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProcessingService.class);

    @Autowired
    JmsTemplate jmsTemplate;

    @Transactional
    public void processOrder(Order order, OrderState state) {
        if (ACCEPTED.equals(state)) {
            LOGGER.info("Process order sent");
            jmsTemplate.convertAndSend(PROCESSED_QUEUE, order);
        } else {
            LOGGER.info("Canceled order sent");
            jmsTemplate.convertAndSend(CANCELED_QUEUE, order);
        }
    }

}
