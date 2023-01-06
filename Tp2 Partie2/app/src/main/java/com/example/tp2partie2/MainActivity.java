package com.example.tp2partie2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnlistcontact,btndetailcontact,btncall;
    private String uri= "content://contacts/people";
    private TextView txtcontact,txtphone,txtname,txtid;

    private String contact;
    private final int Perm_CTC=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtcontact = findViewById(R.id.txtcontact);
        txtid = findViewById(R.id.txtID);
        txtphone = findViewById(R.id.txtphone);
        txtname = findViewById(R.id.txtName);

        btnlistcontact = findViewById(R.id.btnlistcontact);
        btnlistcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Intent.ACTION_PICK, Uri.parse(uri));
                startActivityForResult(intent,1);
            }
        });

        btndetailcontact = findViewById(R.id.btndetailcontact);
        btndetailcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]
                        {Manifest.permission.READ_CONTACTS}, Perm_CTC);

            }
        });

        btncall = findViewById(R.id.btncall);
        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+contact)));
            }
        });

        txtcontact = findViewById(R.id.txtcontact);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            contact = data.getDataString();
            btndetailcontact.setEnabled(true);
            txtcontact.setText(contact);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Perm_CTC) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String contactPhone;
                String contactName;
                String contactId;

                Cursor cursor = getContentResolver().query(Uri.parse(contact), null, null, null, null);
                if (cursor.moveToNext()){
                    do {
                        contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    }while(cursor.moveToNext());
                    Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?"+" and " +
                                    ContactsContract.CommonDataKinds.Phone.TYPE + "=" +
                                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,
                            new String[]{contactId},
                            null);

                    if (cursorPhone.moveToNext()) {
                        contactPhone = cursorPhone.getString(0);

                        txtphone.setText("Phone :" +contactPhone);
                        contact = contactPhone;
                    }
                    Log.i("TAG", "onRequestPermissionsResult: ");
                    txtid.setText("Id: "+ contactId);
                    txtname.setText("Name: "+ contactName);
                }

                //Toast.makeText(this, "GRANTED CALL",
                Toast.makeText(MainActivity.this,"GRANTED CALL",Toast.LENGTH_SHORT).show();

                btncall.setEnabled(true);
            }
        }
    }

}