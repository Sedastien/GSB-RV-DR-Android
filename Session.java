package fr.gsb.rv.technique;

import fr.gsb.rv.entites.Visiteur;

public class Session {
    private static Session session = null ;
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
}
