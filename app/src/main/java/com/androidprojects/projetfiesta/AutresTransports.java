package com.androidprojects.projetfiesta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidprojects.projetfiesta.demarrage.ActiviteNonLogue;

/**
 * Created by NTS on 02.09.2016.
 */
public class AutresTransports extends AppCompatActivity {

    private Toolbar toolbar;

    Button Btn_CFF;
    Button Btn_BusMM;
    Button Btn_BusSM;
    Button Btn_BusSS;
    Button Btn_Taxi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autres_transports_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Btn_CFF = (Button) findViewById(R.id.btn_cff);
        Btn_BusMM = (Button) findViewById(R.id.btn_bus_MM);
        Btn_BusSM = (Button) findViewById(R.id.btn_bus_SM);
        Btn_BusSS= (Button) findViewById(R.id.btn_bus_SS);
        Btn_Taxi = (Button) findViewById(R.id.btn_taxi);

        Btn_CFF.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Toast.makeText(AutresTransports.this,
                        "Redirection sur le site des CFF", Toast.LENGTH_SHORT).show();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cff.ch"));
                startActivity(browserIntent);
 }
 });

        Btn_BusMM.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Toast.makeText(AutresTransports.this,
                        "Redirection sur le site postauto.ch...", Toast.LENGTH_SHORT).show();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.postauto.ch/fr/Informations-de-voyage/Bus-de-nuit/Monthey%E2%80%93Martigny-VS"));
                startActivity(browserIntent);
            }
        });

        Btn_BusSM.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Toast.makeText(AutresTransports.this,
                        "Redirection sur le site postauto.ch...", Toast.LENGTH_SHORT).show();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.postauto.ch/fr/Informations-de-voyage/Bus-de-nuit/Sion%E2%80%93Martigny-VS"));
                startActivity(browserIntent);
            }
        });

        Btn_BusSS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Toast.makeText(AutresTransports.this,
                        "Redirection sur le site verreluisant.ch...", Toast.LENGTH_SHORT).show();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.verreluisant.ch/horaires.php"));
                startActivity(browserIntent);
            }
        });

        Btn_Taxi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Toast.makeText(AutresTransports.this,
                        "Redirection sur le site taxivalais.ch...", Toast.LENGTH_SHORT).show();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.taxivalais.ch/"));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        toolbar.setTitle(R.string.title);
        toolbar.setTitleTextColor(Color.WHITE);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        if(res_id == R.id.action_info) {
            Intent intent = new Intent(this, Apropos.class);
            startActivity(intent);
        }

        if(res_id == R.id.action_logout) {

            SharedPreferences settings = getSharedPreferences("prefs",0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("estLogue",false);
            editor.putLong("idUtilisateur", 0);
            editor.commit();
            Intent i = new Intent(this, ActiviteNonLogue.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
