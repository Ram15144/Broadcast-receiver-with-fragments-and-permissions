package com.example.ram.landscapegallery;

import android.graphics.Bitmap;

/**
 * Created by ram_n on 10/31/2017.
 */

public class Chicago_Landmarks {
    private String name;
    private int img;
    private Bitmap bitmap;

    public Chicago_Landmarks(String name, int img, Bitmap bitmap)
    {
        this.name=name;
        this.img=img;
        this.bitmap=bitmap;
    }
    public String getName()
    {
        return name;
    }
    public int getImg()
    {
        return img;
    }
    public Bitmap getBitmap(){ return bitmap; }
    public void setName(String name)
    {
        this.name= name;
    }
    public void setImg(int img)
    {
        this.img=img;
    }
}
