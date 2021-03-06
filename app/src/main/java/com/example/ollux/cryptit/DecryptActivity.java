package com.example.ollux.cryptit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;

public class DecryptActivity extends AppCompatActivity {
    Globals globals = (Globals)getApplication();
    //USER currUser = globals.currUser;
    String decryptedText;
    ArrayList<String> friendNames = globals.friendNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);
        Button btn = (Button) findViewById(R.id.decryptButton);
        Button clipboardbtn = (Button) findViewById(R.id.copytoclipboard);
        final EditText cipherText = (EditText) findViewById(R.id.cipherText);

        final Spinner spinner = (Spinner) findViewById(R.id.friendArray);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.spinner_item,
                        friendNames); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String encryptText = cipherText.getText().toString();
                String text = spinner.getSelectedItem().toString();
                KeyPair tempKeys = globals.keyPairs.get(globals.friendNames.indexOf(text));
                try {
                    decryptedText=RSA.decrypt(encryptText, tempKeys.getPrivate());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cipherText.setText(decryptedText);
            }

        });
        cipherText.setText(decryptedText);

        clipboardbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", cipherText.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast toast=Toast.makeText(getApplicationContext(),"Copied Text to Clipboard",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0,50);
                toast.show();
            }

        });
    }
}
