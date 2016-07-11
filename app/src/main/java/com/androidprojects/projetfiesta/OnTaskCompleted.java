package com.androidprojects.projetfiesta;

import com.projetfiesta.backend.trajetApi.model.Trajet;

import java.util.List;


public interface OnTaskCompleted {
    void updateListView(List<Trajet> trajets);
}
