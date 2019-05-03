package fr.gsb.rv.entites;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Praticien  implements Parcelable {
    private int Num ;
    private String Nom ;
    private String Prenom ;
    private String adresse ;
    private String cp ;
    private String ville ;
    private double coeffNotoriete ;
    private String typeCode ;

    public Praticien()  {
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

    protected Praticien(Parcel in) {
        Num = in.readInt();
        Nom = in.readString();
        Prenom = in.readString();
        adresse = in.readString();
        cp = in.readString();
        ville = in.readString();
        coeffNotoriete = in.readDouble();
        typeCode = in.readString();
    }

    public static final Creator<Praticien> CREATOR = new Creator<Praticien>() {
        @Override
        public Praticien createFromParcel(Parcel in) {
            return new Praticien(in);
        }

        @Override
        public Praticien[] newArray(int size) {
            return new Praticien[size];
        }
    };

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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Num);
        parcel.writeString(Nom);
        parcel.writeString(Prenom);
        parcel.writeString(adresse);
        parcel.writeString(cp);
        parcel.writeString(ville);
        parcel.writeDouble(coeffNotoriete);
        parcel.writeString(typeCode);
    }

    public String toString(){
        return this.Nom + " " + this.Prenom ;
    }
}
