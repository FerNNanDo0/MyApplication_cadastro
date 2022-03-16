package com.usuario.cadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity  {

    public FirebaseAuth mAuth;
    private ListView listMessage;
    public  Intent intentLog;

    private final String[] contatos = { "1 contato","2 contato","3 contato",
                                  "4 contato","5 contato","6 contato",
                                  "7 contato","8 contato","9 contato" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    /*  message = findViewById(R.id.message);
        send = findViewById(R.id.btn_Send);*/
        intentLog = new Intent(this, Log_Activity.class);
        mAuth = FirebaseAuth.getInstance();

        listMessage = findViewById(R.id.listView);

        listagem();
    }

    public void listagem() {
       ArrayAdapter<String> adapter = new ArrayAdapter<>(
               getApplicationContext(),
               android.R.layout.simple_list_item_1,
               android.R.id.text1,
               contatos
       );
       listMessage.setAdapter(adapter);
       listMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               String item = listMessage.getItemAtPosition(i).toString();
               Toast.makeText(MainActivity.this, item, Toast.LENGTH_LONG).show();
           }
       });
    }


}