package com.example.qrscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

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

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    final int RequestCode = 0x0000c0de;
    Button scanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 테스트를 위해 여기에서 호출했음, 최종 위치는 onActivityResult()로 가야될거 같음.
        //requestPOST_test();

        // initialize
        init();
        ButtonListener();
    }

    // Zxing 라이브러리 관련 초기 설정
    private void init() {
        scanButton = findViewById(R.id.scan_button);
        
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setCaptureActivity(QrReaderActivity.class);
        intentIntegrator.setPrompt("Test");
        intentIntegrator.initiateScan();
    }

    private void ButtonListener() {
        scanButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, QrReaderActivity.class);
            startActivityForResult(intent, RequestCode);
        });
    }

    private void requestPOST(RequestDTO data) {  // data = Request body에 추가할 데이터
        // 요청할 서버의 주소
        String Base_URL = data.getRequestURL();

        // Retrofit 인스턴스 생성
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 사용자 지정 인터페이스(RetrofitAPI.java)를 Retrofit 라이브러리에 의해 인스턴스로 자동 구현
        RetrofitAPI api = retrofit.create(RetrofitAPI.class);

        // 인터페이스 함수를 호출하여 Call 객체 생성 (이때, body 데이터를 넣어준다.)
        Call<ResponseUrl> call = api.getUrl(data);

        // Call 객체를 통해 서버에 요청
        call.enqueue(new Callback<ResponseUrl>() {

            // 서버에서 응답 성공
            @Override
            public void onResponse(Call<ResponseUrl> call, Response<ResponseUrl> response) {
                Log.e("Response", "onResponse success");

                // Response body에서 데이터 꺼내기
                ResponseUrl result = response.body();
                Toast.makeText(getApplicationContext(), result.getUrl(), Toast.LENGTH_SHORT).show();
                openCustomTab(result.getUrl());
            }

            // 서버에서 응답 실패
            @Override
            public void onFailure(Call<ResponseUrl> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onResponse failed", Toast.LENGTH_SHORT).show();
                Log.e("Response", Log.getStackTraceString(t));
            }
        });
    }

    // QR 코드를 인식 후, 데이터 꺼내는 함수
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // QrReaderActivity에서 QR 스캔후, 스캔된 데이터는 intent에 담겨 MainActivity로 넘어온다.
        // 그래서 intent에서 result 데이터를 꺼낸다.
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            // 결과값이 없으면
            if (result.getContents() == null)
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            // 결과값이 제대로 있으면 Toast 메시지를 통해 출력
            else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();

                // Pair 로 json parsing
                String raw_data = result.getContents();

                // Json 형식일때만, 별도의 데이터 추출 후, Request한다.
                if (isJSON(raw_data)) {
                    RequestDTO parsed_data = jsonParsing(raw_data);
                    if(parsed_data.getC_index() != -1) {
                        requestPOST(parsed_data);
                    }
                } else {
                    openCustomTab(raw_data);
                }
            }
        } else super.onActivityResult(requestCode, resultCode, data);
    }

    public RequestDTO jsonParsing(String raw_data) {
        // error handling 위한 변수 초기화
        int c_index = -1;
        int d_index = -1;

        String requestURL = "error handling";
        String data = "";

        // string to json
        JsonObject jsonObject = new JsonParser().parse(raw_data).getAsJsonObject();
        
        
        // 입력된 json에서 requestURL 이나 index key가 없는 경우 에러 출력
        if ((!jsonObject.has("requestURL")) ||
                (!jsonObject.has("c_index"))) {
            Toast.makeText(this, "Your Json is not for Secure QR!", Toast.LENGTH_SHORT).show();
        }
        // requestURL, index 파싱 후 return
        else {
            requestURL = jsonObject.get("requestURL").getAsString();
            c_index = jsonObject.get("c_index").getAsInt();
            d_index = jsonObject.get("d_index").getAsInt();
            data = jsonObject.get("data").getAsString();
        }

        return new RequestDTO(requestURL, c_index, d_index, data);
    }

    // String이 JSON인지 확인 (Json Array는 Json 아닌걸로 취급했음)
    public boolean isJSON(String s) {
        try {
            new JSONObject(s);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    // 인터넷 창(Chrome Custom Tabs) 띄우기
    public void openCustomTab(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}