package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static boolean result=false;
    private final int CALL_Perm=1;
    private ImageButton btnBrowser;
    private EditText edtchallenge1, edtchallenge2,url;
    private int intchallenge1,intchallenge2;
    private String urlName;


    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.i("LIFECYCLE ", getLocalClassName() + " : ici onActivityResult: ");

                    if (result.getResultCode()== 78){
                        Intent intent = result.getData();

                        if (intent!=null){
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]
                                    {Manifest.permission.CALL_PHONE}, CALL_Perm);
                            EditText txt = findViewById(R.id.editTxtPhone);
                            String number = txt.getText().toString();
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number)));
                        }
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtchallenge1 = (EditText) findViewById(R.id.edtchallenge1);
        edtchallenge2 = (EditText) findViewById(R.id.edtchallenge2);

        btnBrowser = (ImageButton) findViewById(R.id.btnBrowser);
        btnBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = findViewById(R.id.edtTxtUrl);
                urlName = url.getText().toString();
                if (!urlName.startsWith("http://") && !urlName.startsWith("https://")) {
                    urlName = "http://" + urlName;
                }

                Intent intent =new Intent(MainActivity.this,CheckActivity.class);
                intchallenge1 = Integer.parseInt(edtchallenge1.getText().toString());
                intchallenge2 = Integer.parseInt(edtchallenge2.getText().toString());
                intent.putExtra("challenge1",intchallenge1);
                intent.putExtra("challenge2",intchallenge2);
                setResult(1,intent);
                startActivityForResult(intent,1);
            };
        });
    };

    public void callNumber(View view){
        Intent intent = new Intent("login.ACTION");
        activityLauncher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CALL_Perm) {
            //the array is empty if not granted
            if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "GRANTED CALL",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 1:{
                if (resultCode==1){
                    int somme=intchallenge2 + intchallenge1;
                    ;
                    if (data.getIntExtra("somme",0)==somme) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlName)));
                    }
                }
            }
        }
    }
}