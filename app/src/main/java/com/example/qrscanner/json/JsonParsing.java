package com.example.qrscanner.json;

import android.util.Pair;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonParsing {
    private String raw_data;
    int index;
    String requestURL;
    String data;

    public JsonParsing(String raw_json_data){
        raw_data = raw_json_data;
        index = -1;
        requestURL = "Default URL";
        data = "Default Data";
    }

    public int getIndex(){
        return index;
    }

    public String getRequestURL(){
        return requestURL;
    }

    public String getData(){
        return data;
    }

    public void jsonParsing() {
        // error handling 위한 변수 초기화

        // string to json
        JsonObject jsonObject = new JsonParser().parse(raw_data).getAsJsonObject();


        // 입력된 json에서 requestURL 이나 index key가 없는 경우 에러 출력
        if ((!jsonObject.has("requestURL")) ||
                (!jsonObject.has("index"))||(!jsonObject.has("data"))
            ) {
            throw new IllegalArgumentException("You've got Wrong QR");

        }
        // requestURL, index 파싱 후 return
        else {
            requestURL = jsonObject.get("requestURL").getAsString();
            index = jsonObject.get("index").getAsInt();
            data = jsonObject.get("data").getAsString();
        }
    }
}
