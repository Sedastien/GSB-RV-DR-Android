package fr.gsb.rv.modeles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.gsb.rv.R;
import fr.gsb.rv.entites.RapportVisite;

public class VisuRvActivity extends AppCompatActivity {
    private int numRapport ;
    private TextView tvRapport ;
    private Button echantillonsOfferts ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visu_rv);
        tvRapport = (TextView) findViewById(R.id.tvLeRapport) ;
        echantillonsOfferts = (Button) findViewById(R.id.echantillonsOfferts) ;
        final Intent intent = getIntent();
        if (intent != null){
            final RapportVisite rapport = intent.getParcelableExtra("rapport");
            if (rapport != null){
                tvRapport.setText(rapport.Presenter());
                echantillonsOfferts.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle paquet = new Bundle() ;
                                Intent intentionEnvoyer = new Intent(VisuRvActivity.this, VisuEchantActivity.class) ;
                                Log.i("num rapport", Integer.toString(rapport.getrapNum())) ;
                                paquet.putInt("numRapport", rapport.getrapNum());
                                intentionEnvoyer.putExtras(paquet) ;
                                startActivity(intentionEnvoyer);
                            }
                        }
                );
            }
        }
    }
}
