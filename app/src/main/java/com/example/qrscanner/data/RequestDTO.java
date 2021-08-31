package com.example.qrscanner.data;

import com.google.gson.annotations.SerializedName;

// Client가 서버에 요청시 body에 첨부할 데이터
public class RequestDTO {
    @SerializedName("requestURL")
    String requestURL;

    @SerializedName("index")
    Integer index;

    @SerializedName("data")
    String data;

    public RequestDTO(String requestURL, Integer index, String data) {
        this.requestURL = requestURL;
        this.index = index;
        this.data = data;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public Integer getIndex() {
        return index;
    }

    public String getData() {
        return data;
    }
}
