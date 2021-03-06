package com.androidprojects.projetfiesta;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;
import com.projetfiesta.backend.trajetApi.model.Trajet;
import com.projetfiesta.backend.utilisateurApi.model.Utilisateur;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

public class MessagesAdapter extends ArrayAdapter<Message> implements OnTaskCompleted {

    public MessagesAdapter(Context context, List<Message> messages) {
        super(context, 0, messages);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // On assigne le layout "bulle_message" créé pour afficher les messages
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bulle_message,parent, false);
        }


        MessageViewHolder viewHolder = (MessageViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new MessageViewHolder();
            viewHolder.texte = (TextView) convertView.findViewById(R.id.texte);
            viewHolder.nomUtilisateur = (TextView)  convertView.findViewById(R.id.nomUtilisateur);
            viewHolder.heureMessage = (TextView)  convertView.findViewById(R.id.heureMessage);
            viewHolder.bulle = (LinearLayout) convertView.findViewById(R.id.bulle);
            convertView.setTag(viewHolder);
        }

        // pour chaque message...
        Message message = getItem(position);

        String texte = message.getTexte();

        // .. on récupère le nom de celui qui a posté le message
        List <Utilisateur> posteurs = new ArrayList<Utilisateur>();
        try {
            posteurs = new EndpointsAsyncTaskUtilisateur(message.getUtilisateurId(), this ).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Utilisateur posteur = posteurs.get(0);

        //Récupération de l'id de l'utilisateur de l'app
        SharedPreferences settings = getContext().getSharedPreferences("prefs",0);
        final Long idUtilisateur = settings.getLong("idUtilisateur",0);


        //... et l'heure d'écriture du message en convertissant du timestamp en heure et minutes
        long timestamp = Long.parseLong(message.getDateHeure());
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm (dd.MM)");
        sdf.setTimeZone(tz);
        String localTime = sdf.format(new Date(timestamp));


        // ... pour afficher le tout
        viewHolder.texte.setText(texte);
        //viewHolder.heureMessage.setText(heureMessage);
        viewHolder.heureMessage.setText(localTime);

        //Si l'utilisateur de l'app a posté le message, l'affichage diffère :
        // - s'il s'agit d'un message posté par l'utilisateur connecté ...
        if (idUtilisateur.equals(posteur.getId())) {
            viewHolder.bulle.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bubble_white));
            viewHolder.bulle.setPadding(60, 20, 140, 40);
            viewHolder.texte.setTextColor(ContextCompat.getColor(getContext(), R.color.rouge_texte));
            viewHolder.heureMessage.setTextColor(ContextCompat.getColor(getContext(), R.color.rouge_texte));
        }
        // - ou s'il s'agit d'un message posté par un autre utilisateur
        else{
            viewHolder.bulle.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bubble_red));
            viewHolder.bulle.setPadding(170, 10, 80, 40);
            String posteurNom = posteur.getPrenom()+" "+posteur.getNom().charAt(0) +".";
            viewHolder.nomUtilisateur.setText(posteurNom);
            viewHolder.texte.setTextColor(ContextCompat.getColor(getContext(), R.color.blanc));
            viewHolder.heureMessage.setTextColor(ContextCompat.getColor(getContext(), R.color.blanc));
        }

        return convertView;
    }

    private class MessageViewHolder{
        public TextView texte;
        public TextView nomUtilisateur;
        public  TextView heureMessage;
        public LinearLayout bulle;
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