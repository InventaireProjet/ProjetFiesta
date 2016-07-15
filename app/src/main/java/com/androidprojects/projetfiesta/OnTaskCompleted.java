package com.androidprojects.projetfiesta;

import com.projetfiesta.backend.trajetApi.model.Trajet;
import com.projetfiesta.backend.utilisateurApi.model.Utilisateur;

import java.util.List;


public interface OnTaskCompleted {
    void updateListViewTrajet(List<Trajet> trajets);
    void updateListViewUtilisateur(List<Utilisateur> utilisateurs);
}
