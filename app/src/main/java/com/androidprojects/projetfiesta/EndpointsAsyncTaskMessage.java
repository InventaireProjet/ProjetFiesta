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
    private static final String TAG = EndpointsAsyncTaskMessage.class.getName();
    private static MessageApi messageApi = null;
    private Message message;
    private int codeAction;
    private OnTaskCompleted listener;
    private Long trajetId;

    EndpointsAsyncTaskMessage(OnTaskCompleted listener) {
        this.listener = listener;
    }

    EndpointsAsyncTaskMessage(int codeAction, Message message, OnTaskCompleted listener) {
        this.message = message;
        this.listener = listener;
        this.codeAction = codeAction;
    }

    //AsyncTask dédiée à la recherche des messages liés à un trajet
    EndpointsAsyncTaskMessage(Long trajetId, OnTaskCompleted listener) {
        this.listener = listener;
        this.trajetId = trajetId;
        this.codeAction=3;
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
                    .setRootUrl("https://5-dot-projet-fiesta.appspot.com/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            messageApi = builder.build();
        }

        try {
            switch (codeAction) {
                case (0):

                    if (message != null) {
                        messageApi.insert(message).execute();
                        Log.i(TAG, "insert message");
                    }
                    break;
                case (1):
                    if (message != null) {
                        messageApi.update(message.getId(), message).execute();
                        Log.i(TAG, "update message");
                    }
                    break;
                case (2) :
                    if (message != null) {
                        messageApi.remove(message.getId()).execute();
                        Log.i(TAG, "remove message");
                    }
                    break;
                case (3) :


                        List messages = messageApi.messagesParTrajet(trajetId).execute().getItems();
                        Log.i(TAG, "get messages par trajet");

                        return messages;

            }

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
