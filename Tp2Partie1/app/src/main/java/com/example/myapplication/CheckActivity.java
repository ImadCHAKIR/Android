package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CheckActivity extends AppCompatActivity {

    private TextView txtchallenge1,txtchallenge2;
    private Button btnok,btncancel;
    private EditText edtsomme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        txtchallenge1 = (TextView) findViewById(R.id.txtchallenge1);
        txtchallenge1.setText(Integer.toString(getIntent().getIntExtra("challenge1",0)));
        txtchallenge2 = (TextView) findViewById(R.id.txtchallenge2);
        txtchallenge2.setText(Integer.toString(getIntent().getIntExtra("challenge2",0)));
        edtsomme = (EditText) findViewById(R.id.edtsomme);

        btnok = (Button) findViewById(R.id.btnok);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(1,intent);
                intent.putExtra("somme",Integer.parseInt(edtsomme.getText().toString()));
                finish();
            }
        });

        btncancel = (Button) findViewById(R.id.btncancel);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }
}