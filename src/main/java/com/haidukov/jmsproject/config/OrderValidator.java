package com.haidukov.jmsproject.config;

import com.haidukov.jmsproject.pojo.Order;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

public class OrderValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(Order.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Order order = (Order) target;

        if (order.getOrderId() == null){
            errors.reject(null, "order cannot be null");
        } else {
            if (order.getTotalPrice().compareTo(BigDecimal.valueOf(10000)) >= 0) {
                errors.rejectValue("totalPrice", null, "Total price cannot be > 10000");
            }
        }
    }
}
