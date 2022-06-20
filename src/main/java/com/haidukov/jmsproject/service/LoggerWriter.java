package com.haidukov.jmsproject.service;

import com.haidukov.jmsproject.pojo.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.haidukov.jmsproject.config.JmsConfig.CANCELED_QUEUE;
import static com.haidukov.jmsproject.config.JmsConfig.PROCESSED_QUEUE;

@Component
public class LoggerWriter {
    private final Logger LOGGER = LoggerFactory.getLogger(LoggerWriter.class);

    @JmsListener(destination = PROCESSED_QUEUE)
    public void receiveLogsProcessed(Order order) {
        LOGGER.info("Received LOGG message: {}", order);
    }

    @JmsListener(destination = CANCELED_QUEUE)
    public void receiveLogsCanceled(Order order) {
        LOGGER.info("Received LOGG message: {}", order);
    }
}
