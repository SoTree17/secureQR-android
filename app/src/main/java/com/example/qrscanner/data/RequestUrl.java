package com.example.qrscanner.data;

import com.google.gson.annotations.SerializedName;

public class RequestUrl {
    @SerializedName("reqURL")
    String reqURL;

    public RequestUrl(String url) {
        this.reqURL = url;
    }

    public String getUrl() {
        return reqURL;
    }
}
