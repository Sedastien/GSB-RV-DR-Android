package fr.gsb.rv.modeles;

import android.content.Intent;
import android.os.Bundle;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import fr.gsb.rv.MainActivity;
import fr.gsb.rv.R;
import fr.gsb.rv.entites.Visiteur;
import fr.gsb.rv.modeles.ModeleGSB;

@SuppressLint("Registered")
public class MenuActivity extends AppCompatActivity {
    TextView Identite ;
    Button ConsulterRapport ;
    Button SaisirRapport ;
    private String identite ;
    private String matricule ;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Identite = ( TextView ) findViewById(R.id.tvIdentite);

        Bundle paquet = this.getIntent().getExtras() ;
        this.identite = paquet.getString("identite") ;
        this.matricule = paquet.getString("matricule") ;

        Identite.setText(identite);
    }

    public void consulter(View vue){
        Bundle paquet = new Bundle() ;
        paquet.putString("identite", this.identite);
        paquet.putString("matricule", this.matricule);

        Intent intentionEnvoyer = new Intent(MenuActivity.this, consulterActivity.class) ;
        intentionEnvoyer.putExtras(paquet) ;

        startActivity(intentionEnvoyer);
    }

    public void saisir(View vue){
        Intent intentionEnvoyer = new Intent(MenuActivity.this, SaisirActivity.class) ;
        startActivity(intentionEnvoyer);
    }

}
