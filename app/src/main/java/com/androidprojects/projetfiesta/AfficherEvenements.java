package com.androidprojects.projetfiesta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;
import com.projetfiesta.backend.trajetApi.model.Trajet;
import com.projetfiesta.backend.utilisateurApi.model.Utilisateur;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AfficherEvenements extends AppCompatActivity implements OnTaskCompleted {

    ListView eventListView;
    String dateDuJour = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
    int dateInt = Integer.parseInt(dateDuJour);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evenements_liste_afficher);

        getEvenements();

        eventListView = (ListView) findViewById(R.id.listViewEvenements);
    }

    // méthode permettant d'appeler tous les événements en entrant la date du jour
    public void getEvenements() {
        new EndpointsAsyncTaskEvenement(dateInt, this).execute();
    }

    //Affichage des événements dans une listView à 'aide d'un ArrayAdapter personnalisé
     @Override
     public void updateListViewEvenement(final List<Evenement> evenements) {

         ArrayAdapter adapter = new EvenementAdapter(this, evenements);
         eventListView.setAdapter(adapter);
     }

    @Override
    public void updateListViewTrajet(List<Trajet> trajets) {
    }

    @Override
    public void updateListViewUtilisateur(List<Utilisateur> utilisateurs) {
    }

    @Override
    public void updateListViewMessage(List<Message> messages) {

    }
}