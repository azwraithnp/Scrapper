package com.example.avinashmishra.scrapper;

public class Item {

    String name;
    Double whole_price, retail_price;

    public Item(String name, Double whole_price, Double retail_price) {
        this.name = name;
        this.whole_price = whole_price;
        this.retail_price = retail_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWhole_price() {
        return whole_price;
    }

    public void setWhole_price(Double whole_price) {
        this.whole_price = whole_price;
    }

    public Double getRetail_price() {
        return retail_price;
    }

    public void setRetail_price(Double retail_price) {
        this.retail_price = retail_price;
    }
}
