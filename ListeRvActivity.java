package fr.gsb.rv.modeles;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import fr.gsb.rv.MainActivity;
import fr.gsb.rv.R;
import fr.gsb.rv.entites.RapportVisite;
import fr.gsb.rv.entites.Visiteur;
import fr.gsb.rv.technique.Session;

public class ListeRvActivity extends AppCompatActivity {
    ListView Rapports ;
    TextView tvRapport;
    private List<RapportVisite> lesRapportsDeVisite = new ArrayList<RapportVisite>() ;
    private List<String> rapportsVisite = new ArrayList<String>() ;
    private String mois ;
    private String annee ;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_rv);
        Rapports = (ListView) findViewById(R.id.list);
        tvRapport = (TextView) findViewById(R.id.tvRapport) ;
        Bundle paquet = this.getIntent().getExtras();
        this.mois = paquet.getString("mois");
        this.annee = paquet.getString("annee");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, rapportsVisite);

        Rapports.setAdapter(arrayAdapter);

        String url = String.format("http://192.168.1.45:5000/rapports/%s/%s/%s",Session.getSession().getVisiteur().getMatricule(), mois, annee);
        Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>() {

            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        RapportVisite unRapport = new RapportVisite();
                        unRapport.setPraNom(response.getJSONObject(i).getString("pra_nom"));
                        unRapport.setRapNum(response.getJSONObject(i).getInt("rap_num"));
                        unRapport.setPraPrenom(response.getJSONObject(i).getString("pra_prenom"));
                        unRapport.setPraVille(response.getJSONObject(i).getString("pra_ville"));
                        unRapport.setRapBilan(response.getJSONObject(i).getString("rap_bilan"));
                        unRapport.setRapCoefConficance(response.getJSONObject(i).getInt("rap_coeff_confiance"));
                        unRapport.setRapDateRedaction(response.getJSONObject(i).getString("rap_date_redaction"));
                        unRapport.setRapDateVisite(response.getJSONObject(i).getString("rap_date_visite"));

                        Log.i("lvRapports", unRapport.toString()) ;
                        lesRapportsDeVisite.add(unRapport) ;
                        arrayAdapter.add(unRapport.toString());
                    }

                    Rapports.setOnItemClickListener(
                            new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                    //Bundle paquet = new Bundle() ;
                                    RapportVisite leRapport = lesRapportsDeVisite.get(position) ;
                                    Intent intentionEnvoyer = new Intent(ListeRvActivity.this, VisuRvActivity.class) ;
                                    intentionEnvoyer.putExtra("rapport", leRapport) ;
                                    startActivity(intentionEnvoyer);
                                }
                            }
                    );

                } catch (JSONException e) {
                    Log.e("APP-RV", "Erreur JSON : " + e.getMessage());
                }
            }
        };

        Response.ErrorListener ecouteurErreur = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //connexion impossible : code 92, 166, 167
                //erreur login/mdp : code 27
                int codeErreur = Log.e("APP-RV", "ERREUR HTTP : " + error.getMessage());

            }
        };

        JsonArrayRequest requete = new JsonArrayRequest(Request.Method.GET, url, null, ecouteurReponse, ecouteurErreur);

        RequestQueue fileRequetes = Volley.newRequestQueue(this);
        fileRequetes.add(requete);
    }

}
