package com.example.privatecloudstorageserver;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static TextView result;
    Button scan_btn;

    /**
     * Creates button to start scanning and text view to show the content of QR code
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create Text View to show the information which are read from QR Code
        result=(TextView)findViewById(R.id.result_text);
        //Create Button to scan
        scan_btn=(Button) findViewById(R.id.btn_scan);
        //set action on click button
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open QR code for scanning
                startActivity(new Intent(getApplicationContext(),QRCodeScanner.class));
            }
        });
    }

    /**
     * starts connection between proxy and other servers
     * @param proxyObj
     */
    public static void StartConn(Proxy proxyObj)
    {
        //Create object from NetworkManager class to start server
        NetworkManager networkManager = new NetworkManager(proxyObj);
        networkManager.StartServer();
    }
}