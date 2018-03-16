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
    public Bitmap thumbnail;
    public String p_id;

    public card(String title, String company,String price, Bitmap thumbnail,String p_id,String name, String phone){
        this.title = title;
        this.company = company;
        this.price = price;
        this.thumbnail = thumbnail;
        this.name = name;
        this.phone = phone;
        this.p_id = p_id;

    }
}
