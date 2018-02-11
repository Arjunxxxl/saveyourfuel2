package com.saveyourfuel.saveyourfuel.models;
import com.saveyourfuel.saveyourfuel.R;

/**
 * Created by root on 11/2/18.
 */

public class card {
    public String title;
    public String description;
    public int icon;
    public card(String title, String discription,int src){
        this.title=title;
        this.description = discription;
        this.icon = src;
    }
}
