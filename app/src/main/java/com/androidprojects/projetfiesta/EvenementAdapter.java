package com.androidprojects.projetfiesta;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.projetfiesta.backend.evenementApi.model.Evenement;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by elsio on 20.08.2016.
 */
public class EvenementAdapter extends ArrayAdapter<Evenement> {



    //tweets est la liste des models à afficher
    public EvenementAdapter(Context context, List<Evenement> evenements) {
        super(context, 0, evenements);
    }

    class MyViewHolder{

        ImageView logo;
        TextView date;
        TextView titre;
        RelativeLayout relativeLayout;

        MyViewHolder(View v){

            logo = (ImageView) v.findViewById(R.id.icon_evenement);
            date = (TextView) v.findViewById(R.id.evenement_date);
            titre = (TextView) v.findViewById(R.id.evenement_titre);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.evenement_layout);

        }
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MyViewHolder myViewHolder = null;

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_evenement, parent, false);
            myViewHolder = new MyViewHolder(row);
            row.setTag(myViewHolder);
        }
        else{
            myViewHolder = (MyViewHolder) row.getTag();
        }


        //getItem(position) va récupérer l'item [position] de la List<Evenement> evenements
        Evenement evenement = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        myViewHolder.titre.setText(evenement.getTitre());
        myViewHolder.date.setText(evenement.getDate());

        // Récupération des logos utilisant la classe privée dédiée
        /*
            Pour l'affichage des photos nous utilisons le système Picasso qui met en cache les images afin de ne pas ralentir la listview
            http://square.github.io/picasso/
            https://www.youtube.com/watch?v=8051fZyWToA&list=PL9jCwTXYWjDK0tL-lN5coNLNCbH_cL8Ph&index=7
         */
        if (evenement.getLogo()!=null) {
            try {
                Picasso.with(myViewHolder.logo.getContext()).load(evenement.getLogo()).into(myViewHolder.logo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Si aucun logo n'a été défini pour l'événement, on affiche le logo "Fiesta" par défaut
        else {
            try {
                Picasso.with(myViewHolder.logo.getContext()).load(R.drawable.fiesta_logo).into(myViewHolder.logo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        myViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AfficherTrajets.class);
                Long evenementId = getItem(position).getId();
                intent.putExtra("evenementId",evenementId);
                getContext().startActivity(intent);
            }
        });

        return row;

    }
}