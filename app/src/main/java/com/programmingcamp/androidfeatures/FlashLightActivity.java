package com.programmingcamp.androidfeatures;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FlashLightActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnOnOffLight, btnShowAlertDialog;

    private CameraManager mCameraManager;
    private String mCameraId;
    private Boolean isLightOn;
    private int count;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_light);

        btnOnOffLight = (Button) findViewById(R.id.btnOnOffLight);
        btnShowAlertDialog = (Button) findViewById(R.id.btnShowAlertDialog);

        btnOnOffLight.setOnClickListener(this);
        btnShowAlertDialog.setOnClickListener(this);

        isLightOn = false;
        count =0;
        Boolean isFlashAvailable = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isFlashAvailable) {
            Toast.makeText(this, "Flash not availabale", Toast.LENGTH_LONG).show();
            return;
        }

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void turnOnFlashLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                count++;
                if(count ==2){
                    count =0;
                }

                mCameraManager.setTorchMode(mCameraId, true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void turnOffFlashLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View button) {
        if(button.getId() == btnOnOffLight.getId()){
            if (isLightOn) {
                turnOffFlashLight();
                isLightOn = false;
            } else {
                turnOnFlashLight();
                isLightOn = true;
            }
        }
        else if(button.getId() == btnShowAlertDialog.getId()){
            AlertDialog alert = new AlertDialog.Builder(FlashLightActivity.this).create();

            alert.setTitle("Test Message Dialog");
            alert.setMessage("Test Message");

            alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // closing the application
                    finish();
                    System.exit(0);
                }
            });

            alert.show();
        }
    }
}
