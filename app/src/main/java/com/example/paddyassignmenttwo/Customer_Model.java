package com.example.paddyassignmenttwo;

public class Customer_Model {

    private String name, email, address, payment, type, card, cardExpiryDate;

    public Customer_Model() {
    }

    public Customer_Model(String name, String email, String address, String payment, String type, String card, String cardExpiryDate) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.payment = payment;
        this.type = type;
        this.card = card;
        this.cardExpiryDate = cardExpiryDate;
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

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }
}
