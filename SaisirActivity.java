package fr.gsb.rv.modeles;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import fr.gsb.rv.R;
import fr.gsb.rv.entites.Motif;
import fr.gsb.rv.entites.Praticien;
import fr.gsb.rv.entites.RapportVisite;
import fr.gsb.rv.technique.Session;

public class SaisirActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private Button valider ;
    private Button annuler ;
    private TextView date;
    private Button modifierDate ;
    private Spinner spPraticiens ;
    private Spinner spMotifs ;
    private EditText etBilan ;
    private Spinner spCoeffConfiance ;
    private TextView tvLePraticien ;
    private  TextView tvLeMotif ;
    private GregorianCalendar dateVisite ;
    private List<Praticien> lesPraticiens = new ArrayList<Praticien>() ;
    private List<Motif> lesMotifs = new ArrayList<Motif>() ;
    private String[] lesCoefConficance = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"} ;
    GregorianCalendar aujourdhui = new GregorianCalendar() ;
    private int jour = aujourdhui.get(Calendar.DAY_OF_MONTH) ;
    private int mois = aujourdhui.get(Calendar.MONTH) ;
    private int annee = aujourdhui.get(Calendar.YEAR) ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getPraticiens();
        this.getMotifs();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisir);
        date = (TextView) findViewById(R.id.date);
        modifierDate = (Button) findViewById(R.id.modifierDate);
        spPraticiens = (Spinner) findViewById(R.id.spPraticien);
        spMotifs = (Spinner) findViewById(R.id.spMotif);
        etBilan = (EditText) findViewById(R.id.etBilan);
        spCoeffConfiance = (Spinner) findViewById(R.id.spCoeffConficance);
        valider = (Button) findViewById(R.id.valider) ;
        annuler = (Button) findViewById(R.id.annuler);

        date.setText(jour + "/" + mois + "/" + annee);

        System.out.println("Taille de lesPraticiens : "+lesPraticiens.size());
        System.out.println("Taille de lesMotifs     : "+lesMotifs.size());

        String[] lesPraticiensTest = {"Notini Alain", "Gosselin Albert", "Desmoulins Anne", "..."} ;
        String[] lesMotifsTest  = {"Actualisation", "Informer", "Nouveaute", "Periodicite"} ;


        Log.i("T.Pra", Integer.toString(lesPraticiens.size()));

        ArrayAdapter<String> aaPraticiens = new ArrayAdapter<String>(
                SaisirActivity.this,
                android.R.layout.simple_spinner_item,
                lesPraticiensTest
        );
        aaPraticiens.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPraticiens.setAdapter(aaPraticiens);
        spPraticiens.setOnItemSelectedListener(new spPraticiensListener());

        ArrayAdapter<String> aaMotifs = new ArrayAdapter<String>(
                SaisirActivity.this,
                android.R.layout.simple_spinner_item,
                lesMotifsTest
        );
        aaMotifs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMotifs.setAdapter(aaMotifs);
        spMotifs.setOnItemSelectedListener(new spMotifsListener());


        ArrayAdapter<String> aaCoefConfiance = new ArrayAdapter<String>(
                SaisirActivity.this,
                android.R.layout.simple_spinner_item,
                lesCoefConficance
        );
        aaCoefConfiance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCoeffConfiance.setAdapter(aaCoefConfiance);
        spCoeffConfiance.setOnItemSelectedListener(new spCoeffConfianceListener());


    }

    public void selectionnerDateCommande(View vue){
        GregorianCalendar aujourdhui = new GregorianCalendar() ;
        int jour = aujourdhui.get(Calendar.DAY_OF_MONTH) ;
        int mois = aujourdhui.get(Calendar.MONTH) ;
        int annee = aujourdhui.get(Calendar.YEAR) ;

        new DatePickerDialog(SaisirActivity.this, this, annee, mois, jour).show();
    }

    public void valider(View vue){
        String url = String.format("http://192.168.1.45:5000/%s/%s/%s/%s", Session.getSession().getVisiteur().getMatricule());
        Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>() {

            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        Praticien unPraticien = new Praticien();
                        unPraticien.setNum(response.getJSONObject(i).getInt("pra_num"));
                        unPraticien.setNom(response.getJSONObject(i).getString("pra_nom"));
                        unPraticien.setPrenom(response.getJSONObject(i).getString("pra_prenom"));
                        unPraticien.setVille(response.getJSONObject(i).getString("pra_ville"));

                        Log.i("le praticien est", unPraticien.toString());
                        lesPraticiens.add(unPraticien);
                    }

                } catch (JSONException e) {
                    Log.e("APP-RV", "Erreur JSON : " + e.getMessage());
                }
            }
        };

        Response.ErrorListener ecouteurErreur = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int codeErreur = Log.e("APP-RV", "ERREUR HTTP : " + error.getMessage());
            }
        };

        JsonArrayRequest requete = new JsonArrayRequest(Request.Method.POST, url, null, ecouteurReponse, ecouteurErreur);
        RequestQueue fileRequetes = Volley.newRequestQueue(this);
        fileRequetes.add(requete);
    }

    public void annuler(View vue){
        etBilan.setText("");
        SaisirActivity.this.finish();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        String dateVisite = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year) ;
        this.date.setText(dateVisite);
        this.dateVisite = new GregorianCalendar(year, monthOfYear, dayOfMonth);
    }

    public void getPraticiens(){
        System.out.println("Appel de getPraticiens");
        String url = String.format("http://192.168.1.45:5000/praticiens");
        Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>() {

            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        Praticien unPraticien = new Praticien();
                        unPraticien.setNum(response.getJSONObject(i).getInt("pra_num"));
                        unPraticien.setNom(response.getJSONObject(i).getString("pra_nom"));
                        unPraticien.setPrenom(response.getJSONObject(i).getString("pra_prenom"));
                        unPraticien.setVille(response.getJSONObject(i).getString("pra_ville"));

                        System.out.println("Nouveau Praticien : "+unPraticien.toString());
                        lesPraticiens.add(unPraticien);
                    }

                } catch (JSONException e) {
                    Log.e("APP-RV", "Erreur JSON : " + e.getMessage());
                }
            }
        };

        Response.ErrorListener ecouteurErreur = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int codeErreur = Log.e("APP-RV", "ERREUR HTTP : " + error.getMessage());
            }
        };

        JsonArrayRequest requete = new JsonArrayRequest(Request.Method.GET, url, null, ecouteurReponse, ecouteurErreur);
        RequestQueue fileRequetes = Volley.newRequestQueue(this);
        fileRequetes.add(requete);
    }

    public void getMotifs(){
        System.out.println("Appel de getMotifs");
        String url = String.format("http://192.168.1.45:5000/motifs");
        Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>() {

            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        Motif unMotif = new Motif() ;
                        unMotif.setCode(response.getJSONObject(i).getString("mo_code"));
                        unMotif.setLibelle(response.getJSONObject(i).getString("mo_libelle"));

                        System.out.println("Nouveau Motif : "+unMotif.toString());
                        lesMotifs.add(unMotif);
                    }

                } catch (JSONException e) {
                    Log.e("APP-RV", "Erreur JSON : " + e.getMessage());
                }
            }
        };

        Response.ErrorListener ecouteurErreur = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                int codeErreur = Log.e("APP-RV", "ERREUR HTTP : " + error.getMessage());
            }
        };

        JsonArrayRequest requete = new JsonArrayRequest(Request.Method.GET, url, null, ecouteurReponse, ecouteurErreur);
        RequestQueue fileRequetes = Volley.newRequestQueue(this);
        fileRequetes.add(requete);
    }
}

class spPraticiensListener implements AdapterView.OnItemSelectedListener{
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("PRATICIEN","Nouvel item selectionne") ;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

class spMotifsListener implements AdapterView.OnItemSelectedListener{
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("MOTIF","Nouvel item selectionne") ;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

class spCoeffConfianceListener implements AdapterView.OnItemSelectedListener{
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("COEF CONFIANCE","Nouvel item selectionne") ;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
