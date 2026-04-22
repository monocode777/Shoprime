package com.example.shoprime;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String orderId;
    private List<CartItem> items;
    private double total;
    private long timestamp;
    private String buyerEmail;

    public Order(String orderId, List<CartItem> items, double total, long timestamp, String buyerEmail) {
        this.orderId = orderId;
        this.items = items;
        this.total = total;
        this.timestamp = timestamp;
        this.buyerEmail = buyerEmail;
    }

    public String getOrderId() { return orderId; }
    public List<CartItem> getItems() { return items; }
    public double getTotal() { return total; }
    public long getTimestamp() { return timestamp; }
    public String getBuyerEmail() { return buyerEmail; }
}