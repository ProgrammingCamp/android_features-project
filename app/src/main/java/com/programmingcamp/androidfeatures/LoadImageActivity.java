package com.programmingcamp.androidfeatures;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class LoadImageActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLoadImage;
    ImageView imgvwLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);

        btnLoadImage = (Button)findViewById(R.id.btnLoadImage);
        imgvwLogo = (ImageView)findViewById(R.id.imgvwLogo);

        btnLoadImage.setOnClickListener(this);
    }

    private void LoadImage(String filename, ImageView imageView){
        AssetManager assetManager = getAssets();
        try {
            InputStream image = assetManager.open(filename);
            imageView.setImageBitmap(BitmapFactory.decodeStream(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        LoadImage("small_logo.jpg", imgvwLogo);
    }
}
