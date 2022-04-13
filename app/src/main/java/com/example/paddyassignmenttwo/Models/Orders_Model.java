package com.example.paddyassignmenttwo.Models;

public class Orders_Model {

    private String name, email, address, date, totalAmount, orderId, customerId;

    public Orders_Model() {
    }

    public Orders_Model(String name, String email, String address, String date, String totalAmount, String orderId, String customerId) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.date = date;
        this.totalAmount = totalAmount;
        this.orderId = orderId;
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
