package com.paper.bob.rta.roadtoamsterdam.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.paper.bob.rta.roadtoamsterdam.R;
import com.paper.bob.rta.roadtoamsterdam.activity.GameComposerActivity;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences prefs;
    private Button btnNuovaPartita;
    private Button btnCarPartita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        prefs = this.getSharedPreferences("com.paper.bob.rta.roadtoamsterdam", MODE_PRIVATE);
        btnCarPartita = findViewById(R.id.carPartita);
        btnNuovaPartita= findViewById(R.id.nuovaPartita);
        final Context c = getApplicationContext();
        btnCarPartita.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(c,"Caricamento In Corso",Toast.LENGTH_LONG).show();
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                launchSaveGame();
                            }
                        },
                        5
                );
            }
        });

        btnNuovaPartita.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(c,"Caricamento In Corso",Toast.LENGTH_LONG).show();
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                launchNewGame();
                            }
                        },
                        5
                );
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onResume()
    {
        super.onResume();
        if(getSaveData() == 0)
        {btnCarPartita.setEnabled(false);}
    }

    public int getSaveData()
    {
        // use a default value using new Date()
        int savegame = prefs.getInt("savegame", 0);
        Log.i("RTA","Level save: n."+ savegame);
        return savegame;
    }

    private void launchSaveGame() {
        Intent intent = new Intent(this, GameComposerActivity.class);
        intent.putExtra("savegame", getSaveData());
        startActivity(intent);
    }

    private void launchNewGame() {
        Intent intent = new Intent(this, GameComposerActivity.class);
        intent.putExtra("savegame", 0);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {
            Log.i("RTA","navShare");
            String shareSub = "ShareSub Soggetto del share";
            String shareBody = "ShareBody body corpo del share";
            Intent in = new Intent(Intent.ACTION_SEND);
            in.putExtra(Intent.EXTRA_SUBJECT,shareSub);
            in.putExtra(Intent.EXTRA_TEXT,shareBody);
            in.setType("text/plain");
            startActivity(Intent.createChooser(in,"Share using"));
        }
        else if(id == R.id.nav_relazione)
        {
            Log.i("RTA","naRelazione");
            Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        else if(id == R.id.nav_settings)
        {
            Log.i("RTA","navSettings");
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
