package com.androidprojects.projetfiesta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.projetfiesta.backend.utilisateurApi.model.Utilisateur;
import com.projetfiesta.backend.trajetApi.model.Trajet;
import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;

import java.util.List;

public class CreerTrajet extends AppCompatActivity implements OnTaskCompleted {

    //private EditText nomConducteur;
    private EditText nom;
    private EditText prenom;
    private EditText dateNaissance;
    private EditText email;
    private EditText motPasse;
    private EditText destination;
    private EditText nombrePlaces;
    private EditText heureDepart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trajet_creer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void insert(View view) {

        Utilisateur utilisateur = new Utilisateur();
        Trajet trajet = new Trajet();
        Evenement evenement = new Evenement();
        Message message = new Message();

        nom = (EditText) findViewById(R.id.nom);
        prenom = (EditText) findViewById(R.id.prenom);
        dateNaissance = (EditText) findViewById(R.id.dateNaissance);
        email = (EditText) findViewById(R.id.email);
        motPasse = (EditText) findViewById(R.id.motPasse);
        destination = (EditText) findViewById(R.id.destination);
        nombrePlaces = (EditText) findViewById(R.id.nbPlaces);
        heureDepart = (EditText) findViewById(R.id.heure);


        utilisateur.setNom(nom.getText().toString());
        utilisateur.setPrenom(prenom.getText().toString());
        utilisateur.setDateNaissance(dateNaissance.getText().toString());
        utilisateur.setEmail(email.getText().toString());
        utilisateur.setMotDePasse(motPasse.getText().toString());

        new EndpointsAsyncTaskUtilisateur(utilisateur, this).execute();

        trajet.setDestination(destination.getText().toString());
        trajet.setNombrePlaces(Integer.valueOf(nombrePlaces.getText().toString()));
        trajet.setHeureDepart(heureDepart.getText().toString());
        trajet.setDestination(destination.getText().toString());
        trajet.setNombrePlaces(Integer.valueOf(nombrePlaces.getText().toString()));
        trajet.setHeureDepart(heureDepart.getText().toString());
        trajet.setConducteurId(utilisateur.getId());

        evenement.setTitre("titre");
        evenement.setDate("aujourd'hui");
        evenement.setLogo("image");

        message.setTexte("mon texte");
        message.setDateHeure("now");


        new EndpointsAsyncTaskTrajet(trajet, this).execute();
        new EndpointsAsyncTaskEvenement(evenement, this).execute();
        new EndpointsAsyncTaskMessage(message, this).execute();
    }

    @Override
    public void updateListViewTrajet(List<Trajet> trajets) {
        Toast.makeText(this, "Trajet successfully inserted", Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateListViewUtilisateur(List<Utilisateur> utilisateurs) {
        Toast.makeText(this, "Utilisateur successfully inserted", Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateListViewEvenement(List<Evenement> evenements) {
        Toast.makeText(this, "Evenement successfully inserted", Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateListViewMessage(List<Message> messages) {
        Toast.makeText(this, "Message successfully inserted", Toast.LENGTH_LONG).show();
    }

}
