package com.example.qrscanner.data;

import com.google.gson.annotations.SerializedName;

public class ResponseUrl {
    @SerializedName("resURL")
    String resURL;

    public String getUrl() {
        return this.resURL;
    }
}
