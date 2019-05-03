package fr.gsb.rv.modeles;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import fr.gsb.rv.MainActivity;
import fr.gsb.rv.R;
import fr.gsb.rv.entites.Motif;
import fr.gsb.rv.entites.Praticien;
import fr.gsb.rv.entites.Visiteur;
import fr.gsb.rv.modeles.ModeleGSB;
import fr.gsb.rv.technique.Session;

@SuppressLint("Registered")
public class MenuActivity extends AppCompatActivity {
    TextView Identite;
    List<Praticien> lesPraticiens = new ArrayList<Praticien>();
    List<Motif> lesMotifs = new ArrayList<Motif>();
    Button ConsulterRapport;
    Button SaisirRapport;
    private String identite;
    private String matricule;
    private boolean fait = false ;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Identite = (TextView) findViewById(R.id.tvIdentite);

        Bundle paquet = this.getIntent().getExtras();
        this.identite = paquet.getString("identite");
        this.matricule = paquet.getString("matricule");

        Identite.setText(identite);
    }

    public void consulter(View vue) {
        Bundle paquet = new Bundle();
        paquet.putString("identite", this.identite);
        paquet.putString("matricule", this.matricule);

        Intent intentionEnvoyer = new Intent(MenuActivity.this, consulterActivity.class);
        intentionEnvoyer.putExtras(paquet);

        startActivity(intentionEnvoyer);
    }

    public void saisir(View vue) throws InterruptedException {
        fait = false;
        lesMotifs = getMotifs() ;
        lesPraticiens = getPraticiens() ;
    }

    public List<Praticien> getPraticiens() {
        final List<Praticien> praticiens = new ArrayList<Praticien>();
        System.out.println("Appel de getPraticiens");
        String url = String.format("http://%s:5000/praticiens", Session.getSession().getIpServeur());
        Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>() {

            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        Praticien unPraticien = new Praticien();
                        unPraticien.setNum(response.getJSONObject(i).getInt("pra_num"));
                        unPraticien.setNom(response.getJSONObject(i).getString("pra_nom"));
                        unPraticien.setPrenom(response.getJSONObject(i).getString("pra_prenom"));
                        unPraticien.setVille(response.getJSONObject(i).getString("pra_ville"));
                        System.out.println("Nouveau Praticien : " + unPraticien.toString());
                        praticiens.add(unPraticien);
                    }
                    System.out.println("Sortie de getPraticiens : Taille : " + lesPraticiens.size());
                    if (!fait) {
                        fait = true;
                        Bundle paquet = new Bundle();
                        paquet.putParcelableArrayList("lesPraticiens", (ArrayList<? extends Parcelable>) lesPraticiens);
                        paquet.putParcelableArrayList("lesMotifs", (ArrayList<? extends Parcelable>) lesMotifs);
                        Intent intentionEnvoyer = new Intent(MenuActivity.this, SaisirActivity.class);
                        intentionEnvoyer.putExtras(paquet);
                        startActivity(intentionEnvoyer);
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
        fileRequetes.start();

        return praticiens;
    }

    public List<Motif> getMotifs() {
        final List<Motif> motifs = new ArrayList<Motif>();
        System.out.println("Appel de getMotifs");
        String url = String.format("http://%s:5000/motifs", Session.getSession().getIpServeur());
        Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>() {

            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        Motif unMotif = new Motif();
                        unMotif.setCode(response.getJSONObject(i).getString("mo_code"));
                        unMotif.setLibelle(response.getJSONObject(i).getString("mo_libelle"));

                        System.out.println("Nouveau Motif : " + unMotif.toString());
                        motifs.add(unMotif);
                    }
                    System.out.println("Sortie de getMotifs : Taille : " + lesMotifs.size());

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
        fileRequetes.start();

        return motifs;
    }

}

