package com.androidprojects.projetfiesta;


import android.os.AsyncTask;
import android.util.Log;

import com.projetfiesta.backend.trajetApi.TrajetApi;
import com.projetfiesta.backend.trajetApi.model.Trajet;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class EndpointsAsyncTaskTrajet extends AsyncTask<Void, Void, List<Trajet>> {
    private static final String TAG = EndpointsAsyncTaskTrajet.class.getName();
    private static TrajetApi trajetApi = null;
    private Trajet trajet;
    private int codeAction;
    private OnTaskCompleted listener;
    private Long evenementId;
    private Long trajetId;

    EndpointsAsyncTaskTrajet(OnTaskCompleted listener) {
        this.listener = listener;
    }

    EndpointsAsyncTaskTrajet(int codeAction , Trajet trajet, OnTaskCompleted listener) {
        this.trajet = trajet;
        this.listener = listener;
        this.codeAction=codeAction;
    }

    //AsyncTask dédiée à la recherche des trajets liés à un événement
    EndpointsAsyncTaskTrajet(Long evenementId, OnTaskCompleted listener) {
        this.listener = listener;
        this.evenementId = evenementId;
        this.codeAction=3;
    }

    //AsyncTask dédiée à l'affichage d'un trajet
    EndpointsAsyncTaskTrajet(OnTaskCompleted listener, Long trajetId ) {
        this.listener = listener;
        this.trajetId = trajetId;
        this.codeAction=4;
    }
    @Override
    protected List<Trajet> doInBackground(Void... params) {

        if (trajetApi == null) {
            // Only do this once
            TrajetApi.Builder builder = new TrajetApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    // if you deploy on the cloud backend, use your app name
                    // such as https://<your-app-id>.appspot.com
                    .setRootUrl("https://5-dot-projet-fiesta.appspot.com/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            trajetApi = builder.build();
        }

        try {

            switch (codeAction) {

                case (0):
                    if (trajet != null) {
                        trajetApi.insert(trajet).execute();
                        Log.i(TAG, "insert trajet");
                    }
                    break;
                case (1):
                    if (trajet != null) {
                        trajetApi.update(trajet.getId(), trajet).execute();
                        Log.i(TAG, "update trajet");
                    }
                    break;
                case (2):
                    if (trajet != null) {
                        trajetApi.remove(trajet.getId()).execute();
                        Log.i(TAG, "remove trajet");
                    }
                    break;
                case (3):

                    List trajets = trajetApi.trajetsParEvenement(evenementId).execute().getItems();
                    Log.i(TAG, "get trajets par evenement");

                    return trajets;

                case (4):

                    Trajet trajet = trajetApi.get(trajetId).execute();
                    List trajetUnique = new ArrayList();
                    trajetUnique.add(trajet);
                    Log.i(TAG, "get trajet par id");

                    return trajetUnique;
            }
            return trajetApi.list().execute().getItems();

        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<Trajet>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<Trajet> result) {

        if (result != null) {
            listener.updateListViewTrajet(result);
        }
    }
}
