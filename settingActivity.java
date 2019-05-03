package fr.gsb.rv.technique;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.gsb.rv.R;
import fr.gsb.rv.entites.Visiteur;

import android.view.View;

public class settingActivity extends AppCompatActivity {

    /**
     *  ---- LISEZ MOI ----
     *
     * CLASSE settingActivity
     * SERS JUSTE A REGLER CERTAINS PARAMETRES
     * COMME L'IP DU SERVEUR
     * NE FAIS PAS PARTI DU CONTEXTE
     *
     * --------------------
     * */

    private TextView messageAcceuil ;
    private EditText champIp ;
    private Button ok ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        messageAcceuil = (TextView) findViewById(R.id.messageAccueil) ;
        champIp = (EditText) findViewById(R.id.champIp) ;
        ok = (Button) findViewById(R.id.ok) ;
        Visiteur admin = new Visiteur() ;
        Session.ouvrir(admin);
        messageAcceuil.setText("Parametres de RV-Visiteur");
        champIp.setText(Session.getSession().getIpServeur());
    }

    public void enregistrer(View vue){
        Session.getSession().setIpServeur(champIp.getText().toString());
        settingActivity.this.finish();
    }
}
