package com.example.qrscanner.data;

import com.google.gson.annotations.SerializedName;

// Client가 서버에 요청시 body에 첨부할 데이터
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
