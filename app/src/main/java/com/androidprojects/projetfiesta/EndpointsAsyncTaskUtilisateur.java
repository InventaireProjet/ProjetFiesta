package com.androidprojects.projetfiesta;


import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.projetfiesta.backend.utilisateurApi.UtilisateurApi;
import com.projetfiesta.backend.utilisateurApi.model.Utilisateur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class EndpointsAsyncTaskUtilisateur extends AsyncTask<Void, Void, List<Utilisateur>> {
    private static final String TAG = EndpointsAsyncTaskUtilisateur.class.getName();
    private static UtilisateurApi utilisateurApi = null;
    private Utilisateur utilisateur;
    private int codeAction;
    private OnTaskCompleted listener;

    EndpointsAsyncTaskUtilisateur(OnTaskCompleted listener) {
        this.listener = listener;
    }

    EndpointsAsyncTaskUtilisateur(int codeAction , Utilisateur utilisateur, OnTaskCompleted listener) {
        this.utilisateur = utilisateur;
        this.listener = listener;
        this.codeAction = codeAction;
    }

    @Override
    protected List<Utilisateur> doInBackground(Void... params) {

        if (utilisateurApi == null) {
            // Only do this once
            UtilisateurApi.Builder builder = new UtilisateurApi.Builder(AndroidHttp.newCompatibleTransport(),
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
            utilisateurApi = builder.build();
        }

        try {
            switch (codeAction) {

                case (0):
                    if (utilisateur != null) {
                        utilisateurApi.insert(utilisateur).execute();
                        Log.i(TAG, "insert utilisateur");
                    }
                    break;
                case (1):
                    if (utilisateur != null) {
                        utilisateurApi.update(utilisateur.getId(), utilisateur).execute();
                        Log.i(TAG, "update utilisateur");
                    }
                    break;
                case (2) :
                    if (utilisateur != null) {
                        utilisateurApi.remove(utilisateur.getId()).execute();
                        Log.i(TAG, "remove utilisateur");
                    }
                    break;
            }


            return utilisateurApi.list().execute().getItems();

        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<Utilisateur>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<Utilisateur> result) {

        if (result != null) {
            listener.updateListViewUtilisateur(result);
        }
    }
}
