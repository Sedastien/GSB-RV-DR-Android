package fr.gsb.rv.modeles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class consulterActivity extends AppCompatActivity {
    private static final String [] lesMois = {
            "Janvier", "Février", "Mars", "Avril",
            "Mai", "Juin", "Juillet", "Août",
            "Septembre", "Octobre", "Novembre", "Décembre"} ;
    private static final String [] lesAnnees = {
            "2010", "2011", "2012", "2013", "2014", "2015",
            "2016", "2017", "2018", "2019"
    } ;

    Spinner spMois ;
    Spinner spAnnee ;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_consulter);

        spMois = (Spinner) findViewById(R.id.spMois) ;
        spAnnee = (Spinner) findViewById(R.id.spAnnee);

        ArrayAdapter<String> aaMois = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                lesMois
        ) ;

        ArrayAdapter<String> aaAnnee = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                lesAnnees
        ) ;

        aaMois.setDropDownViewResource(
                android.R.layout.simple_spinner_item
        );

        aaAnnee.setDropDownViewResource(
                android.R.layout.simple_spinner_item
        );

        spMois.setAdapter(aaMois);
        spAnnee.setAdapter(aaAnnee);
    }


    public void voirSelectionMois(View vue) {
        Bundle paquet = new Bundle() ;
        String leMois = spMois.getSelectedItem().toString();
        int rangMois = moisToNumber(leMois)+1 ;
        leMois = Integer.toString(rangMois) ;
        paquet.putString("mois", leMois);
        paquet.putString("annee", spAnnee.getSelectedItem().toString());

        Intent intentionEnvoyer = new Intent(consulterActivity.this, ListeRvActivity.class) ;
        intentionEnvoyer.putExtras(paquet) ;

        startActivity(intentionEnvoyer);
    }

    public void voirSelectionAnnee(View vue){

    }

    public void annuler(View vue){
        consulterActivity.this.finish();
    }

    public int moisToNumber(String leMois){
        for(int i=0;i<lesMois.length;i++){
            if(leMois.equals(lesMois[i])){
                return i;
            }
        }
        return 0;
    }
}