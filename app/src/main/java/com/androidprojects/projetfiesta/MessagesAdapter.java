package com.androidprojects.projetfiesta;


import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;
import com.projetfiesta.backend.trajetApi.model.Trajet;
import com.projetfiesta.backend.utilisateurApi.model.Utilisateur;

import java.util.List;

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
            convertView.setTag(viewHolder);
        }

        // pour chaque message...
        Message message = getItem(position);

        String texte = message.getTexte();
/*
        // .. on récupère le nom de l'utilisateur
        List <Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
        try {
            utilisateurs = new EndpointsAsyncTaskUtilisateur(message.getUtilisateurId(), this ).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Utilisateur utilisateur = utilisateurs.get(0);
        String Chauffeur = "<b>"+utilisateur.getPrenom()+" "+utilisateur.getNom().charAt(0)+".</b>";
*/


        // ... pour afficher le tout
        viewHolder.texte.setText(Html.fromHtml(texte));




        return convertView;
    }

    private class MessageViewHolder{
        public TextView texte;
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
}