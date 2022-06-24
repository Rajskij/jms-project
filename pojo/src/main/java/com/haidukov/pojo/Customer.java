package com.haidukov.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {
    private final String customerId;

    private final String fullName;

    @JsonCreator
    public Customer(@JsonProperty("customerId") String customerId, @JsonProperty("fullName") String fullName) {
        this.customerId = customerId;
        this.fullName = fullName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
