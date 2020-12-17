package com.example.nvhuy.navdrawer.models;

public class Fruit {
    int id, img, count,numStar;
    String name, cost, unit, description, company;
    public Fruit(int id, String name, int count, String unit, String cost, String company, String description, int img) {
        this.id = id;
        this.img = img;
        this.count = count;
        this.name = name;
        this.cost = cost;
        this.unit = unit;
        this.description = description;
        this.company = company;
        this.numStar = 0;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String introduce) {
        this.description = introduce;
    }

    public int getCount() {
        return count;
    }

    public int getNumStar() {
        return numStar;
    }

    public void setNumStar(int numStar) {
        this.numStar = numStar;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
