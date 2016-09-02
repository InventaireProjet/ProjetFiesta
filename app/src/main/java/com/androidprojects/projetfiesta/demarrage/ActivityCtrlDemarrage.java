package com.androidprojects.projetfiesta.demarrage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.androidprojects.projetfiesta.AfficherEvenements;
import com.androidprojects.projetfiesta.R;
import android.os.Bundle;

public class ActivityCtrlDemarrage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctrl_demarrage);




        SharedPreferences settings = getSharedPreferences("prefs",0);
        boolean estLogue = settings.getBoolean("estLogue",false);

        if(estLogue == true)
        {
            Intent i = new Intent(ActivityCtrlDemarrage.this, AfficherEvenements.class);
            startActivity(i);
            finish();
        }
        else
        {
            Intent a = new Intent(ActivityCtrlDemarrage.this, ActiviteNonLogue.class);
            startActivity(a);
            finish();
        }


    }
}