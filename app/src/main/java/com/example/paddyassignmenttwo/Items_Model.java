package com.example.paddyassignmenttwo;

public class Items_Model {

    private String title, description, price, manufacturer, category;

    public Items_Model() {
    }

    public Items_Model(String title, String description, String price, String manufacturer, String category) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.manufacturer = manufacturer;
        this.category = category;
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
}
