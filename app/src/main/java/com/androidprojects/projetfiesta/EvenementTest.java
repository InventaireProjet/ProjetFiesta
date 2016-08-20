package com.androidprojects.projetfiesta;

/**
 * Created by elsio on 20.08.2016.
 */
public class EvenementTest {
    private Long id;
    private String titre;
    private String date;
    private int logo;

    public EvenementTest(int logo, String titre, String date) {
        this.logo = logo;
        this.titre = titre;
        this.date = date;
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

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
