package fr.gsb.rv.modeles;

import android.graphics.ColorSpace;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import fr.gsb.rv.entites.Visiteur;

public class ModeleGSB {
    private static ModeleGSB modele = null ;
    private List<Visiteur> lesVisiteurs = new ArrayList<>() ;
    private ModeleGSB(){
         super();
         this.peupler() ;
    }

    public static ModeleGSB getInstance(){
        if(ModeleGSB.modele==null){
            modele = new ModeleGSB();
        }
        return ModeleGSB.modele ;
    }

    private void peupler(){
        this.lesVisiteurs.add(new Visiteur("a131", "azerty", "VilleChalane", "Louis"));
        this.lesVisiteurs.add(new Visiteur("b13", "azerty", "Bentot", "Pascal"));
        this.lesVisiteurs.add(new Visiteur("b16", "azerty", "Bioret", "Luc"));
        this.lesVisiteurs.add(new Visiteur("a17", "azerty", "Andre", "David"));
    }

    public Visiteur seConnecter(String matricule, String mdp){
        for(Visiteur unVisiteur : this.lesVisiteurs){
            if(unVisiteur.getMatricule().equals(matricule) && unVisiteur.getMdp().equals(mdp)){
                return unVisiteur ;
            }
        }
        return null ;
    }
}
