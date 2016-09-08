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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidprojects.projetfiesta.demarrage.ActiviteNonLogue;
import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;
import com.projetfiesta.backend.trajetApi.model.Trajet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AfficherEvenements extends AppCompatActivity implements OnTaskCompleted {

    private Toolbar toolbar;

    private ListView eventListView;
    private String dateDuJour = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
    private int dateInt = Integer.parseInt(dateDuJour);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evenements_liste_afficher);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

    //Mise à jour des données lors de l'accès à cette vue
    @Override
    protected void onResume() {
        super.onResume();
        getEvenements();

        eventListView = (ListView) findViewById(R.id.listViewEvenements);
    }
}