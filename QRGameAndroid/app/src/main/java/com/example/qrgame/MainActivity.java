package com.example.qrgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    TextView csapatnev, eltoltottido, pontszam, feladatcim, feladatszoveg, feladatpontszam,
    allomasszam, csapat_id, idopont;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        csapatnev = findViewById(R.id.csapatnev);
        eltoltottido = findViewById(R.id.eltoltottido);
        pontszam = findViewById(R.id.pontszam);
        feladatcim = findViewById(R.id.feladatcim);
        feladatszoveg = findViewById(R.id.feladatszoveg);
        feladatpontszam = findViewById(R.id.feladatpontszam);
        allomasszam = findViewById(R.id.allomasszam);
        csapat_id = findViewById(R.id.csapat_id);
        idopont = findViewById(R.id.idopont);

        reference = FirebaseDatabase.getInstance().getReference("csapatok");
        reference.child("csapat_id_1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        Toast.makeText(MainActivity.this, "Read is succesful.", Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        csapatnev.setText(String.valueOf(dataSnapshot.child("csapatnev").getValue()));
                        eltoltottido.setText(String.valueOf(dataSnapshot.child("eltoltottido").getValue()));
                        pontszam.setText(String.valueOf(dataSnapshot.child("pontszam").getValue()));
                    }else {
                        Toast.makeText(MainActivity.this, "Team Doesn't Exist.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to read", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("feladatok");
        reference.child("feladat_1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        Toast.makeText(MainActivity.this, "Read is succesful.", Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        feladatcim.setText(String.valueOf(dataSnapshot.child("feladatcim").getValue()));
                        feladatszoveg.setText(String.valueOf(dataSnapshot.child("feladatszoveg").getValue()));
                        feladatpontszam.setText(String.valueOf(dataSnapshot.child("pontszam").getValue()));
                    }else {
                        Toast.makeText(MainActivity.this, "Task Doesn't Exist.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to read", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("logok");
        reference.child("log_id_1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        Toast.makeText(MainActivity.this, "Read is succesful.", Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        allomasszam.setText(String.valueOf(dataSnapshot.child("allomasszam").getValue()));
                        csapat_id.setText(String.valueOf(dataSnapshot.child("csapat_id").getValue()));
                        idopont.setText(String.valueOf(dataSnapshot.child("idopont").getValue()));
                    }else {
                        Toast.makeText(MainActivity.this, "Log Doesn't Exist.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to read", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}