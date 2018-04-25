package com.programmingcamp.androidfeatures;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class PlayVideoActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnPlayVideo;
    VideoView videoViewTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        btnPlayVideo = (Button)findViewById(R.id.btnPlayVideo);
        videoViewTraining = (VideoView)findViewById(R.id.videoViewTraining);

        btnPlayVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        VideoView videoViewTraining = (VideoView) findViewById(R.id.videoViewTraining);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.drivers_training);
        videoViewTraining.setVideoURI(uri);
        videoViewTraining.start();
    }
}
