package com.androidprojects.projetfiesta;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.projetfiesta.backend.trajetApi.model.Trajet;

import java.util.List;

/**
 * Created by NTS on 28.08.2016.
 */
public class TrajetsAdapter extends ArrayAdapter<Trajet> {

    public TrajetsAdapter(Context context, List<Trajet> trajets) {
        super(context, 0, trajets);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_trajets,parent, false);
        }

        TrajetViewHolder viewHolder = (TrajetViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TrajetViewHolder();
            viewHolder.trajetDestination = (TextView) convertView.findViewById(R.id.destination);
            viewHolder.trajetNbPlaces = (TextView) convertView.findViewById(R.id.nbPlaces);
            viewHolder.trajetDepart = (TextView) convertView.findViewById(R.id.depart);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Trajet> trajets
        Trajet trajet = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.trajetDestination.setText(trajet.getDestination());
        viewHolder.trajetNbPlaces.setText(trajet.getNombrePlaces());
        viewHolder.trajetDepart.setText(trajet.getHeureDepart());

        viewHolder.trajetDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AfficherTrajet.class);
                Long trajetId = getItem(position).getId();
                intent.putExtra("trajetId",trajetId);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    private class TrajetViewHolder{
        public TextView trajetDestination;
        public TextView trajetNbPlaces;
        public TextView trajetDepart;
    }
}

