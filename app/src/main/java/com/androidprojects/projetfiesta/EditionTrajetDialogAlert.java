package com.androidprojects.projetfiesta;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;
import com.projetfiesta.backend.trajetApi.model.Trajet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by rilcy on 07.09.16.
 */
public class EditionTrajetDialogAlert extends DialogFragment implements OnTaskCompleted {

    private Long trajetId;
    private Activity activity;
    private Trajet trajet;

    private EditText nombrePlaces;
    private EditText heureDepart;
    private LayoutInflater inflater;


    // Variable Pattern pour contrôler le format de l'heure entré par l'utilisateur
    private final String heurePattern = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        trajetId = getArguments().getLong("trajetId");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activityalert_editiontrajet, null))



                .setPositiveButton(R.string.editer, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Correction du trajet
                        // Récupération du trajet
                        List<Trajet> trajets = new ArrayList<Trajet>();
                        try {
                            trajets = new EndpointsAsyncTaskTrajet(EditionTrajetDialogAlert.this, trajetId).execute().get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        trajet = trajets.get(0);

                        View content =  inflater.inflate(R.layout.activityalert_editiontrajet, null);

                        nombrePlaces = (EditText) getDialog().findViewById(R.id.nbPlacesEntree);
                        heureDepart = (EditText) getDialog().findViewById(R.id.heureEntree);

                        update(content);
                        refreshMyActivity();

                    }
                })
                .setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditionTrajetDialogAlert.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    private void refreshMyActivity() {
        getActivity().finish();
        getActivity().overridePendingTransition(0, 0);
        startActivity(getActivity().getIntent());
        getActivity().overridePendingTransition(0, 0);
    }



    // Modification de l'heure de départ pour un trajet donné
    public void update (View v){

        if(nombrePlaces.length()>0) {
            int nb = Integer.parseInt(nombrePlaces.getText().toString());
            if (nb >= 0 && nb <= 20) {
                trajet.setNombrePlaces(nb);
                new EndpointsAsyncTaskTrajet(1, trajet, this).execute();
            }
            else {
                Toast.makeText(EditionTrajetDialogAlert.this.getActivity(), R.string.minMaxPlace, Toast.LENGTH_SHORT).show();
                return;
            }
        }


        if (heureDepart.getText().toString().matches(heurePattern))
        {
            trajet.setHeureDepart(heureDepart.getText().toString());

            new EndpointsAsyncTaskTrajet(1, trajet, this).execute();

        }
        else
        {
            if (heureDepart.getText().toString().equals("")) {
            }
            else {
                Toast.makeText(EditionTrajetDialogAlert.this.getActivity(), R.string.heure_invalide, Toast.LENGTH_SHORT).show();
                return;
            }
        }
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
}
