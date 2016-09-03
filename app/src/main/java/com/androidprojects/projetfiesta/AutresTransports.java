package com.androidprojects.projetfiesta;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by Nini on 02.09.2016.
 */
public class AutresTransports extends AppCompatActivity {

    ImageButton ImgBtnCFF;
    ImageButton ImgBtnBusMM;
    ImageButton ImgBtnBusSM;
    ImageButton ImgBtnBusSS;
    ImageButton ImgBtnTaxi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.autres_transports_layout);

        ImgBtnCFF = (ImageButton) findViewById(R.id.ImgB_cff);
        ImgBtnBusMM = (ImageButton) findViewById(R.id.ImgB_mm);
        ImgBtnBusSM = (ImageButton) findViewById(R.id.ImgB_sm);
        ImgBtnBusSS= (ImageButton) findViewById(R.id.ImgB_ss);
        ImgBtnTaxi = (ImageButton) findViewById(R.id.ImgB_taxi);

        ImgBtnCFF.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Toast.makeText(AutresTransports.this,
                        "Redirection sur le site des CFF", Toast.LENGTH_SHORT).show();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cff.ch"));
                startActivity(browserIntent);
 }
 });

        ImgBtnBusMM.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Toast.makeText(AutresTransports.this,
                        "Redirection sur le site postauto.ch...", Toast.LENGTH_SHORT).show();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.postauto.ch/fr/Informations-de-voyage/Bus-de-nuit/Monthey%E2%80%93Martigny-VS"));
                startActivity(browserIntent);
            }
        });

        ImgBtnBusSM.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Toast.makeText(AutresTransports.this,
                        "Redirection sur le site postauto.ch...", Toast.LENGTH_SHORT).show();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.postauto.ch/fr/Informations-de-voyage/Bus-de-nuit/Sion%E2%80%93Martigny-VS"));
                startActivity(browserIntent);
            }
        });

        ImgBtnBusSS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Toast.makeText(AutresTransports.this,
                        "Redirection sur le site verreluisant.ch...", Toast.LENGTH_SHORT).show();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.verreluisant.ch/horaires.php"));
                startActivity(browserIntent);
            }
        });

        ImgBtnTaxi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Toast.makeText(AutresTransports.this,
                        "Redirection sur le site taxivalais.ch...", Toast.LENGTH_SHORT).show();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.taxivalais.ch/"));
                startActivity(browserIntent);
            }
        });
    }
}
