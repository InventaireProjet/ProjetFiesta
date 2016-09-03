package com.androidprojects.projetfiesta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;
import com.projetfiesta.backend.trajetApi.model.Trajet;
import com.projetfiesta.backend.utilisateurApi.model.Utilisateur;

import java.util.List;

public class AfficherEvenements extends AppCompatActivity implements OnTaskCompleted {

    ListView eventListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evenements_liste_afficher);

        //final String date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

        //TODO: voir si cela fonctionne ainsi, sous getEvenements() la méthode de base montrant tous les événements
        /*
        List<Evenement> evenements = new ArrayList<Evenement>();
        try {
            evenements = new EndpointsAsyncTaskEvenement(date,this).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        */
        //TODO: il s'agit de la méthode permettant d'afficher tous les événements, à retirer dès que le filtre par date est OK
        getEvenements();

        eventListView = (ListView) findViewById(R.id.listViewEvenements);
    }



    public void getEvenements() {
        new EndpointsAsyncTaskEvenement(this).execute();
    }



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
