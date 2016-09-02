package com.androidprojects.projetfiesta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;
import com.projetfiesta.backend.trajetApi.model.Trajet;
import com.projetfiesta.backend.utilisateurApi.model.Utilisateur;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Chat  extends AppCompatActivity implements OnTaskCompleted{

    // Evemement
    private Evenement evenement;
    private TextView tvDateEvenement;
    private TextView tvNomEvenement;

    // Trajet
    private Trajet trajet;
    private TextView tvDepart;
    private TextView tvNbPlace;
    private TextView tvDestination;


    // Conducteur
    private Utilisateur conducteur;
    private TextView tvNomConducteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);


        //Récupération du trajet transmis par la vue précédente
        Intent intent = getIntent();
        final Long trajetId = intent.getLongExtra("trajetId", 1);
        List <Trajet> trajets = new ArrayList<Trajet>();
        try {
            trajets = new EndpointsAsyncTaskTrajet(this, trajetId ).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        trajet = trajets.get(0);


        tvDepart = (TextView) findViewById(R.id.tvDepart);
        tvNbPlace = (TextView) findViewById(R.id.tvNbPlace);
        tvDestination = (TextView) findViewById(R.id.tvDestination);
        tvDepart.setText(trajet.getHeureDepart());

        if (trajet.getNombrePlaces()<2){
            tvNbPlace.setText(trajet.getNombrePlaces() + getString(R.string.place));
        }
        else {
            tvNbPlace.setText(trajet.getNombrePlaces() + getString(R.string.places));
        }

        tvDestination.setText(trajet.getDestination());

        //Récupération de l'événement concerné

        List <Evenement> evenements = new ArrayList <Evenement>();
        try {
            evenements = new EndpointsAsyncTaskEvenement(trajet.getEvenementId(), this ).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        evenement = evenements.get(0);

        tvDateEvenement = (TextView) findViewById(R.id.tvDateEvenement);
        tvNomEvenement = (TextView) findViewById(R.id.tvNomEvenement);
        tvDateEvenement.setText(evenement.getDate());
        tvNomEvenement.setText(evenement.getTitre());


        //Récupération du conducteur concerné

        List <Utilisateur> conducteurs = new ArrayList <Utilisateur>();
        try {
            conducteurs = new EndpointsAsyncTaskUtilisateur(trajet.getConducteurId(), this ).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        conducteur = conducteurs.get(0);

        tvNomConducteur = (TextView) findViewById(R.id.tvNomConducteur);
        tvNomConducteur.setText(conducteur.getNom());

    }


    @Override
    public void updateListViewTrajet(List<Trajet> trajets) {

    }

    @Override
    public void updateListViewUtilisateur(List<Utilisateur> utilisateurs) {

    }

    @Override
    public void updateListViewEvenement(List<Evenement> evenements) {

    }

    @Override
    public void updateListViewMessage(List<Message> messages) {

    }
}
