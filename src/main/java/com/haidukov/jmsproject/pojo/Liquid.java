package com.haidukov.jmsproject.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Liquid {
    private final String liquidId;

    private final int volume;

    @JsonCreator
    public Liquid(@JsonProperty("liquidId") String liquidId, @JsonProperty("volume") int volume) {
        this.liquidId = liquidId;
        this.volume = volume;
    }

    public String getLiquidId() {
        return liquidId;
    }

    public int getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        return "Liquid{" +
                "liquidId='" + liquidId + '\'' +
                ", volume=" + volume +
                '}';
    }
}
