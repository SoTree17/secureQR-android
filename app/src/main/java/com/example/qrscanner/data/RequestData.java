package com.example.qrscanner.data;

import com.google.gson.annotations.SerializedName;

// Client가 서버에 요청시 body에 첨부할 데이터
public class RequestData {
    @SerializedName("reqData")
    String reqData;

    public RequestData(String data) {
        this.reqData = data;
    }

    public String getUrl() {
        return reqData;
    }
}
