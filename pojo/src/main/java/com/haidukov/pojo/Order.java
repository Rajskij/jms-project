package com.haidukov.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Order {
    private final BigDecimal itemPrice = BigDecimal.valueOf(3);

    private final BigDecimal liquidPrice = BigDecimal.valueOf(2);

    private final String orderId;

    private final Item item;

    private final Liquid liquid;

    private final Customer customer;

    private final BigDecimal totalPrice;

    @JsonCreator
    public Order(@JsonProperty("orderId") String orderId,
                 @JsonProperty("item") Item item,
                 @JsonProperty("liquid") Liquid liquid,
                 @JsonProperty("customer") Customer customer) {
        this.orderId = orderId;
        this.item = item;
        this.liquid = liquid;
        this.customer = customer;
        this.totalPrice = itemPrice.pow(item.getItemNumber())
                .add(liquidPrice.pow(liquid.getVolume()));
    }

    public String getOrderId() {
        return orderId;
    }

    public Item getItem() {
        return item;
    }

    public Liquid getLiquid() {
        return liquid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", item=" + item +
                ", liquid=" + liquid +
                ", customer=" + customer +
                '}';
    }
}