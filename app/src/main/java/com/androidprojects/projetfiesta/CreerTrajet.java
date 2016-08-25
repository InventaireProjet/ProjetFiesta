package com.androidprojects.projetfiesta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;
import com.projetfiesta.backend.trajetApi.model.Trajet;
import com.projetfiesta.backend.utilisateurApi.model.Utilisateur;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CreerTrajet extends AppCompatActivity implements OnTaskCompleted {



    private EditText nom;
    private EditText email;
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
        Intent intent = getIntent();
        evenementId= intent.getLongExtra("evenementId", 1);


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

        nom = (EditText) findViewById(R.id.nomEntree);
        destination = (EditText) findViewById(R.id.destinationEntree);
        nombrePlaces = (EditText) findViewById(R.id.nbPlacesEntree);
        heureDepart = (EditText) findViewById(R.id.heureEntree);
        email = (EditText) findViewById(R.id.emailEntree);

        //Test que les champs contiennent des valeurs
        if ((nom == null) || nom.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), R.string.nom_vide, Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            utilisateur.setNom(nom.getText().toString());
        }

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

        //Test de validité de l'email repris de : http://stackoverflow.com/questions/12947620/email-address-validation-in-android-on-edittext
        emailteste = email.getText().toString();

        if (emailteste.matches(emailPattern))
        {
            utilisateur.setEmail(emailteste);
        }
        else
        {
            Toast.makeText(getApplicationContext(), R.string.email_invalide, Toast.LENGTH_SHORT).show();
            return;
        }



//Test si le nom d'utilisateur existe déjà afin d'éviter des doublons
        try {
            List <Utilisateur> utilisateurs =  new EndpointsAsyncTaskUtilisateur(this).execute().get();
            if (utilisateurs!=null)
            {
                for (Utilisateur uti : utilisateurs) {
                    if (uti.getNom().equals(utilisateur.getNom())) {
                        Toast.makeText(this, R.string.nom_existant, Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
            new EndpointsAsyncTaskUtilisateur(0, utilisateur, this).execute();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//Récupération de l'id de l'utilisateur
        try {
            List <Utilisateur> utilisateurs =  new EndpointsAsyncTaskUtilisateur(this).execute().get();
            for (Utilisateur uti:utilisateurs)
            {
                if (uti.getNom().equals(utilisateur.getNom()))
                {
                    conducteurId=uti.getId();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//Clés étrangères
        trajet.setEvenementId(evenementId);
        trajet.setConducteurId(conducteurId);

        new EndpointsAsyncTaskTrajet(0, trajet, this).execute();
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
