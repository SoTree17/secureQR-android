package com.example.qrscanner.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import com.example.qrscanner.data.RequestUrl;
import com.example.qrscanner.data.ResponseUrl;

public interface RetrofitAPI {
    @POST("v1/secureQR")
    Call<ResponseUrl> getUrl(@Body RequestUrl requestUrl);
}
