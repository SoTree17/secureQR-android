package com.example.qrscanner.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import com.example.qrscanner.data.RequestUrl;
import com.example.qrscanner.data.ResponseUrl;

public interface RetrofitAPI {
    // Base_URL 뒤에 붙는 상세 주소와 요쳥방식을 의미한다. 현재 하드 코드에 의하면, http://192.168.219.107:8080/api/v1/secureQR 주소로 POST 요청하라 지시.
    @POST("v1/secureQR")
    Call<ResponseUrl> getUrl(@Body RequestUrl requestUrl);
    // Call<ResponseUrl>은 서버의 Response 데이터를 ResponseUrl 데이터 형식으로 받겠다는 의미.
    // @Body 어노테이션을 통해 Request body에 RequestUrl 데이터 첨부 명시.
}
