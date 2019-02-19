package com.paper.bob.rta.roadtoamsterdam.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

import com.paper.bob.rta.roadtoamsterdam.R;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        VideoView view = (VideoView)findViewById(R.id.videoView);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.dam420;
        view.setVideoURI(Uri.parse(path));
        view.start();
        final Intent pl = new Intent(this, PlatformMainActivity.class);
        view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                startActivity(pl);
                finish();
            }
        });
    }


}
