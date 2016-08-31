package com.androidprojects.projetfiesta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;
import com.projetfiesta.backend.trajetApi.model.Trajet;
import com.projetfiesta.backend.utilisateurApi.model.Utilisateur;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AfficherTrajets extends AppCompatActivity implements OnTaskCompleted{

   ListView trajetsListView;

    // Evemement
    private Evenement evenement;
    private TextView tvDateEvenement;
    private TextView tvNomEvenement;
    private TextView tvEtatTrajets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trajets_afficher);

        // Récupération des trajets concernant un événement (celui cliqué auparavant)
        Intent intent = getIntent();
        final Long evenementId = intent.getLongExtra("evenementId", 1);

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
        String Etat = "Actuellement "+nbChauffeurs+" conducteurs sont inscrits et "+nbPlaces+" places sont disponibles pour rentrer de "+evenement.getTitre()+". "+"<b>"+"Prenez contact pour la destination qui vous convient!"+"</b>";
        tvEtatTrajets.setText(Html.fromHtml(Etat));

        //for (int i=0; i<trajets;)

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
    }


    //Affichage des trajets dans une listView à 'aide d'un ArrayAdapter personnalisé
    @Override
    public void updateListViewTrajet(final List<Trajet> trajets) {

        ArrayAdapter adapter = new TrajetsAdapter(this, trajets);
        trajetsListView.setAdapter(adapter);

        final Context context = this;
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
