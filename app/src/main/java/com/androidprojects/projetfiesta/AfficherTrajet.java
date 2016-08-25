package com.androidprojects.projetfiesta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;
import com.projetfiesta.backend.trajetApi.model.Trajet;
import com.projetfiesta.backend.utilisateurApi.model.Utilisateur;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AfficherTrajet extends AppCompatActivity implements OnTaskCompleted {


    // Evemement
    private Evenement evenement;
    private TextView tvDateEvenement;
    private TextView tvNomEvenement;

    // Conducteur
    private Utilisateur conducteur;
    private TextView contacterConducteur;
    private TextView tvNomConducteur;

    // Trajet
    private Trajet trajet;
    private TextView tvDepart;
    private TextView tvNbPlace;
    private TextView tvDestination;

    // Utilisateur
    private Utilisateur utilisateur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trajet_afficher);

        // Permet de ne pas démarrer le keyboard automatiquement au lancement de l'activité
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        /*
        // Récupération des données de la vue.
        evenement = new Evenement();
        trajet = new Trajet();
        utilisateur = new Utilisateur();

        tvDateEvenement = (TextView) findViewById(R.id.tvDateEvenement);
        tvNomEvenement = (TextView) findViewById(R.id.tvNomEvenement);


        try {
            List<Evenement> events = new EndpointsAsyncTaskEvenement(this).execute().get();


            new EndpointsAsyncTaskEvenement(0, evenement, this).execute();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        */

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
