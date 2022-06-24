package com.haidukov.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    private final String itemId;

    private final int itemNumber;

    @JsonCreator
    public Item(@JsonProperty("itemId") String itemId, @JsonProperty("itemNumber") int itemNumber) {
        this.itemId = itemId;
        this.itemNumber = itemNumber;
    }

    public String getItemId() {
        return itemId;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId='" + itemId + '\'' +
                ", itemNumber=" + itemNumber +
                '}';
    }
}
