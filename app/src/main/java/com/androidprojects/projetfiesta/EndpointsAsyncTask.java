package com.androidprojects.projetfiesta;


import android.os.AsyncTask;
import android.util.Log;

import com.example.ingestien.projetfiesta.backend.trajetApi.TrajetApi;
import com.example.ingestien.projetfiesta.backend.trajetApi.model.Trajet;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class EndpointsAsyncTask extends AsyncTask<Void, Void, List<Trajet>> {
    private static TrajetApi trajetApi = null;
    private static final String TAG = EndpointsAsyncTask.class.getName();
    private Trajet trajet;
    private OnTaskCompleted listener;

    EndpointsAsyncTask(OnTaskCompleted listener) {
        this.listener = listener;
    }

    EndpointsAsyncTask(Trajet trajet, OnTaskCompleted listener) {
        this.trajet = trajet;
        this.listener = listener;
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
                    .setRootUrl("https://projet-fiesta.appspot.com/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            trajetApi = builder.build();
        }

        try {
            // Call here the wished methods on the Endpoints
            // For instance insert
            if (trajet != null) {
                trajetApi.insert(trajet).execute();
                Log.i(TAG, "insert trajet");
            }
            // and for instance return the list of all trajets
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
            listener.updateListView(result);
        }
    }
}
