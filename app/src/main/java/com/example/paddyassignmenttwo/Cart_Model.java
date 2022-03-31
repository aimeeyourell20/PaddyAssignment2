package com.example.paddyassignmenttwo;

public class Cart_Model {

    private String itemID, title, price, quantity, discount;

    public Cart_Model() {
    }

    public Cart_Model(String itemID, String title, String price, String quantity, String discount) {
        this.itemID = itemID;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
