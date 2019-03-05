package com.paper.bob.rta.roadtoamsterdam.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import com.paper.bob.rta.roadtoamsterdam.R;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        VideoView view = findViewById(R.id.videoView);
        String video = getIntent().getExtras().getString("video");
        String path = "android.resource://" + getPackageName() + "/raw/" + video;
        Log.i("RTA","@VIDEO\n\tPath: "+path);
        view.setVideoURI(Uri.parse(path));
        view.start();
        view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });

    }


}
