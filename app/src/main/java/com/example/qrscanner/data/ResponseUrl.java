package com.example.qrscanner.data;

import com.google.gson.annotations.SerializedName;

// 서버 Response로 받을 데이터
public class ResponseUrl {
    @SerializedName("resURL")
    String resURL;

    public String getUrl() {
        return this.resURL;
    }
}
