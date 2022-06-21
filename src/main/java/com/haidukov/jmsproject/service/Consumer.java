package com.haidukov.jmsproject.service;

import com.haidukov.jmsproject.pojo.Order;
import com.haidukov.jmsproject.pojo.OrderState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.haidukov.jmsproject.pojo.OrderState.ACCEPTED;
import static com.haidukov.jmsproject.pojo.OrderState.REJECTED;

@Component
public class Consumer {
    private final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    private OrderProcessingService orderProcessingService;

    @JmsListener(destination = "order.topic", selector = "(ItemNumber > 0) OR (LiquidVolume > 0)")
    public void receiveItemMessage(Order order){
        OrderState orderState = validate(order);
        LOGGER.info("Received order from: {}, items: {} pieces, liquid: {} liters",
                order.getCustomer().getFullName(),
                order.getItem().getItemNumber(),
                order.getLiquid().getVolume());

        orderProcessingService.processOrder(order, orderState);
    }

    private OrderState validate(Order order) {
        if (order.getTotalPrice().compareTo(BigDecimal.valueOf(10000)) >= 0) {
            return REJECTED;
        }
        if (order.getLiquid().getVolume() > 1000) {
            return REJECTED;
        }
        return ACCEPTED;
    }
}
