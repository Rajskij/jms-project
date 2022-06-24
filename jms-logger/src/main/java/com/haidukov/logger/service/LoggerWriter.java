package com.haidukov.logger.service;


import com.haidukov.pojo.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.haidukov.logger.config.JmsConfig.CANCELED_QUEUE;
import static com.haidukov.logger.config.JmsConfig.PROCESSED_QUEUE;

@Component
public class LoggerWriter {
    private final Logger LOGGER = LoggerFactory.getLogger(LoggerWriter.class);

    @JmsListener(destination = PROCESSED_QUEUE)
    public void receiveLogsProcessed(Order order) {
        LOGGER.info("Received order from: {}", order.getCustomer().getFullName());
    }

    @JmsListener(destination = CANCELED_QUEUE)
    public void receiveLogsCanceled(Order order) {
        LOGGER.info("Canceled order from: {}", order.getCustomer().getFullName());
    }
}
