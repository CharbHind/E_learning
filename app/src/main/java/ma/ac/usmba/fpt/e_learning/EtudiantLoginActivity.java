package ma.ac.usmba.fpt.e_learning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class EtudiantLoginActivity extends AppCompatActivity {
    /*private static final Pattern PASSWORD_PATTERN =
            //Pattern.compile("[A-Z]{1,2}" + "[0-9]*");//
    Pattern.compile("[A-Z]{1,2}\\-[0-9]*");*/
    private EditText cne;
    private EditText cin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant_login);

        cne = findViewById(R.id.cne);
        cin = findViewById(R.id.cin);
    }

    public void goback(View view) {
        onBackPressed();
    }
    private boolean validatecne() {
        String cnee= cne.getText().toString().trim();

        if (cnee.isEmpty()) {
            cne.setError("remplir le vide");
            return false;
        } else if (cnee.matches("([A-Z]{1})"+"[0-9]{9}?")) {
            cne.setError("essayer d'ecrire: S*******");
            return false;
        } else {
            cne.setError(null);
            return true;
        }
    }
    private boolean validatecin() {
        String cinn= cin.getText().toString().trim();

        if (cinn.isEmpty()) {
            cin.setError("**********");
            return false;
        } else if (cinn.matches("([A-Z]{1,2})?"+"[0-9]*"))  {
            cin.setError("********");
            return false;
        } else {
            cin.setError(null);
            return true;
        }
    }


    public void onClickValider(View view) {
        if ((!validatecne())||(!validatecin())){

        String input;
        input = "cne: " + cne.getText().toString() + "\n";
        input += "cin: " + cin.getText().toString();
        Toast.makeText(EtudiantLoginActivity.this, input, Toast.LENGTH_SHORT).show();
    } else{
        Intent intent = new Intent(EtudiantLoginActivity.this, EtudiantAccueilActivity.class);
        startActivity(intent);}
    }
}
