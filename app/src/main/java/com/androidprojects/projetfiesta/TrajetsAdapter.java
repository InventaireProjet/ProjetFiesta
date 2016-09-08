package com.androidprojects.projetfiesta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * ArrayAdapter destiné à l'affichage des trajets
 * Créé par NTS
 */
public class TrajetsAdapter extends ArrayAdapter<Trajet> implements OnTaskCompleted {

    public TrajetsAdapter(Context context, List<Trajet> trajets) {
        super(context, 0, trajets);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // On assigne le layout "row_trajets" créé pour afficher les trajets
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_trajets,parent, false);
        }


        TrajetViewHolder viewHolder = (TrajetViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TrajetViewHolder();
            viewHolder.trajetChauffeur = (TextView) convertView.findViewById(R.id.chauffeur);
            viewHolder.trajetDestination = (TextView) convertView.findViewById(R.id.destination);
            viewHolder.trajetNbPlaces = (TextView) convertView.findViewById(R.id.nbPlaces);
            viewHolder.trajetDepart = (TextView) convertView.findViewById(R.id.depart);
            viewHolder.linearlayout = (LinearLayout) convertView.findViewById(R.id.trajetsLayout);
            convertView.setTag(viewHolder);
        }

        // pour chaque trajet...
        final Trajet trajet = getItem(position);

        // .. on récupère le nom du chauffeur
        List <Utilisateur> chauffeurs = new ArrayList<Utilisateur>();
        try {
            chauffeurs = new EndpointsAsyncTaskUtilisateur(trajet.getConducteurId(), this ).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Utilisateur chauffeur = chauffeurs.get(0);
        String Chauffeur = "<b>"+chauffeur.getPrenom()+" "+chauffeur.getNom().charAt(0)+".</b>";

        // ...sa destination
        String ville = "";

        if(trajet.getDestination().length()>7)
            ville = trajet.getDestination().substring(0,7) + "...";
        else
            ville = trajet.getDestination().toString();

        String Destination = "Destination "+"<b>"+ville+"</b>";

        // ... le nombre de places disponibles
        String NbPlaces;
        if (trajet.getNombrePlaces()<2)
        {
            NbPlaces = "<b>"+String.valueOf(trajet.getNombrePlaces())+" place"+"</b>";
        }
        else
        {
            NbPlaces = "<b>"+String.valueOf(trajet.getNombrePlaces())+" places"+"</b>";
        }

        // ... et l'heure de départ
        String Depart = "Départ "+"<b>"+"~"+trajet.getHeureDepart()+"</b>";

        // ... pour afficher le tout
        viewHolder.trajetChauffeur.setText(Html.fromHtml(Chauffeur));
        viewHolder.trajetDestination.setText(Html.fromHtml(Destination));
        viewHolder.trajetNbPlaces.setText(Html.fromHtml(NbPlaces));
        viewHolder.trajetDepart.setText(Html.fromHtml(Depart));


        // En cliquant sur un trajet on affiche le trajet concerné ou bien le chat (nouvelle activité)
        viewHolder.linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Récupération de l'id de l'utilisateur
                SharedPreferences settings = getContext().getSharedPreferences("prefs",0);
                final Long idUtilisateur = settings.getLong("idUtilisateur",0);
                Long trajetId = getItem(position).getId();
                Intent intent;

                /*Si le conducteur pour le trajet sélectionné correspond à l'utilisateur de l'app
                ou si l'utilisateur a déjà posté un message pour ce trajet,
                on passe directement à l'affichage du chat
                 */
                if (trajet.getConducteurId().equals(idUtilisateur) || dejaPosteMessage(trajetId, idUtilisateur)) {
                    intent = new Intent(getContext(), Chat.class);
                } else {
                    intent = new Intent(getContext(), AfficherTrajet.class);
                }

                intent.putExtra("trajetId", trajetId);
                getContext().startActivity(intent);
            }
        });


        return convertView;
    }

    private class TrajetViewHolder{
        public TextView trajetChauffeur;
        public TextView trajetDestination;
        public TextView trajetNbPlaces;
        public TextView trajetDepart;
        public LinearLayout linearlayout;
    }

    //Méthode de vérification si un message a déjà été posté par un utilisateur donné pour un trajet donné
    private boolean dejaPosteMessage (Long trajetId, Long utilisateurId){

        List <Message> messages = new ArrayList<>();
        try {
            messages = new EndpointsAsyncTaskMessage(trajetId, this ).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (messages!=null) {
            for (Message message:messages)
            {
                if (message.getUtilisateurId().equals(utilisateurId))
                {
                    return true;
                }
            }
        }
        return false;
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

