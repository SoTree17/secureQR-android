package com.example.qrscanner.data;

import com.google.gson.annotations.SerializedName;

// Client가 서버에 요청시 body에 첨부할 데이터
public class RequestDTO {
    @SerializedName("requestURL")
    String requestURL;

    @SerializedName("c_index")
    Integer c_index;

    @SerializedName("d_index")
    Integer d_index;

    @SerializedName("data")
    String data;

    public RequestDTO(String requestURL, Integer c_index, Integer d_index, String data) {
        this.requestURL = requestURL;
        this.c_index = c_index;
        this.d_index = d_index;
        this.data = data;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public Integer getC_index() {
        return c_index;
    }


    public Integer getD_index() {
        return d_index;
    }


    public String getData() {
        return data;
    }
}
