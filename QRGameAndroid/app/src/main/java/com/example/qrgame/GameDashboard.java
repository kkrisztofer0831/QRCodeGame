package com.example.qrgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GameDashboard extends AppCompatActivity {

    TextView csapatNev;
    TextView pontszam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_dashboard);

        csapatNev = findViewById(R.id.csapatNev);
        pontszam = findViewById(R.id.pontszam);

        csapatNev.setText(readFromFile("csapat.txt"));

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("csapatok");
        DatabaseReference teamRef = dbRef.child(readFromFile("csapat.txt"));

        teamRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String pointNum = snapshot.child("Pontszam").getValue().toString();
                pontszam.setText("Aktuális pontszám: " + pointNum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



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

    public void exitButton(View v){
        startActivity(new Intent(GameDashboard.this, MainActivity.class));
    }
}