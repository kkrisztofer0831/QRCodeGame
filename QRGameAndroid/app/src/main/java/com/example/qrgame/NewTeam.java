package com.example.qrgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewTeam extends AppCompatActivity {

    EditText nameEt;
    Button createBtn;
    csapat csapat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_team);
        nameEt = findViewById(R.id.nameEt);
        createBtn = findViewById(R.id.createBtn);

        csapat = new csapat("A_epulet", 0);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("csapatok");

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.child(nameEt.getText().toString()).setValue(csapat);
                Toast.makeText(NewTeam.this, "Létrejött az új csapat", Toast.LENGTH_LONG).show();
                startActivity(new Intent(NewTeam.this, MainActivity.class));
            }
        });

    }

}