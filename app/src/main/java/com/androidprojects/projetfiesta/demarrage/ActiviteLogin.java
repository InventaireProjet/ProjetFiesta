package com.androidprojects.projetfiesta.demarrage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.androidprojects.projetfiesta.AfficherEvenements;
import com.androidprojects.projetfiesta.AfficherTrajet;
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

    private EditText etEmail;
    private EditText etMDP;
    private Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                        Toast.makeText(this, "Vous allez être connecté.", Toast.LENGTH_LONG).show();
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

        Toast.makeText(this, "Essayez à nouveau.", Toast.LENGTH_LONG).show();
        return false;
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
}
