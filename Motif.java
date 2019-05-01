package fr.gsb.rv.entites;

public class Motif {
    private String code ;
    private String libelle ;

    public Motif(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public Motif() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return libelle;
    }
}
