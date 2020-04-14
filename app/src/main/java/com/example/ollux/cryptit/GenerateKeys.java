package com.example.ollux.cryptit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GenerateKeys extends AppCompatActivity {
    Globals globals = (Globals)getApplication();
    USER currUser = globals.currUser;
    RSA myRSA = globals.myRSA;
    Button btn = (Button) findViewById(R.id.generateKeys);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_keys);
        Button btn = (Button) findViewById(R.id.generateKeys);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    globals.currUser.pair = myRSA.generateKeyPair();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
