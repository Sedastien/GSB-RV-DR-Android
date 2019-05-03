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
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import fr.gsb.rv.R;
import fr.gsb.rv.entites.Motif;
import fr.gsb.rv.entites.Praticien;
import fr.gsb.rv.entites.RapportVisite;
import fr.gsb.rv.technique.Session;

public class SaisirActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
,AdapterView.OnItemSelectedListener{

    private Button valider ;
    private Button annuler ;
    private TextView date;
    private String laDateDeVisite ;
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
    private List<String> lesPraticiensString = new ArrayList<>();
    private List<String> lesMotifsString = new ArrayList<>();
    private String[] lesCoefConficance = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"} ;
    GregorianCalendar aujourdhui = new GregorianCalendar() ;
    private int jour = aujourdhui.get(Calendar.DAY_OF_MONTH) ;
    private int mois = aujourdhui.get(Calendar.MONTH) ;
    private int annee = aujourdhui.get(Calendar.YEAR) ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisir);
        Bundle paquet = getIntent().getExtras();
        lesMotifs = paquet.getParcelableArrayList("lesMotifs") ;
        lesPraticiens = paquet.getParcelableArrayList("lesPraticiens") ;
        date = (TextView) findViewById(R.id.date);
        modifierDate = (Button) findViewById(R.id.modifierDate);
        spPraticiens = (Spinner) findViewById(R.id.spPraticien);
        spMotifs = (Spinner) findViewById(R.id.spMotif);
        etBilan = (EditText) findViewById(R.id.etBilan);
        spCoeffConfiance = (Spinner) findViewById(R.id.spCoeffConficance);
        valider = (Button) findViewById(R.id.valider) ;
        annuler = (Button) findViewById(R.id.annuler);

        date.setText(jour + "/" + mois + "/" + annee);
        laDateDeVisite = annee+"-"+mois+"-"+jour;

        System.out.println("Taille de lesPraticiens : "+lesPraticiens.size());
        System.out.println("Taille de lesMotifs     : "+lesMotifs.size());

        for(int i=0;i<lesPraticiens.size();i++){
            lesPraticiensString.add(lesPraticiens.get(i).toString()) ;
        }

        for(int i=0;i<lesMotifs.size();i++){
            lesMotifsString.add(lesMotifs.get(i).toString()) ;
        }


        Log.i("T.Pra", Integer.toString(lesPraticiens.size()));

        ArrayAdapter<String> aaPraticiens = new ArrayAdapter<String>(
                SaisirActivity.this,
                android.R.layout.simple_spinner_item,
                lesPraticiensString//Test
        );
        aaPraticiens.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPraticiens.setAdapter(aaPraticiens);
        spPraticiens.setOnItemSelectedListener(this);
        //spPraticiens.setOnItemSelectedListener(new spPraticiensListener());
        Log.i("T.Pra", Integer.toString(lesPraticiens.size()));

        ArrayAdapter<String> aaMotifs = new ArrayAdapter<String>(
                SaisirActivity.this,
                android.R.layout.simple_spinner_item,
                lesMotifsString//Test
        );
        aaMotifs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMotifs.setAdapter(aaMotifs);
        spMotifs.setOnItemSelectedListener(this);
        //spMotifs.setOnItemSelectedListener(new spMotifsListener());


        ArrayAdapter<String> aaCoefConfiance = new ArrayAdapter<String>(
                SaisirActivity.this,
                android.R.layout.simple_spinner_item,
                lesCoefConficance
        );
        aaCoefConfiance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCoeffConfiance.setAdapter(aaCoefConfiance);
        spCoeffConfiance.setOnItemSelectedListener(this);
        //spCoeffConfiance.setOnItemSelectedListener(new spCoeffConfianceListener());


    }

    public void selectionnerDateCommande(View vue){
        GregorianCalendar aujourdhui = new GregorianCalendar() ;
        int jour = aujourdhui.get(Calendar.DAY_OF_MONTH) ;
        int mois = aujourdhui.get(Calendar.MONTH) ;
        int annee = aujourdhui.get(Calendar.YEAR) ;

        new DatePickerDialog(SaisirActivity.this, this, annee, mois, jour).show();
    }

    public void valider(View vue){
        Praticien praticienSelectionne = new Praticien() ;
        Motif motifSelectionne = new Motif() ;

        praticienSelectionne = lesPraticiens.get(spPraticiens.getSelectedItemPosition());
        motifSelectionne = lesMotifs.get(spMotifs.getSelectedItemPosition()) ;

        final String matriculeVisiteur = Session.getSession().getVisiteur().getMatricule() ;
        final String matriculePraticien = Integer.toString(praticienSelectionne.getNum());
        final String codeMotif = motifSelectionne.getCode();
        final String bilan = etBilan.getText().toString();
        final String dateRedaction = annee+"-"+mois+"-"+jour ;
        final String dateVisite = this.laDateDeVisite;
        final String rapCoeffConfiance = lesCoefConficance[spCoeffConfiance.getSelectedItemPosition()];

        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    String url1 = String.format("http://%s:5000/rapports", Session.getSession().getIpServeur());
                    URL url = new URL(url1);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("matricule", matriculeVisiteur);
                    jsonParam.put("dateVisite", dateVisite) ;
                    jsonParam.put("bilan", bilan);
                    jsonParam.put("rapDateRedaction", dateRedaction) ;
                    jsonParam.put("rapCoeffConfiance",rapCoeffConfiance) ;
                    jsonParam.put("praticien", matriculePraticien);
                    jsonParam.put("codeMotif", codeMotif);

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG", conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }


    public void annuler(View vue){
        etBilan.setText("");
        SaisirActivity.this.finish();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        String dateVisite = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year) ;
        this.laDateDeVisite = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth) ;
        this.date.setText(dateVisite);
        this.dateVisite = new GregorianCalendar(year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        System.out.println("Nouvel item selectionne ! Taille de praticiens : "+lesPraticiens.size() );
        System.out.println("Nouvel item selectionne ! Taille de Motifs : "+lesMotifs.size() );
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

class spPraticiensListener implements AdapterView.OnItemSelectedListener{
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("PRATICIEN","Nouvel item selectionne") ;
        System.out.println("Taille actuelle de Praticiens :");
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
