package com.androidprojects.projetfiesta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;
import com.projetfiesta.backend.trajetApi.model.Trajet;
import com.projetfiesta.backend.utilisateurApi.model.Utilisateur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Chat  extends AppCompatActivity implements OnTaskCompleted{

    // Evemement
    private Evenement evenement;
    private TextView tvDateEvenement;
    private TextView tvNomEvenement;

    // Trajet
    private Trajet trajet;
    private Long trajetId;
    private TextView tvDepart;
    private TextView tvNbPlace;
    private TextView tvDestination;


    // Conducteur
    private Utilisateur conducteur;
    private TextView tvNomConducteur;

    private ListView listView;

    //Message
    private Message message;
    private List<Message> messages = new ArrayList<>();

    //Bouton envoyer
    private ImageButton envoyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        // Permet de ne pas démarrer le keyboard automatiquement au lancement de l'activité
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Récupération du trajet transmis par la vue précédente
        Intent intent = getIntent();
        trajetId = intent.getLongExtra("trajetId", 1);
        List <Trajet> trajets = new ArrayList<Trajet>();
        try {
            trajets = new EndpointsAsyncTaskTrajet(this, trajetId ).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        trajet = trajets.get(0);

        //Affichage des informations sur le trajet
        tvDepart = (TextView) findViewById(R.id.tvDepart);
        tvNbPlace = (TextView) findViewById(R.id.tvNbPlace);
        tvDestination = (TextView) findViewById(R.id.tvDestination);
        tvDepart.setText(getString(R.string.environ)+ trajet.getHeureDepart());

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
        tvNomConducteur.setText(conducteur.getPrenom()+" "+conducteur.getNom().charAt(0)+".");



        //Récupération des messages liés au trajet
        try {
            messages =new EndpointsAsyncTaskMessage(trajetId, this).execute().get();

            //Tri des messages par date si la liste de messages n'est pas vide
            if(messages!=null) {
                Collections.sort(messages, new MessageComparator());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        listView = (ListView) findViewById(R.id.listView);

        //Configuration de l'envoi du  message
        envoyer = (ImageButton) findViewById(R.id.btnEnvoyer);

    }

    public void insert (View v){
        EditText texte = (EditText) findViewById(R.id.etMessage);

        //Si le texte n'est pas vide, on attribue le texte au message
        if (texte==null || texte.getText().toString().trim().equals("")) {

            return;
        }
        else {
            message=new Message();
            message.setTexte(texte.getText().toString());
        }
        //Ajout de l'heure
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
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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

    //Affichage des messages dans une listView à l'aide d'un ArrayAdapter personnalisé
    @Override
    public void updateListViewMessage( List<Message> messages) {

        listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter adapter = new MessagesAdapter(this, messages);
        listView.setAdapter(adapter);

    }

    private class MessageComparator implements Comparator<Message> {
        public int compare(Message messageA, Message messageB) {
            return messageA.getDateHeure().compareTo(messageB.getDateHeure());
        }
    }
}
