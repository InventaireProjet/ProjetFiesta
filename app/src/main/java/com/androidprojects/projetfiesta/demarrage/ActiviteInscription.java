package com.androidprojects.projetfiesta.demarrage;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.androidprojects.projetfiesta.Apropos;
import com.androidprojects.projetfiesta.EndpointsAsyncTaskUtilisateur;
import com.androidprojects.projetfiesta.OnTaskCompleted;
import com.androidprojects.projetfiesta.R;
import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;
import com.projetfiesta.backend.trajetApi.model.Trajet;
import com.projetfiesta.backend.utilisateurApi.model.Utilisateur;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActiviteInscription extends AppCompatActivity implements OnTaskCompleted {

    private Toolbar toolbar;
    private EditText etNom;
    private EditText etPrenom;
    private EditText etDateNaissance;
    private EditText etEmail;
    private EditText etMDP;
    private String dateTravail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Permet de ne pas démarrer le keyboard automatiquement au lancement de l'activité
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etNom = (EditText) findViewById(R.id.etNom);
        etPrenom = (EditText) findViewById(R.id.etPrenom);
        etDateNaissance  = (EditText) findViewById(R.id.etDateNaissance);
        etEmail  = (EditText) findViewById(R.id.etEmail);
        etMDP = (EditText) findViewById(R.id.etMDP);


        // http://stackoverflow.com/questions/16889502/how-to-mask-an-edittext-to-show-the-dd-mm-yyyy-date-format
        // contrôle d ela validté de la date de naissance
        TextWatcher tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    etDateNaissance.setText(current);
                    etDateNaissance.setSelection(sel < current.length() ? sel : current.length());
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        };

        etDateNaissance.addTextChangedListener(tw);
    }

    public void mEnregistre(View view) {

        boolean verificationChamps = controleChamps();

        if(!verificationChamps)
            return;

        boolean etatErengistrement = enregistrementServeur();

        if(etatErengistrement){
            Intent intent = new Intent(this, ActiviteLogin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent = new Intent(this, ActiviteInscription.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    private boolean controleChamps() {

        // contrôle que tous les champs soient remplis
        if(etNom.getText().toString().equals("") || etPrenom.getText().toString().equals("") || etDateNaissance.getText().toString().equals("") ||
                etEmail.getText().toString().equals("") || etMDP.getText().toString().equals("")) {
            Toast.makeText(this, R.string.toastChampsIncomplet , Toast.LENGTH_SHORT).show();
            return false;
        }

        // http://stackoverflow.com/questions/6119722/how-to-check-edittexts-text-is-email-address-or-not
        // contrôle de la validité de l'e-mail
        boolean verificationMail = emailValide(etEmail.getText().toString());
        if(!verificationMail) {
            Toast.makeText(this, R.string.toastEmailInvalide , Toast.LENGTH_SHORT).show();
            return false;
        }

        // contrôle longueur du mot de passe
        if(etMDP.length() < 8){
            Toast.makeText(this, R.string.toastMdpCourt, Toast.LENGTH_SHORT).show();
            return false;
        }


        // corriger la date de naissance au format ##.##.#### pour envoyer vers le serveur.
        dateTravail = etDateNaissance.getText().toString().replaceAll("/", ".");

        // corriger la date de naissance au format ##.##.####
        String dateEnCours = etDateNaissance.getText().toString().replaceAll("/", "");
        boolean verificationNbre = dateEnCours.matches("[0-9]+");
        if(!verificationNbre) {
            Toast.makeText(this, R.string.toastDateNum , Toast.LENGTH_SHORT).show();
            return false;
        }


        Toast.makeText(this, R.string.toastInscriptionOk , Toast.LENGTH_SHORT).show();
        // si tout est ok on retourne TRUE.
        return true;

    }


    private boolean enregistrementServeur() {

        Utilisateur utilisateurNew = new Utilisateur();
        utilisateurNew.setNom(etNom.getText().toString());
        utilisateurNew.setPrenom(etPrenom.getText().toString());
        utilisateurNew.setDateNaissance(dateTravail);
        utilisateurNew.setEmail(etEmail.getText().toString());
        utilisateurNew.setMotDePasse(etMDP.getText().toString());

        //Test si le nom d'utilisateur existe déjà afin d'éviter des doublons
        try {
            List<Utilisateur> utilisateurs =  new EndpointsAsyncTaskUtilisateur(this).execute().get();
            if (utilisateurs!=null)
            {
                for (Utilisateur uti : utilisateurs) {
                    if (uti.getEmail().equals(etEmail.getText().toString())) {
                        Toast.makeText(this, R.string.nom_existant, Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }
            new EndpointsAsyncTaskUtilisateur(0, utilisateurNew, this).execute();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return true;
    }


    public static boolean emailValide(String email) {
        boolean estValide = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            estValide = true;
        }
        return estValide;
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
