package com.androidprojects.projetfiesta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.projetfiesta.backend.trajetApi.model.Trajet;

import java.util.List;

public class CreerTrajet extends AppCompatActivity implements OnTaskCompleted {

    private EditText nomConducteur;
    private EditText destination;
    private EditText nombrePlaces;
    private EditText heureDepart;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trajet_creer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void insert(View view) {

        Trajet trajet = new Trajet();

          nomConducteur = (EditText) findViewById(R.id.editText);
          destination = (EditText) findViewById(R.id.editText1);
          nombrePlaces = (EditText) findViewById(R.id.editText2);
          heureDepart = (EditText) findViewById(R.id.editText3);
          email = (EditText) findViewById(R.id.editText4);

        trajet.setNomConducteur(nomConducteur.getText().toString());
        trajet.setDestination(destination.getText().toString());
        trajet.setNombrePlaces(Integer.valueOf(nombrePlaces.getText().toString()));
        trajet.setHeureDepart(heureDepart.getText().toString());
        trajet.setEmail(email.getText().toString());



        new EndpointsAsyncTask(trajet, this).execute();
    }

    @Override
    public void updateListView(List<Trajet> trajets) {
        Toast.makeText(this, "Trajet successfully inserted", Toast.LENGTH_LONG).show();
    }

}
