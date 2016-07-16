package com.androidprojects.projetfiesta;


import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.projetfiesta.backend.messageApi.MessageApi;
import com.projetfiesta.backend.messageApi.model.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class EndpointsAsyncTaskMessage extends AsyncTask<Void, Void, List<Message>> {
    private static MessageApi messageApi = null;
    private static final String TAG = EndpointsAsyncTaskMessage.class.getName();
    private Message message;
    private OnTaskCompleted listener;

    EndpointsAsyncTaskMessage(OnTaskCompleted listener) {
        this.listener = listener;
    }

    EndpointsAsyncTaskMessage(Message message, OnTaskCompleted listener) {
        this.message = message;
        this.listener = listener;
    }

    @Override
    protected List<Message> doInBackground(Void... params) {

        if (messageApi == null) {
            // Only do this once
            MessageApi.Builder builder = new MessageApi.Builder(AndroidHttp.newCompatibleTransport(),
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
            messageApi = builder.build();
        }

        try {
            // Call here the wished methods on the Endpoints
            // For instance insert
            if (message != null) {
                messageApi.insert(message).execute();
                Log.i(TAG, "insert message");
            }
            // and for instance return the list of all trajets
            return messageApi.list().execute().getItems();

        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<Message>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<Message> result) {

        if (result != null) {
            listener.updateListViewMessage(result);
        }
    }
}
