package com.example.qrscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.qrscanner.data.RequestDTO;
import com.example.qrscanner.data.ResponseUrl;
import com.example.qrscanner.retrofit.RetrofitAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.secureQR.module.SecureQR;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    // --------------------------- CAUTION! ----------------------------------------------------
    //---------- You have to change below URL to your Auth Server URL ---------------------------

    final String authURL = "http://ec2-3-37-43-9.ap-northeast-2.compute.amazonaws.com:8080/";
    final int QR_RequestCode = 12345;

    SecureQR secureQR;
    Button scanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize
        init();
        ButtonListener();
    }

    // SecureQR 및 Zxing 라이브러리 관련 초기 설정
    private void init() {
        final String pkgName = "com.example.qrscanner";
        final String activityName = "ResultActivity";

        secureQR = new SecureQR(getApplicationContext(), pkgName, activityName, authURL, QR_RequestCode);

        scanButton = findViewById(R.id.scan_button);

        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setCaptureActivity(QrReaderActivity.class);
        intentIntegrator.setPrompt("Test");
        intentIntegrator.setRequestCode(QR_RequestCode);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.initiateScan();
    }

    private void ButtonListener() {
        scanButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, QrReaderActivity.class);
            startActivityForResult(intent, QR_RequestCode);
        });
    }

    // QR 코드를 인식 후, 데이터 꺼내는 함수
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // QrReaderActivity에서 QR 스캔후, 스캔된 데이터는 intent에 담겨 MainActivity로 넘어온다.
        // 그래서 intent에서 result 데이터를 꺼낸다.
        IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);

        secureQR.processResult(result);

        super.onActivityResult(requestCode, resultCode, data);
    }
}