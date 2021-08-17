package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class QrReaderActivity extends CaptureActivity {
    CaptureManager manager;
    DecoratedBarcodeView decoratedBarcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_reader);

        init();

        manager.initializeFromIntent(getIntent(), savedInstanceState);
        manager.decode();
    }

    private void init() {
        decoratedBarcodeView = findViewById(R.id.QrScanner);
        manager = new CaptureManager(this, decoratedBarcodeView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        manager.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}