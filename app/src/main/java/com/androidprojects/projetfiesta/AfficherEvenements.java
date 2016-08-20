package com.androidprojects.projetfiesta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;
import com.projetfiesta.backend.trajetApi.model.Trajet;
import com.projetfiesta.backend.utilisateurApi.model.Utilisateur;

import java.util.List;

public class AfficherEvenements extends AppCompatActivity implements OnTaskCompleted {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evenements_afficher);
        getEvenements();
    }





    public void getEvenements() {
        new EndpointsAsyncTaskEvenement(this).execute();
    }



    @Override
    public void updateListViewEvenement(final List<Evenement> evenements) {
        //final ListView listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, evenements) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(evenements.get(position).getTitre());
                text2.setText(evenements.get(position).getDate());

                text1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Intent intent = new Intent(getBaseContext(), AfficherTrajets.class);
                        //Long evenementId = evenements.get(position).getId();
                        //intent.putExtra("evenementId",evenementId);
                        //startActivity(intent);
                    }
                });
                return view;
            }
        };

        //listView.setAdapter(adapter);

        final Context context = this;
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
