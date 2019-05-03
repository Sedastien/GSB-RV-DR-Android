package fr.gsb.rv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import fr.gsb.rv.entites.Visiteur;
import fr.gsb.rv.modeles.MenuActivity;
import fr.gsb.rv.technique.Session;
import fr.gsb.rv.technique.settingActivity;
import fr.gsb.rv.modeles.ModeleGSB;

public class MainActivity extends AppCompatActivity {

    public String matricule ;
    public String mdp ;
    TextView tvErreur ;
    EditText etMatricule ;
    EditText etMdp ;
    Button bSeConnecter ;
    Button bAnnuler ;
    Button Admin ;
    Visiteur vis ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvErreur = ( TextView ) findViewById( R.id.tvErreur );
        tvErreur.setText("") ;
        etMatricule = ( EditText ) findViewById(R.id.matricule) ;
        etMdp = ( EditText ) findViewById(R.id.motDePasse) ;
        Admin = (Button) findViewById(R.id.Admin) ;
    }


    @SuppressLint("ResourceAsColor")
    public void seConnecter(View vue){
        matricule = this.etMatricule.getText().toString() ;
        mdp = this.etMdp.getText().toString() ;

        String url = String.format("http://%s:5000/visiteurs/%s/%s",Session.getSession().getIpServeur(), matricule, mdp) ;

        Response.Listener<JSONObject> ecouteurReponse = new Response.Listener<JSONObject>() {

            public void onResponse(JSONObject response){
                try{
                    //Visiteur leVisiteur = new Visiteur(MainActivity.this.matricule, MainActivity.this.mdp, response.getString("nom"), response.getString("prenom")) ;
                    Visiteur leVisiteur = new Visiteur() ;
                    leVisiteur.setMatricule(response.getString("vis_matricule"));
                    leVisiteur.setNom(response.getString("vis_nom"));
                    leVisiteur.setPrenom(response.getString("vis_prenom"));
                    leVisiteur.setMdp(MainActivity.this.mdp);
                    Session.ouvrir(leVisiteur);

                    Log.i("APP-RV", "" + leVisiteur) ;
                    tvErreur.setText(" [Ok] Connexion réussie !");
                    tvErreur.setTextColor(Color.parseColor("#79CF41"));
                    String nomPrenom = leVisiteur.getPrenom() + " " + leVisiteur.getNom() ;
                    Bundle paquet = new Bundle() ;
                    paquet.putString("identite", nomPrenom);
                    paquet.putString("matricule", matricule);

                    Intent intentionEnvoyer = new Intent(MainActivity.this, MenuActivity.class) ;
                    intentionEnvoyer.putExtras(paquet) ;

                    startActivity(intentionEnvoyer);

                }
                catch(JSONException e){
                    Log.e("APP-RV", "Erreur JSON : " + e.getMessage()) ;
                }
            }
        } ;

        Response.ErrorListener ecouteurErreur = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                //connexion impossible : code 92, 166, 167
                //erreur login/mdp : code 27
                int codeErreur = Log.e("APP-RV", "ERREUR HTTP : " + error.getMessage()) ;

                switch (codeErreur){
                    case 166:{
                        tvErreur.setText(" [Erreur] : Serveur injoignable .\n [Attention] : Vérifiez votre connexion.");
                        break;
                    }
                    case 167:{
                        tvErreur.setText(" [Erreur] : Serveur injoignable .\n [Attention] : Vérifier que le serveur soit bien demarré.");
                        break;
                    }
                    case 92:{
                        tvErreur.setText(" [Erreur] : Serveur injoignable .");
                        break;
                    }
                    case 27:{
                        tvErreur.setText(" [Erreur] : Identifiant et/ou mot de passe incorrect(s) .");
                        break;
                    }
                    default:{
                        tvErreur.setText("[Erreur] : Erreur de source inconnue. (code d'erreur : "+Log.e("APP-RV", "ERREUR HTTP : " + error.getMessage())+")");
                        break;
                    }
                }
                tvErreur.setTextColor(Color.parseColor("#FF0000"));
            }
        };


        JsonObjectRequest requete = new JsonObjectRequest(Request.Method.GET,url,null,ecouteurReponse,ecouteurErreur ) ;

        RequestQueue fileRequetes = Volley.newRequestQueue(this) ;
        fileRequetes.add(requete) ;
    }

    public void annuler(View vue){
        this.etMatricule.getText().clear();
        this.etMdp.getText().clear();
        this.tvErreur.setText("");
    }

    public void parametres(View vue){
        Intent intentionEnvoyer = new Intent(MainActivity.this, settingActivity.class) ;
        startActivity(intentionEnvoyer);
    }

}