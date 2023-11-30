package com.example.privatecloudstorageserver;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.zxing.Result;
import java.util.Scanner;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Scan a QR code by a camera to connect to the network
 *
 **/
public class QRCodeScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView;

    /**
     * call the super class create object sacanner view and set it to content view
     * @param savedInstanceState // TODO:
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        scannerView=new ZXingScannerView(this);
        setContentView(scannerView);

    }

    /**
     * get the QR code from the camera and split it to get required information
     * by it's right format, then display it on screen and send it to Proxy
     * then start connection by MainActivity
     *
     * @param result to save the translated QR code into
     **/
    @Override
    public void handleResult(Result result) {
        MainActivity.result.setText(result.getText());
        onBackPressed();
        String [] qr_info=result.getText().toString().split(",");
        System.out.println("i am here");
        for(String s :qr_info) System.out.println(s);
        Proxy proxy=new Proxy(qr_info[0],Integer.parseInt(qr_info[1]),qr_info[2],qr_info[3]);
        MainActivity.StartConn(proxy);

    }

    /**
     * stop camera on click pause
     **/
    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    /**
     * set the result handler and start camera on resume
     **/
    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}