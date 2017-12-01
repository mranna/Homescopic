package com.example.hemanthanil.homescopic;

/**
 * Created by hemanthanil on 2017-11-14.
 */

public class ImageUpload {

    public String name;
    public String url;

    public String getName(){
        return name;
    }
    public String getUrl(){
        return url;

    }
    public ImageUpload(String name ,String url)
    {
        this.name =name;
        this.url=url;
    }
}
