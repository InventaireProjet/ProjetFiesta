package com.androidprojects.projetfiesta;

import com.example.ingestien.projetfiesta.backend.trajetApi.model.Trajet;

import java.util.List;


public interface OnTaskCompleted {
    void updateListView(List<Trajet> trajets);
}
