package fr.gsb.rv.entites;

public class Praticien {
    private int Num ;
    private String Nom ;
    private String Prenom ;
    private String adresse ;
    private String cp ;
    private String ville ;
    private double coeffNotoriete ;
    private String typeCode ;

    public Praticien() {
    }

    public Praticien(int num, String nom, String prenom, String adresse, String cp, String ville, double coeffNotoriete, String typeCode) {
        Num = num;
        Nom = nom;
        Prenom = prenom;
        this.adresse = adresse;
        this.cp = cp;
        this.ville = ville;
        this.coeffNotoriete = coeffNotoriete;
        this.typeCode = typeCode;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public double getCoeffNotoriete() {
        return coeffNotoriete;
    }

    public void setCoeffNotoriete(double coeffNotoriete) {
        this.coeffNotoriete = coeffNotoriete;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    @Override
    public String toString() {
        return this.Nom + " " + this.Prenom ;
    }
}
