package fr.gsb.rv.entites;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Motif implements Parcelable {
    private String code ;
    private String libelle ;

    public Motif(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public Motif() {
    }

    protected Motif(Parcel in) {
        code = in.readString();
        libelle = in.readString();
    }

    public static final Creator<Motif> CREATOR = new Creator<Motif>() {
        @Override
        public Motif createFromParcel(Parcel in) {
            return new Motif(in);
        }

        @Override
        public Motif[] newArray(int size) {
            return new Motif[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(code);
        parcel.writeString(libelle);
    }
}
