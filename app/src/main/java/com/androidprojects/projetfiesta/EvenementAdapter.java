package com.androidprojects.projetfiesta;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.projetfiesta.backend.evenementApi.model.Evenement;

import java.util.List;

/**
 * Created by elsio on 20.08.2016.
 */
public class EvenementAdapter extends ArrayAdapter<Evenement> {
    //tweets est la liste des models à afficher
    public EvenementAdapter(Context context, List<Evenement> evenements) {
        super(context, 0, evenements);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_evenement,parent, false);
        }

        EvenementViewHolder viewHolder = (EvenementViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new EvenementViewHolder();
            viewHolder.evenementTitre = (TextView) convertView.findViewById(R.id.evenement_titre);
            viewHolder.evenementDate = (TextView) convertView.findViewById(R.id.evenement_date);
            viewHolder.logo = (ImageView) convertView.findViewById(R.id.icon_evenement);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Evenement> evenements
        Evenement evenement = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.evenementTitre.setText(evenement.getTitre());
        viewHolder.evenementDate.setText(evenement.getDate());
        viewHolder.logo.setImageURI( Uri.parse(evenement.getLogo()));

        viewHolder.evenementTitre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AfficherTrajets.class);
                Long evenementId = getItem(position).getId();
                intent.putExtra("evenementId",evenementId);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    private class EvenementViewHolder{
        public TextView evenementTitre;
        public TextView evenementDate;
        public ImageView logo;
    }

}
