package com.androidprojects.projetfiesta;

import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.projetfiesta.backend.messageApi.model.Message;
import com.projetfiesta.backend.trajetApi.model.Trajet;

import java.util.List;


public interface OnTaskCompleted {
    void updateListViewTrajet(List<Trajet> trajets);
    void updateListViewEvenement(List<Evenement> evenements);
    void updateListViewMessage(List<Message> messages);
}
