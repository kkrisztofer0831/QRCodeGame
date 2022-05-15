
package com.example.qrgame;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class CamScan extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView ScannerView;
    private static int cam = Camera.CameraInfo.CAMERA_FACING_BACK;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("csapatok");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam_scan);

        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);

        int currentapiVersion = Build.VERSION.SDK_INT;

        if(currentapiVersion >= Build.VERSION_CODES.M){
            if(checkPermission()){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }
    }

    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(CamScan.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionResult(int requestCode, String permission, int[] grantResult){
        switch (requestCode) {
            case REQUEST_CAMERA:
                if(grantResult.length > 0){
                    boolean cameraAccept = grantResult[0] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccept){
                        Toast.makeText(this, "Permission Granted By User", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(this, "Permission Not Granted By User", Toast.LENGTH_LONG).show();
                        if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                            showMessageOKCancel("You need to grant the permission", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                        requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                                    }
                                }
                            });
                            return;
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        int currentapiVersion = Build.VERSION.SDK_INT;
        if(currentapiVersion >= Build.VERSION_CODES.M){
            if(ScannerView == null){
                ScannerView = new ZXingScannerView(this);
                setContentView(ScannerView);
            }
            ScannerView.setResultHandler(this);
            ScannerView.startCamera();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ScannerView.stopCamera();
        ScannerView = null;

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener oklistener){
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", oklistener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(Result result) {
        final String rawresult = result.getText();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot sp : snapshot.getChildren()){
                            if(rawresult.equals(sp.getKey())){
                                writeToFile("csapat.txt", rawresult);
                                startActivity(new Intent(CamScan.this, GameDashboard.class));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDestroy();
            }
        });

        builder.setMessage(rawresult);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult res = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(res!=null){
            if(res.getContents() == null){
                Toast.makeText(this, "Kiléptél a szkennelőből.", Toast.LENGTH_LONG).show();
            }
        } else {

        }

        super.onActivityResult(requestCode, resultCode, data);
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
