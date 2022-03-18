package com.example.qrgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JoinTeam extends AppCompatActivity {

    TextView teamNum;
    ListView teamList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_team);
        teamNum = findViewById(R.id.teamNum);
        teamList = findViewById(R.id.teamList);
        int count = 0;

        final ArrayList<String> teams = new ArrayList<String>(){};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teams);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("csapatok");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sp : snapshot.getChildren()){
                    teams.add(sp.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter.notifyDataSetChanged();
        teamList.setAdapter(adapter);

        for(int i = 0; i < teams.size(); i++){
            count++;
        }

        teamNum.setText("A meglévő csapatok száma: " + count);


    }
}
