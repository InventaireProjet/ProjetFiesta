package com.androidprojects.projetfiesta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidprojects.projetfiesta.demarrage.ActiviteNonLogue;
import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;
import com.projetfiesta.backend.trajetApi.model.Trajet;
import com.projetfiesta.backend.utilisateurApi.model.Utilisateur;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AfficherTrajet extends AppCompatActivity implements OnTaskCompleted {


    private Toolbar toolbar;

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
    private  Long trajetId;
    private TextView tvDepart;
    private TextView tvNbPlace;
    private TextView tvDestination;

    // Utilisateur
    private Utilisateur utilisateur;

    //Bouton envoyer
    private Button envoyer;

    //Message
    private Message message;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trajet_afficher);

        // Permet de ne pas démarrer le keyboard automatiquement au lancement de l'activité
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//Récupération du trajet transmis par la vue précédente
        Intent intent = getIntent();
        trajetId = intent.getLongExtra("trajetId", 1);
        List<Trajet> trajets = new ArrayList<Trajet>();
        try {
            trajets = new EndpointsAsyncTaskTrajet(this, trajetId).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        trajet = trajets.get(0);


        tvDepart = (TextView) findViewById(R.id.tvDepart);
        tvNbPlace = (TextView) findViewById(R.id.tvNbPlace);
        tvDestination = (TextView) findViewById(R.id.tvDestination);
        tvDepart.setText(getString(R.string.environ)+trajet.getHeureDepart());

        if (trajet.getNombrePlaces() < 2) {
            tvNbPlace.setText(trajet.getNombrePlaces() + getString(R.string.place));
        } else {
            tvNbPlace.setText(trajet.getNombrePlaces() + getString(R.string.places));
        }

        tvDestination.setText(trajet.getDestination());

        //Récupération de l'événement concerné

        List<Evenement> evenements = new ArrayList<Evenement>();
        try {
            evenements = new EndpointsAsyncTaskEvenement(trajet.getEvenementId(), this).execute().get();
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

        List<Utilisateur> conducteurs = new ArrayList<Utilisateur>();
        try {
            conducteurs = new EndpointsAsyncTaskUtilisateur(trajet.getConducteurId(), this).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        conducteur = conducteurs.get(0);

        contacterConducteur = (TextView) findViewById(R.id.contacterConducteur);
        tvNomConducteur = (TextView) findViewById(R.id.tvNomConducteur);

        contacterConducteur.setText(getString(R.string.contacter) + " " +conducteur.getPrenom().toUpperCase()+" "+conducteur.getNom().charAt(0)+".");
        tvNomConducteur.setText(conducteur.getPrenom()+" "+conducteur.getNom().charAt(0)+".");

        //Bouton d'envoi du  message
        envoyer = (Button) findViewById(R.id.btnEnvoyer);

    }


    public void insert (View v){
        EditText texte = (EditText) findViewById(R.id.etMessage);

        //Si le texte n'est pas vide, on attribue le texte au message
        if (texte==null || texte.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), R.string.destination_vide, Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            message=new Message();
            message.setTexte(texte.getText().toString());
        }
        //Ajout de l'heure à partir de timestamp
        Long dateMessage = System.currentTimeMillis();
        String dateMessageTexte = dateMessage.toString();
        message.setDateHeure(dateMessageTexte);
        message.setTrajetId(trajetId);

        //Récupération et ajout de l'id de l'utilisateur
        SharedPreferences settings = getSharedPreferences("prefs",0);
        Long idUtilisateur = settings.getLong("idUtilisateur",0);
        message.setUtilisateurId(idUtilisateur);

        //Envoi du message et fin de l'activité
        new EndpointsAsyncTaskMessage(0, message, this).execute();
        Intent intent = new Intent(getBaseContext(), Chat.class);
        intent.putExtra("trajetId",trajetId);
        startActivity(intent);
        finish();


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
