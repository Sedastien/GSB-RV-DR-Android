package fr.gsb.rv.entites;

public class Visiteur {

    private String matricule ;
    private String mdp ;
    private String nom ;
    private String prenom ;

    public Visiteur(String matricule, String mdp, String nom, String prenom)
    {
        this.matricule = matricule ;
        this.mdp = mdp ;
        this.nom = nom ;
        this.prenom = prenom ;
    }

    public Visiteur(){

    }


    public String getMatricule() {
        return matricule;
    }

    public String getMdp() {
        return mdp;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public String toString() {
        return "Visiteur{" +
                "matricule='" + matricule + '\'' +
                ", mdp='" + mdp + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}
