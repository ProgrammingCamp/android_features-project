package com.programmingcamp.androidfeatures;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView lblUsername, lblPassword;
    EditText txtUsername, txtPassword;

    CheckBox chkRemember;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lblUsername = (TextView) findViewById(R.id.lblUsername);
        lblPassword = (TextView) findViewById(R.id.lblPassword);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        chkRemember = (CheckBox) findViewById(R.id.chkRemember);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        SharedPreferences prefs = this.getSharedPreferences("LoginSetting", 0);
        String username =  prefs.getString("Username", "");
        String password =  prefs.getString("Password", "");

        txtUsername.setText(username);
        txtPassword.setText(password);
    }

    @Override
    public void onClick(View v) {
        if(chkRemember.isChecked()){
            SharedPreferences prefs = this.getSharedPreferences("LoginSetting", 0);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("Username", txtUsername.getText().toString());
            editor.putString("Password", txtPassword.getText().toString());

            editor.apply();

            Toast.makeText(this, "Setting Saved", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Setting Not Saved", Toast.LENGTH_LONG).show();
        }

    }
}
