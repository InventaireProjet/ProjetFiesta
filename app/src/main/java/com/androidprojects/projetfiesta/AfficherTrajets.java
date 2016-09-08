package com.androidprojects.projetfiesta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.androidprojects.projetfiesta.demarrage.ActiviteNonLogue;
import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;
import com.projetfiesta.backend.trajetApi.model.Trajet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AfficherTrajets extends AppCompatActivity implements OnTaskCompleted{


    private Toolbar toolbar;

    private ListView trajetsListView;

    // Evemement
    private Evenement evenement;
    private Long evenementId;
    private TextView tvDateEvenement;
    private TextView tvNomEvenement;
    private TextView tvEtatTrajets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trajets_afficher);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Bouton pour s'annoncer comme chauffeur
        Button inscription;
        inscription = (Button) findViewById(R.id.button);

        //Action lors du clic sur le bouton, l'id de l'événement est passé à la classe CreerTrajet
        inscription.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick (View v){
                Intent intent = new Intent(getBaseContext(), CreerTrajet.class);
                intent.putExtra("evenementId",evenementId);
                startActivity(intent);

            }
        });

        //Bouton pour consulter les autres moyens de transport
        Button btn_autre;
        btn_autre = (Button) findViewById(R.id.buttonAT);

        btn_autre.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick (View v){
                Intent intent = new Intent(getBaseContext(), AutresTransports.class);
                startActivity(intent);

            }
        });
    }


    //Affichage des trajets dans une listView à 'aide d'un ArrayAdapter personnalisé
    @Override
    public void updateListViewTrajet(final List<Trajet> trajets) {

        ArrayAdapter adapter = new TrajetsAdapter(this, trajets);
        trajetsListView.setAdapter(adapter);


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

    //Mise à jour des données lors de l'accès à cette vue
    @Override
    protected void onResume() {
        super.onResume();
        // Récupération des trajets concernant un événement (celui cliqué auparavant)
        Intent intent = getIntent();
        evenementId = intent.getLongExtra("evenementId", 1);

        List <Trajet> trajets = new ArrayList<Trajet>();
        try {
            trajets = new EndpointsAsyncTaskTrajet(evenementId, this ).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //new EndpointsAsyncTaskTrajet(evenementId, this).execute();
        trajetsListView = (ListView) findViewById(R.id.listViewTrajets);



        //Récupération de l'événement concerné
        List <Evenement> evenements = new ArrayList<Evenement>();
        try {
            evenements = new EndpointsAsyncTaskEvenement(evenementId, this ).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        evenement = evenements.get(0);

        // Affiche l'événement concerné
        tvDateEvenement = (TextView) findViewById(R.id.tvDateEvenement);
        tvNomEvenement = (TextView) findViewById(R.id.tvNomEvenement);
        tvDateEvenement.setText(evenement.getDate());
        tvNomEvenement.setText(evenement.getTitre());

        // Affiche le nombre de chauffeurs et le nombre de places disponibles
        int nbChauffeurs=0;

        if (trajets!=null)
            nbChauffeurs=trajets.size();

        int nbPlaces=0;

        if (trajets!=null) {
            for (int i = 0; i < trajets.size(); i++) {
                nbPlaces += trajets.get(i).getNombrePlaces();
            }
        }

        tvEtatTrajets = (TextView) findViewById(R.id.etatTrajets);

        String Etat = null;


        switch(nbChauffeurs)
        {
            case 0:  Etat = String.format(getString(R.string.etatTrajets0)+"<b>"+getString(R.string.etatTrajets0B)+"</b>", evenement.getTitre());
                break;
            case 1: if (nbPlaces==1){
                Etat = String.format("Actuellement %d conducteur est inscrit et %d place est disponible pour rentrer de %s. <b>Prenez contact si la destination vous convient!</b>", nbChauffeurs, nbPlaces, evenement.getTitre());}
            else{
                Etat = String.format("Actuellement %d conducteur est inscrit et %d places sont disponibles pour rentrer de %s. <b>Prenez contact si la destination vous convient!</b>", nbChauffeurs, nbPlaces, evenement.getTitre());
            }
                break;
            default:  Etat = String.format("Actuellement %d conducteurs sont inscrits et %d places sont disponibles pour rentrer de %s. <b>Prenez contact pour la destination qui vous convient!</b>", nbChauffeurs, nbPlaces, evenement.getTitre());
                break;
        }


        tvEtatTrajets.setText(Html.fromHtml(Etat));
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
