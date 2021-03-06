package com.androidprojects.projetfiesta.demarrage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.androidprojects.projetfiesta.AfficherEvenements;
import com.androidprojects.projetfiesta.Apropos;
import com.androidprojects.projetfiesta.EndpointsAsyncTaskUtilisateur;
import com.androidprojects.projetfiesta.OnTaskCompleted;
import com.androidprojects.projetfiesta.R;
import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;
import com.projetfiesta.backend.trajetApi.model.Trajet;
import com.projetfiesta.backend.utilisateurApi.model.Utilisateur;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ActiviteLogin extends AppCompatActivity implements OnTaskCompleted {

    private Toolbar toolbar;
    private EditText etEmail;
    private EditText etMDP;
    private Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Permet de ne pas démarrer le keyboard automatiquement au lancement de l'activité
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etEmail = (EditText) findViewById(R.id.etEmailLogin);
        etMDP = (EditText) findViewById(R.id.etMDPLogin);

    }

    public void sEnregistrer(View view) {
        Intent intent = new Intent(this, ActiviteInscription.class);
        startActivity(intent);
    }

    public void seConnecter(View view) {

        // Contrôle que les champs soient remplis
        if(etEmail.getText().toString().equals("") || etMDP.getText().toString().equals("")){
            return;
        }

        // Contrôle que l'utilisateur existe
        boolean verification = verficiationUtilisateur();
        if(!verification)
            return;


        SharedPreferences settings = getSharedPreferences("prefs",0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("estLogue",true);
        editor.putLong("idUtilisateur", utilisateur.getId());
        editor.commit();
        Intent i = new Intent(this, AfficherEvenements.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();

    }

    private boolean verficiationUtilisateur() {

        //Test si le nom d'utilisateur existe déjà afin d'éviter des doublons
        try {
            List<Utilisateur> utilisateurs =  new EndpointsAsyncTaskUtilisateur(this).execute().get();
            if (utilisateurs!=null)
            {
                for (Utilisateur uti : utilisateurs) {
                    if (uti.getEmail().equals(etEmail.getText().toString()) && uti.getMotDePasse().equals(etMDP.getText().toString())) {
                        Toast.makeText(this, R.string.toastConnexionOk, Toast.LENGTH_LONG).show();
                        utilisateur = uti;
                        return true;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, R.string.toastConnexionEchec, Toast.LENGTH_LONG).show();
        return false;
    }


    @Override
    public void updateListViewTrajet(List<Trajet> trajets) {

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
        toolbar.setTitle("Co-voiturage Fiesta");
        toolbar.setTitleTextColor(Color.WHITE);

        MenuItem logout = menu.findItem(R.id.action_logout);
        logout.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        if(res_id == R.id.action_info) {
            Intent intent = new Intent(this, Apropos.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
