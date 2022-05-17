package com.example.qrgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button joinTeam, newTeam;
    ImageView kep, pe_logo;
    TextView bejelentkezes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinTeam = findViewById(R.id.joinTeam);
        newTeam = findViewById(R.id.newTeam);
        kep = findViewById(R.id.jatek_neve_kep);
        Animation am= AnimationUtils.loadAnimation(this,R.anim.backdrop_animation);
        kep.startAnimation(am);
        bejelentkezes = findViewById(R.id.bejelentkezesTv);
        Animation am2 = AnimationUtils.loadAnimation(this, R.anim.bekuszas);
        bejelentkezes.startAnimation(am2);

        Animation am3= AnimationUtils.loadAnimation(this,R.anim.float_in);
        //joinTeam.startAnimation(am3);
        //newTeam.startAnimation(am3);
        //pe_logo.startAnimation(am3);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int startColor = getWindow().getStatusBarColor();
            int endColor = ContextCompat.getColor(this, R.color.black);
            ObjectAnimator.ofArgb(getWindow(), "statusBarColor", startColor, endColor).start();
        }

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