package com.androidprojects.projetfiesta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;
import com.projetfiesta.backend.trajetApi.model.Trajet;
import com.projetfiesta.backend.utilisateurApi.model.Utilisateur;

import java.util.List;

public class AfficherTrajets extends AppCompatActivity implements OnTaskCompleted{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trajets_afficher);
        Intent intent = getIntent();
        final Long evenementId = intent.getLongExtra("evenementId", 1);
        new EndpointsAsyncTaskTrajet(evenementId, this).execute();
        Button inscription;


        //Bouton pour s'annoncer comme conducteur
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


    //Affichage des trajets dans une listView
    @Override
    public void updateListViewTrajet(final List<Trajet> trajets) {
        final ListView listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, trajets) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);


                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);


                text1.setText(trajets.get(position).getDestination());
                text2.setText(trajets.get(position).getNombrePlaces()+ " " +trajets.get(position).getHeureDepart());

                text1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), AfficherTrajet.class);
                        Long trajetId = trajets.get(position).getId();
                        intent.putExtra("trajetId",trajetId);
                        startActivity(intent);
                    }
                });
                return view;
            }
        };

        listView.setAdapter(adapter);

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
