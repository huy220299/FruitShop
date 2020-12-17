package com.example.nvhuy.navdrawer.models;

public class Category {
    public Category(int img, String text) {
        this.img = img;
        this.text = text;
    }

    private int img;
    private String text;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
