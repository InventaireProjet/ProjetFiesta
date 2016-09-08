package com.androidprojects.projetfiesta;


import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.projetfiesta.backend.evenementApi.EvenementApi;
import com.projetfiesta.backend.evenementApi.model.Evenement;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class EndpointsAsyncTaskEvenement extends AsyncTask<Void, Void, List<Evenement>> {
    private static final String TAG = EndpointsAsyncTaskEvenement.class.getName();
    private static EvenementApi evenementApi = null;
    private Evenement evenement;
    private OnTaskCompleted listener;
    private int codeAction;
    private int date;
    private Long evenementId;


    //AsyncTask dédiée à l'affichage de tous les événements
    EndpointsAsyncTaskEvenement(OnTaskCompleted listener) {
        this.listener = listener;
    }


    EndpointsAsyncTaskEvenement(int codeAction, Evenement evenement, OnTaskCompleted listener) {
        this.evenement = evenement;
        this.listener = listener;
        this.codeAction = codeAction;
    }

    //AsyncTask dédiée à l'affichage des événements présents et futurs
    EndpointsAsyncTaskEvenement(int date, OnTaskCompleted listener) {
        this.listener = listener;
        this.date = date;
        this.codeAction=3;
    }

    EndpointsAsyncTaskEvenement(Long evenementId, OnTaskCompleted listener) {
        this.evenementId = evenementId;
        this.listener = listener;
        this.codeAction = 4;
    }

    @Override
    protected List<Evenement> doInBackground(Void... params) {

        if (evenementApi == null) {
            // Only do this once
            EvenementApi.Builder builder = new EvenementApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    // if you deploy on the cloud backend, use your app name
                    // such as https://<your-app-id>.appspot.com
                    .setRootUrl("https://4-dot-projet-fiesta.appspot.com/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            evenementApi = builder.build();
        }

        try {
            switch (codeAction) {
                case (0): // INSERT
                    if (evenement != null) {
                        evenementApi.insert(evenement).execute();
                        Log.i(TAG, "insert evenement");
                    }
                    break;
                case (1): // UPDATE
                    if (evenement != null) {
                        evenementApi.update(evenement.getId(), evenement).execute();
                        Log.i(TAG, "update evenement");
                    }
                    break;
                case (2) : // DELETE
                    if (evenement != null) {
                        evenementApi.remove(evenement.getId()).execute();
                        Log.i(TAG, "remove evenement");
                    }
                    break;

                case (3):
                    List evenements = evenementApi.list().execute().getItems();

                    // permet de trier les événements dans l'ordre chronologique
                    Collections.sort(evenements, new CustomComparator());

                    List<Evenement> evenementsTous = evenements;
                    List<Evenement> evenementsRetour = new ArrayList<Evenement>();
                    for (int i=0; i<evenementsTous.size(); i++) {

                        String reformatedString = null;
                        int dateInt;

                        // Ci-dessous le format tel qu'enregistré puis le format que l'on souhaite pour comparer les dates
                        SimpleDateFormat oldFormat = new SimpleDateFormat("dd.MM.yyyy");
                        SimpleDateFormat newFormat = new SimpleDateFormat("yyyyMMdd");

                        try {
                            reformatedString = newFormat.format(oldFormat.parse(evenementsTous.get(i).getDate()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        dateInt = Integer.parseInt(reformatedString);

                        // Permet de filtrer les événements qu'on souhaite (J-1 et plus récents)
                        if (dateInt >= date-1) {
                            evenementsRetour.add(evenementsTous.get(i));
                        }

                    }

                    Log.i(TAG, "get evenements par date");

                    return evenementsRetour;

                case (4):

                    Evenement evenement = evenementApi.get(evenementId).execute();
                    List evenementUnique = new ArrayList();
                    evenementUnique.add(evenement);
                    Log.i(TAG, "get evenement par id");

                    return evenementUnique;
            }
            return evenementApi.list().execute().getItems();

        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<Evenement>();
        }

    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<Evenement> result) {

        if (result != null) {
            listener.updateListViewEvenement(result);
        }
    }

    // Comparateur personnalisé permettant de comparer deux dates d'événements pour pouvoir les trier
    public class CustomComparator implements Comparator<Evenement> {

        @Override
        public int compare(Evenement o1, Evenement o2) {
            String reformatedStringO1 = null;
            String reformatedStringO2 = null;
            int dateIntO1;
            int dateIntO2;

            SimpleDateFormat oldFormat = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyyMMdd");

            try {
                reformatedStringO1 = newFormat.format(oldFormat.parse(o1.getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            dateIntO1 = Integer.parseInt(reformatedStringO1);

            try {
                reformatedStringO2 = newFormat.format(oldFormat.parse(o2.getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            dateIntO2 = Integer.parseInt(reformatedStringO2);

            if (dateIntO1 < dateIntO2) {
                return -1;
            } else if (dateIntO1 > dateIntO2) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }
}
