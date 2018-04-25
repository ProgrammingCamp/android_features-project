package com.programmingcamp.androidfeatures;

import android.Manifest;
import android.content.pm.PackageManager;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ManageCallLogActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtPhoneNumber;
    Button btnDeleteCallLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_call_log);

        txtPhoneNumber = (EditText) findViewById(R.id.txtPhoneNumber);
        btnDeleteCallLog = (Button) findViewById(R.id.btnDeleteCallLog);

        btnDeleteCallLog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        char[] number = txtPhoneNumber.getText().toString().trim().toCharArray();
        String n = "%";

        if(number.length <= 10) {
            for (int i = 1; i < number.length; i++) {
                if(number[i] != ' '){
                    n = n + (number[i] + "%");
                }
            }
        }
        else{
            int starter = number.length - 9;
            for (int i = starter; i < number.length; i++) {
                if(number[i] != ' '){
                    n = n + (number[i] + "%");
                }
            }
        }

        String queryString = CallLog.Calls.NUMBER.trim() + " LIKE '" + n + "'";
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        this.getContentResolver().delete(CallLog.Calls.CONTENT_URI, queryString, null);
    }
}
