package com.example.qrgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class JoinTeam extends AppCompatActivity {

    TextView csapatokSzama;
    ListView csapatokLista;
    ArrayList<String> csapatok;
    ArrayAdapter<String> csapatokAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_team);
        csapatokSzama = findViewById(R.id.csapatokSzama);
        csapatokLista = findViewById(R.id.csapatokLista);

        csapatok = new ArrayList<>();

        csapatokAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, csapatok);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("csapatok");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sp : snapshot.getChildren()){
                    csapatok.add(sp.getKey());
                    csapatokAdapter.notifyDataSetChanged();

                }
                csapatokSzama.setText("A meglévő csapatok száma: " + csapatok.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        csapatokLista.setAdapter(csapatokAdapter);

        csapatokLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = csapatokLista.getItemAtPosition(i).toString();
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot sp : snapshot.getChildren()){
                            if(s.equals(sp.getKey())){
                                writeToFile("csapat.txt", s);
                                startActivity(new Intent(JoinTeam.this, GameDashboard.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    public void writeToFile(String filename, String content){
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, filename));
            writer.write(content.getBytes());
            Log.d("Hely", path.toString());
        } catch (FileNotFoundException e) {
            Log.d("HIBA", e.toString());
        } catch (IOException e) {
            Log.d("HIBA", e.toString());
        }
    }
}
