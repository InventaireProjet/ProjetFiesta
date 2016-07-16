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
import java.util.ArrayList;
import java.util.List;


public class EndpointsAsyncTaskEvenement extends AsyncTask<Void, Void, List<Evenement>> {
    private static EvenementApi evenementApi = null;
    private static final String TAG = EndpointsAsyncTaskEvenement.class.getName();
    private Evenement evenement;
    private OnTaskCompleted listener;

    EndpointsAsyncTaskEvenement(OnTaskCompleted listener) {
        this.listener = listener;
    }

    EndpointsAsyncTaskEvenement(Evenement evenement, OnTaskCompleted listener) {
        this.evenement = evenement;
        this.listener = listener;
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
                    .setRootUrl("https://projet-fiesta.appspot.com/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            evenementApi = builder.build();
        }

        try {
            // Call here the wished methods on the Endpoints
            // For instance insert
            if (evenement != null) {
                evenementApi.insert(evenement).execute();
                Log.i(TAG, "insert evenement");
            }
            // and for instance return the list of all trajets
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
}
