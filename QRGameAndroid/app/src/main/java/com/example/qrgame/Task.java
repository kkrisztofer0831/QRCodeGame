package com.example.qrgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class Task extends AppCompatActivity {

    TextView leirasTv;
    TextView cimTv;
    EditText answerEt;
    Button sendBtn;
    Integer pontszam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        leirasTv = findViewById(R.id.leirasTv);
        cimTv = findViewById(R.id.cimTv);
        answerEt = findViewById(R.id.answerEt);
        sendBtn = findViewById(R.id.sentBtn);

        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, "feladat.txt");
        FileInputStream is = null;
        try {
            is = new FileInputStream(readFrom);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String feladat = reader.readLine();
        String allomas = reader.readLine();
        String cim = reader.readLine();
        String kerdes = reader.readLine();
        Log.d("feladat", feladat);

        cimTv.setText(cim);
        leirasTv.setText(kerdes);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("csapatok");
        DatabaseReference teamRef = dbRef.child(readFromFile("csapat.txt"));



        csapat csapat = new csapat(allomas, 0);

        teamRef.setValue(csapat);

        DatabaseReference dbRefTask = FirebaseDatabase.getInstance().getReference("feladatok");
        DatabaseReference taskRef = dbRefTask.child(feladat);


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String answer = snapshot.child("valasz").getValue().toString();
                        if(answer.equals(answerEt.getText().toString())){
                            teamRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    pontszam = Integer.parseInt(snapshot.child("pontszam").getValue().toString());
                                    Integer novelt = Integer.parseInt(readFromFile("pontszam.txt")) + 1;
                                    csapat cs = new csapat(allomas, novelt);
                                    teamRef.setValue(cs);
                                    Toast.makeText(Task.this, "Helyes válasz.", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Task.this, GameDashboard.class));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            Toast.makeText(Task.this, "A válasz helytelen.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String readFromFile(String fileName){
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, fileName);
        byte[] content = new byte[(int) readFrom.length()];
        try {
            FileInputStream stream = new FileInputStream(readFrom);
            stream.read(content);
            return new String(content);
        } catch (Exception e) {
            Log.d("HIBA", e.toString());
            return e.toString();
        }
    }
}


