package com.androidprojects.projetfiesta;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;
import com.projetfiesta.backend.trajetApi.model.Trajet;
import com.projetfiesta.backend.utilisateurApi.model.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public class AfficherEvenements extends AppCompatActivity implements OnTaskCompleted {

    ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evenements_liste_afficher);
        eventListView = (ListView) findViewById(R.id.listViewEvenements);

        List<EvenementTest> evenements = genererEvenements();

        EvenementAdapter adapter = new EvenementAdapter(AfficherEvenements.this, evenements);
        eventListView.setAdapter(adapter);
    }

    private List<EvenementTest> genererEvenements(){
        List<EvenementTest> evenements = new ArrayList<EvenementTest>();
        evenements.add(new EvenementTest(Color.BLACK, "Vinea", "02.09.2016"));
        evenements.add(new EvenementTest(Color.BLACK, "Vinea", "03.09.2016"));
        evenements.add(new EvenementTest(Color.BLUE, "Sillicon Valais", "16.09.2016"));
        evenements.add(new EvenementTest(Color.GREEN, "Foire du Valais", "30.09.2016"));
        evenements.add(new EvenementTest(Color.GREEN, "Foire du Valais", "01.10.2016"));
        evenements.add(new EvenementTest(Color.GREEN, "Foire du Valais", "02.10.2016"));
        evenements.add(new EvenementTest(Color.GREEN, "Foire du Valais", "03.10.2016"));
        evenements.add(new EvenementTest(Color.GREEN, "Foire du Valais", "04.10.2016"));
        evenements.add(new EvenementTest(Color.GREEN, "Foire du Valais", "05.10.2016"));
        evenements.add(new EvenementTest(Color.GREEN, "Foire du Valais", "06.10.2016"));
        evenements.add(new EvenementTest(Color.GREEN, "Foire du Valais", "07.10.2016"));
        evenements.add(new EvenementTest(Color.GREEN, "Foire du Valais", "08.10.2016"));
        evenements.add(new EvenementTest(Color.GREEN, "Foire du Valais", "09.10.2016"));

        return evenements;
    }


    @Override
    public void updateListViewTrajet(List<Trajet> trajets) {
        Toast.makeText(this, R.string.trajet_insere, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateListViewUtilisateur(List<Utilisateur> utilisateurs) {
        Toast.makeText(this, R.string.utilisateur_insere, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateListViewEvenement(List<Evenement> evenements) {

    }

    @Override
    public void updateListViewMessage(List<Message> messages) {

    }
}
