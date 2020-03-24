package ma.ac.usmba.fpt.e_learning;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import ma.ac.usmba.fpt.e_learning.Controller.FileUtils;
import ma.ac.usmba.fpt.e_learning.Controller.QuizAdapter;
import ma.ac.usmba.fpt.e_learning.Model.Quiz;

public class ProfCreerSeanceActivity extends AppCompatActivity {
    final String QUIZ = "Quiz";
    ArrayList<Quiz> quizzes = new ArrayList<>();
    Button button_valider,creer_quiz;
    RecyclerView recyclerView;
    QuizAdapter quizAdapter;
    ImageView attach_file;
    TextView file_path;
    final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE};
    final int FILE_CHOOSER = 50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_creer_seance);
        //Necessary Declarations
        creer_quiz = (Button) findViewById(R.id.button_creer_quiz);
        button_valider = (Button) findViewById(R.id.button_valider);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        attach_file = (ImageView) findViewById(R.id.img_view_attach_file);
        file_path = (TextView) findViewById(R.id.txt_view_file_path);
        //Move to the QuizPopUp
        creer_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfCreerSeanceActivity.this,QuizPopUp.class);
                Gson gson = new Gson();
                String array_quizzes = gson.toJson(quizzes);
                intent.putExtra(QUIZ,array_quizzes);
                startActivity(intent);
            }
        });
        //Populate the recycler view
        update_quizzes();
        quizAdapter = new QuizAdapter(this,quizzes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(quizAdapter);
        //Check the quizzes content
        button_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_quizzes();
            }
        });
        //Display filechooser
        attach_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasPermissions(ProfCreerSeanceActivity.this,PERMISSIONS)){
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(Intent.createChooser(intent,"Choisir un fichier"),FILE_CHOOSER);
                }else{
                    ActivityCompat.requestPermissions(ProfCreerSeanceActivity.this, PERMISSIONS, FILE_CHOOSER);
                }
            }
        });

    }

    //Display the content of the quizzes
    public void show_quizzes(){
        if(!quizzes.isEmpty()){
            for(Quiz quiz : quizzes){
                System.out.println(quiz.getQuestion());
                for(Map.Entry answers : quiz.getReponses().entrySet())
                    System.out.println("Key : " + answers.getKey() + " Value : " + answers.getValue());
            }
        }else{
            System.out.println("QUIZ IS EMPTY");
        }
        System.out.println(quizzes.size());
        System.out.println(quizAdapter.getItemCount());
    }
    //Update the quizzes arrayList
    public void update_quizzes(){
        if(getIntent().getSerializableExtra(QUIZ) != null)
        quizzes = (ArrayList<Quiz>) getIntent().getSerializableExtra(QUIZ);
    }
    //Ask the user for permission to read and write from the internale storage.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case FILE_CHOOSER: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                            ProfCreerSeanceActivity.this,
                            "Permission d'accées au stockage accepté",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                            ProfCreerSeanceActivity.this,
                            "Permission d'accées au stockage refusé",
                            Toast.LENGTH_SHORT
                    ).show();
                }

                return;
            }
        }
    }
    //Check if the user has given the application the access to Strorage.
    public  boolean hasPermissions(Context context, String  permissions[]) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    //

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case FILE_CHOOSER:
                String selected_paths = "";
                if(resultCode == RESULT_OK){
                    //Check if the user select multiple files
                    if(data.getClipData() != null){
                        int files_count = data.getClipData().getItemCount();
                            int count = 0;
                            String absolute_paths [] = new String[files_count];
                            while(count < files_count){
                                absolute_paths[count] = FileUtils.getPath(this,data.getClipData().getItemAt(count).getUri());
                                selected_paths += count+")" + FileUtils.getFileName(absolute_paths[count]);
                                count++;
                            }
                            file_path.setText(selected_paths);
                        }else if(data.getData() != null){//The user select one file
                        selected_paths += FileUtils.getFileName(FileUtils.getPath(this,data.getData()));
                        file_path.setText(selected_paths);
                    }
                    Toast.makeText(this, "Selected File " + selected_paths, Toast.LENGTH_SHORT).show();
                    }
                break;
        }
    }
}
