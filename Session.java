package fr.gsb.rv.technique;

import fr.gsb.rv.entites.Visiteur;

public class Session {
    private static Session session = null ;
    private String ipServeur = "192.168.1.45"; ///Valeur par defaut
    Visiteur leVisiteur ;

    private Session(Visiteur visiteur){
        this.leVisiteur = visiteur ;
    }

    public static Session getSession(){
        return session ;
    }

    public static void fermer(){
        session = null ;
    }

    public static void ouvrir(Visiteur visiteur){
       session = new Session(visiteur) ;
    }

    public Visiteur getVisiteur() { return this.leVisiteur ; }

    public void setIpServeur(String ip){
        this.ipServeur = ip ;
    }

    public String getIpServeur(){
        return this.ipServeur ;
    }
}
