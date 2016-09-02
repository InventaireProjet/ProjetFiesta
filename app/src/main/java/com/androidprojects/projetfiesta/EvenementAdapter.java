package com.androidprojects.projetfiesta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.projetfiesta.backend.evenementApi.model.Evenement;

import java.io.InputStream;
import java.net.URL;
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
        Bitmap bitmap;
        EvenementViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_evenement,parent, false);
        }

         viewHolder = (EvenementViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new EvenementViewHolder();
            viewHolder.evenementTitre = (TextView) convertView.findViewById(R.id.evenement_titre);
            viewHolder.evenementDate = (TextView) convertView.findViewById(R.id.evenement_date);
            viewHolder.logo = (ImageView) convertView.findViewById(R.id.icon_evenement);
            viewHolder.relativelayout = (RelativeLayout) convertView.findViewById(R.id.evenement_layout);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Evenement> evenements
        Evenement evenement = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.evenementTitre.setText(evenement.getTitre());
        viewHolder.evenementDate.setText(evenement.getDate());

    // Récupération des logos utilisant la classe privée dédiée
        if (evenement.getLogo()!=null) {
                try {
                    bitmap= new LoadImage().execute(evenement.getLogo()).get();
                    viewHolder.logo.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    // Si aucun logo n'a été défini pour l'événement, on affiche le logo "Fiesta" par défaut
        else {
            try {
                Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.fiesta_logo);
                viewHolder.logo.setImageBitmap(icon);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        viewHolder.relativelayout.setOnClickListener(new View.OnClickListener() {
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
        public RelativeLayout relativelayout;
    }

    //Classe privée dédiée à la récupération des images (inspiré et simplifié de : https://www.learn2crack.com/2014/06/android-load-image-from-internet.html)
    private class LoadImage extends AsyncTask<String, String, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... args) {

            Bitmap bitmap=null;
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

    }
}
