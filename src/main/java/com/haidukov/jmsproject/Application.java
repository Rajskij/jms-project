package com.haidukov.jmsproject;

import com.haidukov.jmsproject.pojo.Customer;
import com.haidukov.jmsproject.pojo.Item;
import com.haidukov.jmsproject.pojo.Liquid;
import com.haidukov.jmsproject.pojo.Order;
import com.haidukov.jmsproject.service.Producer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;

import java.util.UUID;

@EnableJms
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        Producer producer = context.getBean(Producer.class);

        try {
            producer.sendMessage("order.topic", buildAcceptedOrder());
            producer.sendMessage("order.topic", buildCanceledOrder());
            producer.sendMessage("order.topic", buildEmptyOrder());
        } catch (Exception exception) {
            System.out.println("Error occurred!" + exception.getLocalizedMessage());
        }
    }

    private static Order buildAcceptedOrder() {
        Liquid liquid = new Liquid(uuid(), 7);
        Item item = new Item(uuid(), 3);
        Customer customer = new Customer(uuid(), "First Customer");

        return new Order(uuid(), item, liquid, customer);
    }

    private static Order buildCanceledOrder() {
        Liquid liquid = new Liquid(uuid(), 100_000);
        Item item = new Item(uuid(), 3);
        Customer customer = new Customer(uuid(), "Second Customer");

        return new Order(uuid(), item, liquid, customer);
    }

    private static Order buildEmptyOrder() {
        Liquid liquid = new Liquid(uuid(), 0);
        Item item = new Item(uuid(), 0);
        Customer customer = new Customer(uuid(), "Third Customer");

        return new Order(uuid(), item, liquid, customer);
    }

    private static String uuid() {
        return UUID.randomUUID().toString();
    }
}
