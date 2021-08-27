package com.example.qrscanner.data;

import com.google.gson.annotations.SerializedName;

// Client가 서버에 요청시 body에 첨부할 데이터
public class RequestIndex {
    @SerializedName("reqURL")
    Integer reqIndex;

    public RequestIndex(Integer index) {
        this.reqIndex = index;
    }

    public Integer getUrl() {
        return reqIndex;
    }
}
