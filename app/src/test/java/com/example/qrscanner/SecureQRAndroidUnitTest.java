package com.example.qrscanner;

import android.content.Context;
import android.test.mock.MockContext;

import com.secureQR.module.SecureQR;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SecureQRAndroidUnitTest  {
    private SecureQR secureQR;
    Context context;

    @Before
    public void setUp(){
        final String pkgName = "com.example.qrscanner";
        final String activityName = "ResultActivity";
        final String authURL = "http://ec2-3-37-43-9.ap-northeast-2.compute.amazonaws.com:8080/";
        final int QR_RequestCode = 12345;
        // ** 수정 필요
        // context 생성 시 런타임 에러
        context = new MockContext();

        // setContext(context);

        secureQR = new SecureQR(context, pkgName, activityName, authURL, QR_RequestCode);
    }

    @Test

    public void BaseURL_isCorrect() { // Test Case 3 : 서버 base URL 지정 안함
        Assert.assertEquals("http://ec2-3-37-43-9.ap-northeast-2.compute.amazonaws.com:8080/",secureQR.getAuthURL() );
    }
}