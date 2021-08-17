package com.example.qrscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.qrscanner.data.RequestUrl;
import com.example.qrscanner.data.ResponseUrl;
import com.example.qrscanner.retrofit.RetrofitAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPOST();

        init();
    }

    private void init() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setCaptureActivity(QrReaderActivity.class);
        intentIntegrator.setPrompt("Test");
        intentIntegrator.initiateScan();
    }

    private void requestPOST() {
        String Base_URL = "http://192.168.219.107:8080/api/";

        RequestUrl reqURL = new RequestUrl("https://테스트주소.com");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI api = retrofit.create(RetrofitAPI.class);

        Call<ResponseUrl> call = api.getUrl(reqURL);

        call.enqueue(new Callback<ResponseUrl>() {
            @Override
            public void onResponse(Call<ResponseUrl> call, Response<ResponseUrl> response) {
                Log.e("Response", "onResponse success");

                ResponseUrl result = response.body();
                Toast.makeText(getApplicationContext(), result.getUrl(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseUrl> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onResponse failed", Toast.LENGTH_SHORT).show();
                Log.e("Response", Log.getStackTraceString(t));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null)
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
            }
        } else super.onActivityResult(requestCode, resultCode, data);
    }


}