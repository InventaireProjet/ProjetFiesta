package com.androidprojects.projetfiesta;

import com.projetfiesta.backend.trajetApi.model.Trajet;
import com.projetfiesta.backend.utilisateurApi.model.Utilisateur;
import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;

import java.util.List;


public interface OnTaskCompleted {
    void updateListViewTrajet(List<Trajet> trajets);
    void updateListViewUtilisateur(List<Utilisateur> utilisateurs);
    void updateListViewEvenement(List<Evenement> evenements);
    void updateListViewMessage(List<Message> messages);
}
