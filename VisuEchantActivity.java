package fr.gsb.rv.modeles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import fr.gsb.rv.R;
import fr.gsb.rv.entites.Echantillon;
import fr.gsb.rv.entites.RapportVisite;
import fr.gsb.rv.technique.Session;

public class VisuEchantActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private int numRapport ;
    private List<Echantillon> lesEchantillons = new ArrayList<Echantillon>() ;
    private List<String> echantillons = new ArrayList<String>() ;
    private ListView listeEchantillons ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visu_echant);
        listeEchantillons = (ListView) findViewById(R.id.list) ;
        final Intent intent = getIntent();
        if (intent != null){
            Bundle paquet = this.getIntent().getExtras() ;
            this.numRapport = paquet.getInt("numRapport") ;
            Log.i("num rapport", Integer.toString(numRapport)) ;
            if (numRapport > 0){
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_1, echantillons);

                listeEchantillons.setAdapter(arrayAdapter);

                String url = String.format("http://%s:5000/rapports/echantillons/%s/%s", Session.getSession().getIpServeur(),
                        Session.getSession().getVisiteur().getMatricule(), Integer.toString(numRapport));

                Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>() {

                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                Echantillon unEchantillon = new Echantillon();
                                unEchantillon.setNom(response.getJSONObject(i).getString("med_nomcommercial"));
                                unEchantillon.setQuantite(response.getJSONObject(i).getInt("off_quantite"));

                                Log.i("lvRapports", unEchantillon.toString()) ;
                                lesEchantillons.add(unEchantillon) ;
                                arrayAdapter.add(unEchantillon.toString());
                            }

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
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
