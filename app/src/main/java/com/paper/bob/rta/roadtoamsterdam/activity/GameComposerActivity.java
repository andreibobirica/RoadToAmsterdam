package com.paper.bob.rta.roadtoamsterdam.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.R;
import com.paper.bob.rta.roadtoamsterdam.engineGame.EnvironmentContainer;

import java.util.ArrayList;

public class GameComposerActivity extends AppCompatActivity {

    private EnvironmentContainer contPrincipale;
    private boolean checkVideo, checkDialogo, checkPlatform = false;
    ArrayList<EnvironmentContainer> conts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set Activity con layout platformgame visibile
        setContentView(R.layout.activity_game_composer);
        createEnviroments();
    }

    private void createEnviroments() {
        conts = new ArrayList<>();
        conts.add(new EnvironmentContainer("dam420",null,"benzinaio")); //0
        conts.add(new EnvironmentContainer(null,null,"naio"));          //1
        conts.add(new EnvironmentContainer("dam420",null,"benzi"));     //2
        conts.add(new EnvironmentContainer(null,null,"benzinaio"));     //3
        for(int i = 0; i < conts.size(); i++)
        {
            if(conts.get(i).getId() == 0)
            {conts.get(i).setNext(conts.get(1),conts.get(2));}
            if(conts.get(i).getId() == 1)
            {conts.get(i).setNext(conts.get(2),conts.get(3));}
            if(conts.get(i).getId() == 2)
            {conts.get(i).setNext(conts.get(3),conts.get(3));}
        }
        for(int i = 0; i < conts.size(); i++)
        {Log.i("RTA",conts.get(i).toString());}

        //Si parte dal primo Enviroment
        contPrincipale = conts.get(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startGame();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Boolean scelta = Boolean.valueOf(data.getStringExtra("scelta"));
                Log.i("RTA","Il valore scelto è: "+scelta);
            }
        }
    }

    public void startGame()
    {
        if(!checkVideo && contPrincipale.getVideo() != null)
        {
            checkVideo=true;
            Intent i = new Intent(this, VideoActivity.class);
            i.putExtra("video", contPrincipale.getVideo());
            startActivity(i);
        }
        else if(!checkDialogo && contPrincipale.getDialogo() != null)
        {
            checkDialogo=true;
            Intent i = new Intent(this, DialogoActivity.class);
            i.putExtra("dialogo", contPrincipale.getDialogo());
            startActivity(i);
        }
        else if (!checkPlatform && contPrincipale.getPlatform() != null)
        {
            checkPlatform=true;
            Intent i = new Intent(this, PlatformMainActivity.class);
            i.putExtra("platform", contPrincipale.getPlatform());
            startActivityForResult(i,1);
        }
        else
        {
            contPrincipale.setScelta(true);
            Log.i("RTA","Scelte e cambio di livello");
            if(contPrincipale.verifyScelta())
            {
                Log.i("RTA",contPrincipale.toString());
                contPrincipale = new EnvironmentContainer(contPrincipale.getNext());
                checkPlatform = checkDialogo = checkVideo = false;
                startGame();
            }
            else
            {
                Log.i("RTA","C'è un problema");
                finish();
            }
        }
    }
}
