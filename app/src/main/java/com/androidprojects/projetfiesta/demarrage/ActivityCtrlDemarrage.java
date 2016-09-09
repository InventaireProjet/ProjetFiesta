package com.androidprojects.projetfiesta.demarrage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;

import com.androidprojects.projetfiesta.AfficherEvenements;
import com.androidprojects.projetfiesta.Apropos;
import com.androidprojects.projetfiesta.R;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ActivityCtrlDemarrage extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctrl_demarrage);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Recupère les préfèrences de l'utilisateur...
        SharedPreferences settings = getSharedPreferences("prefs",0);
        // ... récupère l'état --> si l'utilisateur est déjà logué
        boolean estLogue = settings.getBoolean("estLogue",false);

        // si l'utilisateur est déjà logué à l'application...
        if(estLogue == true)
        {
            // ... il est directement envoyé vers l'affichage des événements
            Intent i = new Intent(ActivityCtrlDemarrage.this, AfficherEvenements.class);
            startActivity(i);
            finish();
        }
        // si l'utilisateur n'est pas connecté ...
        else
        {
            // ... il l'envoi vers la page de démarrage des utilisateurs non-connectés
            Intent a = new Intent(ActivityCtrlDemarrage.this, ActiviteNonLogue.class);
            startActivity(a);
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        toolbar.setTitle("Co-voiturage Fiesta");
        toolbar.setTitleTextColor(Color.WHITE);

        MenuItem logout = menu.findItem(R.id.action_logout);
        logout.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        if(res_id == R.id.action_info) {
            Intent intent = new Intent(this, Apropos.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}