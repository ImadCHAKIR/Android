package com.example.tp4_player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MyPlayListActivity extends AppCompatActivity {

    private Button btnstoplist;
    private ListView listmusic;
    private String[] list = {"symphony_war",""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_play_list);

        btnstoplist = findViewById(R.id.btnstoplist);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyPlayListActivity.this, R.layout.textview,list);

        listmusic = (ListView) findViewById(R.id.listmusic);
        listmusic.setAdapter(adapter);
        listmusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                String p = parent.getItemAtPosition(pos).toString();
                registerForContextMenu(listmusic);


            }
        });

        btnstoplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Mettez Votre Titre ici");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String title = listmusic.getItemAtPosition(info.position).toString();

        switch (item.getItemId()) {
            case R.id.stop:
                Log.i("TAG", "onContextItemSelected: "+title);
                Intent intent = new Intent(MyPlayListActivity.this,ServiceMusique.class);
                intent.putExtra("music",title);
                stopService(intent);
                return true;
            case R.id.play:
                intent = new Intent(MyPlayListActivity.this,ServiceMusique.class);
                intent.putExtra("music",title);
                startService(intent);

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}