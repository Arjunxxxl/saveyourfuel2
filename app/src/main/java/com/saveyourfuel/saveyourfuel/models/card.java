package com.saveyourfuel.saveyourfuel.models;
import android.graphics.Bitmap;

import com.saveyourfuel.saveyourfuel.R;

/**
 * Created by root on 11/2/18.
 */

public class card {
    public String title;
    public String company;
    public String name,phone;

    public String price;
    public String model;
    public String distance;
    public String condition;
    public Bitmap thumbnail;
    public String p_id;

    public card(String title, String company,String price, Bitmap thumbnail,String p_id,String name, String phone, String model, String distance, String condition){
        this.title = title;
        this.company = company;
        this.price = price;
        this.thumbnail = thumbnail;
        this.name = name;
        this.phone = phone;
        this.p_id = p_id;
        this.model = model;
        this.distance = distance;
        this.condition = condition;

    }
}
