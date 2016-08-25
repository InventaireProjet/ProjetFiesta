package com.androidprojects.projetfiesta.demarrage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.androidprojects.projetfiesta.AfficherTrajet;
import com.androidprojects.projetfiesta.R;
import android.os.Bundle;

public class ActivityCtrlDemarrage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctrl_demarrage);




        SharedPreferences settings = getSharedPreferences("prefs",0);
        boolean estLogue = settings.getBoolean("estLogue",false);

        if(estLogue==true)//if running for first time
        //Splash will load for first time
        {
            //SharedPreferences.Editor editor = settings.edit();
            //editor.putBoolean("estLogue",true);
            //editor.commit();
            Intent i = new Intent(ActivityCtrlDemarrage.this, AfficherTrajet.class);
            startActivity(i);
            finish();
        }
        else
        {
            //SharedPreferences.Editor editor = settings.edit();
            //editor.putBoolean("estLogue",false);
            //editor.commit();
            Intent a = new Intent(ActivityCtrlDemarrage.this, ActiviteNonLogue.class);
            startActivity(a);
            finish();
        }


    }
}
