package com.projetfiesta.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.text.ParseException;
import java.text.SimpleDateFormat;


@Entity
public class Evenement {

    @Id
    private Long id;
    private String titre;
    @Index private String date;
    private String logo;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getModifiedDate() {

        String reformatedString = null;
        int dateInt;

        SimpleDateFormat oldFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyyMMdd");

        try {
            reformatedString = newFormat.format(oldFormat.parse(this.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dateInt = Integer.parseInt(reformatedString);
        return dateInt;
    }

}
