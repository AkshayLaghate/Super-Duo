package it.jaschke.alexandria;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Akki on 25/09/15.
 */
public class ScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    List<BarcodeFormat> formats;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        formats = new ArrayList<>();
        formats.add(BarcodeFormat.EAN_13);
        formats.add(BarcodeFormat.EAN_8);
        mScannerView = new ZXingScannerView(this);// Programmatically initialize the scanner view
        mScannerView.setFormats(formats);
        setContentView(mScannerView);                // Set the scanner view as the content view
    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        String ean = rawResult.getText();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", ean);
        setResult(RESULT_OK, returnIntent);

        Log.v("Scan Result", rawResult.getText()); // Prints scan results
        Log.v("Scan Format", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        finish();
    }
}