package com.example.nvhuy.navdrawer.models;

public class item_viewpager {
    private int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String tittle, desc;

    public item_viewpager(int image, String tittle, String desc) {
        this.image = image;
        this.tittle = tittle;
        this.desc = desc;
    }
}
