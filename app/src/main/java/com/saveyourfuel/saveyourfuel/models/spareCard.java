package com.saveyourfuel.saveyourfuel.models;

import android.graphics.Bitmap;

/**
 * Created by root on 22/3/18.
 */

public class spareCard {

    public String title;
    public String description;
    public String sparePartName;
    public String name,phone;

    public String price;
    public Bitmap thumbnail;
    public String p_id;

    public spareCard(String title, String description, String price, Bitmap thumbnail, String p_id, String name, String phone, String sparePartName){
        this.title = title;
        this.description = description;
        this.price = price;
        this.sparePartName = sparePartName;
        this.thumbnail = thumbnail;
        this.name = name;
        this.phone = phone;
        this.p_id = p_id;

    }
}
