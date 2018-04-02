package com.example.lukemorris.flashscan;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Scanner extends AppCompatActivity {

    private SurfaceView cameraView;
    private TextView textView;
    private CameraSource cameraSource;
    private final int RequestCameraPermissionID = 1001;
    private String text;
    double backupP;
    private String backOfCard;
    private ArrayList<String> usersStores;
    private double price;
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;
    private final String SHARE = "PREFS";
    private DecimalFormat df = new DecimalFormat("####0.00");

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        backOfCard = "";


        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        textView = (TextView) findViewById(R.id.text_view);
        text = "";

        shared = getSharedPreferences(SHARE, getApplicationContext().MODE_PRIVATE);
        editor = shared.edit();

        //Array list to hold the stores that the user frequents
        usersStores = new ArrayList<String>();
        String[] userStoresArray = shared.getString("usersStores", "").split(",");
        for (int i = 0; i < userStoresArray.length - 1; i++) {
            usersStores.add(userStoresArray[i]);
        }

        if (hasCam()) {
            final TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
            if (!textRecognizer.isOperational()) {
            } else {
                cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                        .setFacing(CameraSource.CAMERA_FACING_BACK)
                        .setRequestedPreviewSize(1280, 1024)
                        .setRequestedFps(2.0f)
                        .setAutoFocusEnabled(true)
                        .build();

                cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {

                    @Override
                    public void surfaceCreated(SurfaceHolder surfaceHolder) {
                        try {
                            if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(Scanner.this,
                                        new String[]{android.Manifest.permission.CAMERA},
                                        RequestCameraPermissionID);
                                return;
                            }
                            cameraSource.start(cameraView.getHolder());
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    }

                    @Override
                    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                    }

                    @Override
                    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                        cameraSource.stop();

                    }

                });

                textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                    @Override
                    public void release() {

                    }

                    @Override
                    public void receiveDetections(Detector.Detections<TextBlock> detections) {

                        final SparseArray<TextBlock> items = detections.getDetectedItems();
                        if (items.size() != 0) {
                            textView.post(new Runnable() {
                                @Override
                                public void run() {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    for (int i = 0; i < items.size(); ++i) {
                                        TextBlock item = items.valueAt(i);
                                        stringBuilder.append(item.getValue());
                                        stringBuilder.append("\n");
                                    }
                                    text = stringBuilder.toString();
                                }
                            });
                        }
                    }
                });

            }
        } else {
            Intent intent = new Intent(this, addNewCardSelection.class);
            intent.putExtra("text", "No Camera found");
            startActivity(intent);
        }
    }


    public boolean hasCam() {
        return getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public void capture(View v){

        new AlertDialog.Builder(Scanner.this)
                .setTitle("Results")
                .setMessage("Output: " + (backOfCard.isEmpty()?"No Date Found": backOfCard) )
                .setPositiveButton("Confirm", listener)
                .setNegativeButton("Try Again", listener)
                .setNeutralButton("Edit", listener)
                .create()
                .show();


    }

    //Onclick for the dialog box.
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                final int BUTTON_NEGATIVE = -2;
                final int BUTTON_POSITIVE = -1;
                final int BUTTON_NEUTRAL = -3;

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case BUTTON_NEGATIVE:
                            dialog.dismiss();
                            break;
                        case BUTTON_POSITIVE:
                            Toast.makeText(Scanner.this, "New Card Successfully Added", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            finish();
                            break;
                        case BUTTON_NEUTRAL:
                            dialog.dismiss();
                            Intent back = new Intent(getApplicationContext(), Scanner.class);
                            startActivity(back);
                            finish();
                            break;
                    }
        }
    };

    }

