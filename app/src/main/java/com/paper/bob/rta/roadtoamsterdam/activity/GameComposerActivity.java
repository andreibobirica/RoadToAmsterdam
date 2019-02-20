package com.paper.bob.rta.roadtoamsterdam.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.paper.bob.rta.roadtoamsterdam.engineGame.EnvironmentContainer;

public class GameComposerActivity extends AppCompatActivity {

    private EnvironmentContainer contPrincipale;
    private boolean checkVideo, checkDialogo, checkPlatform = false;
    private EnvironmentContainer cont2;
    private EnvironmentContainer cont3;
    private EnvironmentContainer cont4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cont2 = new EnvironmentContainer("dam420",null,"benzinaio",null,null,null,null);
        contPrincipale = new EnvironmentContainer("dam420",null,"benzi",null,cont2,null,null);
        cont2.setContPrec(contPrincipale);
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
        if(contPrincipale.getDialogo() != null && !checkDialogo)
        {
            checkDialogo=true;
            Intent i = new Intent(this, DialogoActivity.class);
            i.putExtra("dialogo", contPrincipale.getDialogo());
            startActivity(i);
        }
        else if(!checkVideo)
        {
            checkVideo=true;
            Intent i = new Intent(this, VideoActivity.class);
            i.putExtra("video", contPrincipale.getVideo());
            startActivity(i);
        }
        else if (!checkPlatform)
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
            if(contPrincipale.getScelta() != null && contPrincipale.getContA() != null && contPrincipale.getScelta())
            {
                contPrincipale = new EnvironmentContainer(contPrincipale.getContA());
                checkPlatform = checkDialogo = checkVideo = false;
                startGame();
            }
            else if (contPrincipale.getScelta() != null && contPrincipale.getContA() != null  && !contPrincipale.getScelta())
            {
                contPrincipale = new EnvironmentContainer(contPrincipale.getContB());
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
