package com.paper.bob.rta.roadtoamsterdam.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.R;

public class DialogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogo);

        String nomeDialogo = getIntent().getExtras().getString("nomeDialogo");
        Log.i("RTA",nomeDialogo);
    }
}
