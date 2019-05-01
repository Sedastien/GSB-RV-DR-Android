package fr.gsb.rv.entites;

public class Echantillon {
    private int quantite ;
    private String nom ;

    public Echantillon(int quantite, String nom) {
        this.quantite = quantite;
        this.nom = nom;
    }

    public Echantillon() {
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return quantite + " Echantillon(s) de " + nom;
    }
}
