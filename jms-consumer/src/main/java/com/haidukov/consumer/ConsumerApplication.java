package com.haidukov.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.util.FileSystemUtils;

import java.io.File;

@EnableJms
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        FileSystemUtils.deleteRecursively(new File("activemq-data"));


        ConfigurableApplicationContext context = SpringApplication.run(ConsumerApplication.class, args);
    }
}
