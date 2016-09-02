package com.androidprojects.projetfiesta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Nini on 02.09.2016.
 */
public class AutresTransports extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.autres_transports_layout);
/**
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cff.ch"));
        startActivity(browserIntent);**/
    }
}
