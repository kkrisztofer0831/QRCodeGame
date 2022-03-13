package com.example.qrgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinTeam extends AppCompatActivity {

    DatabaseReference reference, myRef;
    ListView teamList;
    TextView teamNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_team);
        teamList = findViewById(R.id.teamList);
        teamNum = findViewById(R.id.teamNum);

        String[] teams = new String[]{};
        final List<String> listOfTeam = new ArrayList<String>(Arrays.asList(teams));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfTeam);


        teamList.setAdapter(arrayAdapter);


        reference = FirebaseDatabase.getInstance().getReference("csapatok");

        reference.child("csapat_id_1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        Toast.makeText(JoinTeam.this, "Read is succesful.", Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        listOfTeam.add(String.valueOf(dataSnapshot.child("csapatnev").getValue()));
                        arrayAdapter.notifyDataSetChanged();

                    }else {
                        Toast.makeText(JoinTeam.this, "Team Doesn't Exist.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(JoinTeam.this, "Failed to read", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}