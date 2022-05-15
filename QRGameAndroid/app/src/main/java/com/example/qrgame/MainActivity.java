package com.example.qrgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button joinTeam, newTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinTeam = findViewById(R.id.joinTeam);
        newTeam = findViewById(R.id.newTeam);
    }

    public void joinTeamClick(View view){
        showDialog();
    }

    private void showDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this)
                .setTitle("CSATLAKOZÁS")
                .setMessage("Hogyan szeretnél csatlakozni a meglévő csapatokhoz?")
                .setPositiveButton("QR kód segítségével", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MainActivity.this, CamScan.class));
                    }
                })
                .setNegativeButton("Csapatok kilistázása", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MainActivity.this, JoinTeam.class));
                    }
                });
        alert.show();
    };

}