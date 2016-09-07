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
import android.widget.CheckBox;
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

public class CreerTrajet extends AppCompatActivity implements OnTaskCompleted {


    private Toolbar toolbar;

    private Evenement evenement;
    private TextView tvDateEvenement;
    private TextView tvNomEvenement;
    private EditText destination;
    private EditText nombrePlaces;
    private EditText heureDepart;
    private Long conducteurId;
    private Long evenementId;
    //http://www.mkyong.com/regular-expressions/how-to-validate-time-in-24-hours-format-with-regular-expression/
    private final String heurePattern = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    private String heuretestee;
    //http://www.w3schools.com/jsref/prop_email_pattern.asp
    private final String emailPattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$";
    private String emailteste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trajet_creer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Permet de ne pas démarrer le keyboard automatiquement au lancement de l'activité
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent intent = getIntent();
        evenementId= intent.getLongExtra("evenementId", 1);

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

        tvDateEvenement = (TextView) findViewById(R.id.tvDateEvenement);
        tvNomEvenement = (TextView) findViewById(R.id.tvNomEvenement);
        tvDateEvenement.setText(evenement.getDate());
        tvNomEvenement.setText(evenement.getTitre());




    }

    public void insert(View view) {

        //Vérification que le conducteur s'est bien engagé à rester sobre et responsable
        CheckBox responsable = (CheckBox) findViewById(R.id.checkbox_sobre);
        if (!responsable.isChecked()) {
            Toast.makeText(this, R.string.sobriete_obligatoire, Toast.LENGTH_LONG).show();
            return;
        }

        //Récupération des données de la vue
        Trajet trajet = new Trajet();
        Utilisateur utilisateur = new Utilisateur();


        destination = (EditText) findViewById(R.id.destinationEntree);
        nombrePlaces = (EditText) findViewById(R.id.nbPlacesEntree);
        heureDepart = (EditText) findViewById(R.id.heureEntree);


        if (destination==null || destination.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), R.string.destination_vide, Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            trajet.setDestination(destination.getText().toString());
        }

        if (nombrePlaces == null || nombrePlaces.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), R.string.nbPlaces_vide, Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            trajet.setNombrePlaces(Integer.valueOf(nombrePlaces.getText().toString()));
        }

        trajet.setHeureDepart(heureDepart.getText().toString());


        //Test de validité de l'heure
        heuretestee = heureDepart.getText().toString();

        if (heuretestee.matches(heurePattern))
        {
            utilisateur.setEmail(heuretestee);
        }
        else
        {
            Toast.makeText(getApplicationContext(), R.string.heure_invalide, Toast.LENGTH_SHORT).show();
            return;
        }

        //Récupération de l'id de l'utilisateur
        SharedPreferences settings = getSharedPreferences("prefs",0);
        Long idConducteur = settings.getLong("idUtilisateur",0);

        //Clés étrangères
        trajet.setEvenementId(evenementId);
        trajet.setConducteurId(idConducteur);

        new EndpointsAsyncTaskTrajet(0, trajet, this).execute();

        Intent intent = new Intent(this, AfficherTrajets.class);
        intent.putExtra("evenementId",evenementId);
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
