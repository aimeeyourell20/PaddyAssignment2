package com.example.paddyassignmenttwo;

public class Items_Model {

    private String title, description, price, manufacturer, category, itemID;
    private int quantity;

    public Items_Model() {
    }

    public Items_Model(String title, String description, String price, String manufacturer, String category, String itemID, int quantity) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.manufacturer = manufacturer;
        this.category = category;
        this.itemID = itemID;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
