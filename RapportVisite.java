package fr.gsb.rv.entites;

import android.os.Parcelable;
import android.os.Parcel;
import android.util.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RapportVisite implements Parcelable {

    private String rapBilan ;
    private int rapCoefConficance ;
    private String praCp ;
    private int rapNum ;
    private String praNom ;
    private String rapDateVisite ;
    private String praPrenom ;
    private String rapDateRedaction ;
    private String praVille ;
    private String[] mois = {
            "Janvier",
            "Fevrier",
            "Mars",
            "Avril",
            "Mai",
            "juin",
            "Juillet",
            "Aout",
            "Septembre",
            "Octobre",
            "Novembre",
            "Decembre"
    } ;

    private String[] rangMois = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    public RapportVisite(String rapBilan, int rapCoefConficance, String praCp, String praNom, String rapDateVisite, String praPrenom, String rapDateRedaction, String praVille) {
        this.rapBilan = rapBilan;
        this.rapCoefConficance = rapCoefConficance;
        this.praCp = praCp;
        this.praNom = praNom;
        this.rapDateVisite = rapDateVisite;
        this.praPrenom = praPrenom;
        this.rapDateRedaction = rapDateRedaction;
        this.praVille = praVille;
    }

    public RapportVisite() {
    }

    protected RapportVisite(Parcel in) {
        rapBilan = in.readString();
        rapCoefConficance = in.readInt();
        praCp = in.readString();
        rapNum = in.readInt();
        praNom = in.readString();
        rapDateVisite = in.readString();
        praPrenom = in.readString();
        rapDateRedaction = in.readString();
        praVille = in.readString();
        mois = in.createStringArray();
        rangMois = in.createStringArray();
    }

    public static final Creator<RapportVisite> CREATOR = new Creator<RapportVisite>() {
        @Override
        public RapportVisite createFromParcel(Parcel in) {
            return new RapportVisite(in);
        }

        @Override
        public RapportVisite[] newArray(int size) {
            return new RapportVisite[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(rapBilan);
        parcel.writeInt(rapCoefConficance);
        parcel.writeString(praCp);
        parcel.writeInt(rapNum);
        parcel.writeString(praNom);
        parcel.writeString(rapDateVisite);
        parcel.writeString(praPrenom);
        parcel.writeString(rapDateRedaction);
        parcel.writeString(praVille);
        parcel.writeStringArray(mois);
        parcel.writeStringArray(rangMois);
    }

    public String getRapBilan() {
        return rapBilan;
    }

    public int getRapCoefConficance() {
        return rapCoefConficance;
    }

    public String getPraCp() {
        return praCp;
    }

    public int getrapNum() {
        return rapNum;
    }

    public String getPraNom() {
        return praNom;
    }

    public String getRapDateVisite() {
        return rapDateVisite;
    }

    public String getPraPrenom() {
        return praPrenom;
    }

    public String getRapDateRedaction() {
        return rapDateRedaction;
    }

    public String getPraVille() {
        return praVille;
    }

    public void setRapBilan(String rapBilan) {
        this.rapBilan = rapBilan;
    }

    public void setRapCoefConficance(int rapCoefConficance) {
        this.rapCoefConficance = rapCoefConficance;
    }

    public void setPraCp(String praCp) {
        this.praCp = praCp;
    }

    public void setRapNum(int praNum) {
        this.rapNum = praNum;
    }

    public void setPraNom(String praNom) {
        this.praNom = praNom;
    }

    public void setRapDateVisite(String rapDateVisite) {
        this.rapDateVisite = rapDateVisite;
    }

    public void setPraPrenom(String praPrenom) {
        this.praPrenom = praPrenom;
    }

    public void setRapDateRedaction(String rapDateRedaction) {
        this.rapDateRedaction = rapDateRedaction;
    }

    public void setPraVille(String praVille) {
        this.praVille = praVille;
    }

    public String dateToString(String date){
        String laDate ;
        String valeurs[] = date.split("-") ;
        String jour = valeurs[2] ;
        String mois = valeurs[1] ;
        String annee = valeurs[0] ;
        for(int i=0;i<12;i++){
            if(mois.equals(this.rangMois[i])){
                mois = this.mois[i] ;
            }
        }
        return jour+" "+mois+" "+annee;
    }

    public String Presenter(){
        String presentation ;
        presentation = "\n\t\t\t\t\t\t\t\t=== Rapport numero "+rapNum+" ===\n\n"
                + " \t\t\t\t\t  ----- Informations sur le Rapport -----\n\n"
                + " Date de redaction du rapport : "+this.dateToString(rapDateRedaction)+"\n"
                + " Date de Visite                            : "+this.dateToString(rapDateVisite)+"\n"
                + " Bilan du rapport                        : "+rapBilan+"\n"
                + " Coefficient de confiance         : "+rapCoefConficance+"\n\n"
                + "\t\t\t\t\t  ----- Informations sur le Praticien ----- \n\n"
                + " Praticien                                     : "+praNom + " " + praPrenom+"\n"
                + " Ville Praticien                             : "+praVille+"\n"
        ;
        return presentation ;
    }

    @Override
    public String toString() {
        return "Rapport numero " + rapNum + " du " + this.dateToString(rapDateRedaction);
    }

}
