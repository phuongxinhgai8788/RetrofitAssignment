package com.example.retrofitassignment.network;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MarsProperty implements Serializable {
    public String id;
    public String type;
    public @SerializedName("img_src")
    String imgSrcUrl;
    public Double price;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getImgSrcUrl() {
        return imgSrcUrl;
    }

    public Double getPrice() {
        return price;
    }
}
