package ma.ac.usmba.fpt.e_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ma.ac.usmba.fpt.e_learning.Model.Module;
import ma.ac.usmba.fpt.e_learning.Model.Semestre;

public class EtudiantSemestreActivity extends AppCompatActivity {
    ArrayList<Button> buttonModules_S1  = new ArrayList<Button>();
    ArrayList<Button> buttonModules_S2 = new ArrayList<Button>();
    ArrayList<Semestre> semesters=new ArrayList<Semestre>();
    ArrayList<Module> modS1;
    ArrayList<Module> modS2;

    boolean sem1=true;
    boolean sem2=true;
    LinearLayout linear1_s1,linear1_s2;
    Button semestre1,semestre2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant_semestre);

        semestre1=(Button)findViewById(R.id.addBtnM_S1);
        semestre2=(Button)findViewById(R.id.addBtnM_S2);
        setSemesters();
        semestre1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if(sem1==true) {
                    addBTN_S1();
                    if(sem2==false)
                        removeBTN_S2();
                }
                else
                    removeBTN_S1();
            }
        });
        semestre2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if(sem2==true)
                {
                    addBTN_S2();
                    if(sem1==false)
                        removeBTN_S1();
                }
                else
                    removeBTN_S2();
            }
        });


    }
    private void addBTN_S1() {
        linear1_s1 = findViewById(R.id.linear1_S1);
        modS1=semesters.get(0).getListof_module();
        for(int i=0;i<modS1.size();i++) {
            buttonModules_S1.add(new Button(this));
            buttonModules_S1.get(i).setHeight(150);
            buttonModules_S1.get(i).setWidth(700);
            buttonModules_S1.get(i).setBackgroundColor(Color.BLACK);
            buttonModules_S1.get(i).setTextColor(Color.WHITE);
            buttonModules_S1.get(i).setText(modS1.get(i).getNom_module());
            buttonModules_S1.get(i).setTag(i);
            linear1_s1.addView(buttonModules_S1.get(i));
            linear1_s1.addView(new TextView(this));
        }
        sem1=false;
    }
    private void addBTN_S2() {
        linear1_s2 = findViewById(R.id.linear1_S2);
        modS2=semesters.get(1).getListof_module();
        for(int i=0;i<modS2.size();i++) {
            buttonModules_S2.add(new Button(this));
            buttonModules_S2.get(i).setHeight(150);
            buttonModules_S2.get(i).setWidth(700);
            buttonModules_S2.get(i).setBackgroundColor(Color.DKGRAY);
            buttonModules_S2.get(i).setTextColor(Color.WHITE);
            buttonModules_S2.get(i).setText(modS2.get(i).getNom_module());
            buttonModules_S2.get(i).setTag(i);
            linear1_s2.addView(buttonModules_S2.get(i));
            linear1_s2.addView(new TextView(this));
        }
        sem2=false;
    }
    private void removeBTN_S1(){
        linear1_s1.removeAllViews();
        sem1=true;
    }
    private void removeBTN_S2(){
        linear1_s2.removeAllViews();
        sem2=true;
    }
    private void setSemesters(){
        semesters.add(new Semestre("Semestre 4"));
        semesters.add(new Semestre("Semestre 6"));
        Module m1=new Module("Module 1");
        Module m2=new Module("Module 2");
        Module m3=new Module("Module 3");
        Module m4=new Module("Module 3");
        Module m5=new Module("Module 5");
        Module m6=new Module("Module 6");
        semesters.get(0).add_module(m1);
        semesters.get(0).add_module(m2);
        semesters.get(1).add_module(m3);
        semesters.get(1).add_module(m4);
        semesters.get(1).add_module(m5);
        semesters.get(1).add_module(m6);
        semestre1.setText(semesters.get(0).get_semestre());
        semestre2.setText(semesters.get(1).get_semestre());
    }



}
