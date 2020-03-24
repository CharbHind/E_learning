package ma.ac.usmba.fpt.e_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EtudiantAccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant_accueil);
    }

    public void onClickAssister(View view) {
        Intent intent = new Intent(EtudiantAccueilActivity.this, EtudiantSeanceActivity.class);
        startActivity(intent);
    }

    public void onClickResources(View view) {
        Intent intent = new Intent(EtudiantAccueilActivity.this, EtudiantSemestreActivity.class);
        startActivity(intent);
    }
    public void back(View view) {
        onBackPressed();
    }

}
