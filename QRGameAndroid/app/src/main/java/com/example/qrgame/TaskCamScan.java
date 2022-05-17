package com.example.qrgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class TaskCamScan extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView ScannerView;
    private static int cam = Camera.CameraInfo.CAMERA_FACING_BACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_cam_scan);

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
        ActivityCompat.requestPermissions(TaskCamScan.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
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

    @Override
    public void handleResult(Result result) {
        final String rawresult = result.getText();
        writeToFile("feladat.txt", rawresult);
        startActivity(new Intent(TaskCamScan.this, Task.class));
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