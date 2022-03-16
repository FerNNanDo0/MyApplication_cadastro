package com.usuario.cadastro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Cadastro_Activity extends MainActivity {

    private static final int READ_SOCKET = 1;
    public Button btnCadastro;
    private EditText edt_email_cadastro;
    private EditText edt_senha_cadastro;
    private EditText edt_confirm;
    public FirebaseAuth mAuth;
    public FirebaseUser user;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        //return super.onOptionsItemSelected(item);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, READ_SOCKET);
        }

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        btnCadastro = findViewById(R.id.btnCadastrar);
        edt_email_cadastro = findViewById(R.id.edt_email_cadastro);
        edt_senha_cadastro = findViewById(R.id.edt_senha_cadastro);
        edt_confirm = findViewById(R.id.edt_confirm_senha);

        Toast toast = Toast.makeText(this, "Cadastrado No App", Toast.LENGTH_LONG);
        Toast toast1 = Toast.makeText(this, "preencha os dados acima", Toast.LENGTH_LONG);
        Toast toast2 = Toast.makeText(this, "Esse E-mail ja existe no sistema", Toast.LENGTH_LONG);
        Toast toastInfoSenha = Toast.makeText(this, "As senhas nao conferem", Toast.LENGTH_LONG);
        Toast InfoEmail = Toast.makeText(this, "Digite um E-MAIL que contenha os caracteres: > @exemplo.com <"
                , Toast.LENGTH_LONG);

        // intent da tela de loguin
        Intent intentLOG = new Intent(this, Log_Activity.class);

        // Function click do botao cadastrar usuarios
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String email_cad = edt_email_cadastro.getText().toString().trim();
                String senha_cad = edt_senha_cadastro.getText().toString().trim();
                String senha_confirm = edt_confirm.getText().toString().trim();

                //FirebaseUser usersVerif = FirebaseAuth.getInstance().getCurrentUser();

                if (email_cad.isEmpty() | senha_cad.isEmpty() | senha_confirm.isEmpty()) {
                    toast1.show();

                } else if (!senha_cad.equals(senha_confirm)) {
                    edt_confirm.setText(null);
                    toastInfoSenha.show();

                } else if (!email_cad.contains("@") || !email_cad.contains(".com")) {
                    edt_email_cadastro.setText("");
                    edt_senha_cadastro.setText(null);
                    edt_confirm.setText(null);
                    InfoEmail.show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email_cad, senha_cad)
                            .addOnCompleteListener(Cadastro_Activity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(intentLOG);
                                        toast.show();
                                    } else {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro_Activity.this);
                                        builder.setMessage("Esse E-mail ja existe no sistema")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id){
                                                        // FIRE ZE MISSILES!
                                                    }
                                                });
                                        // Create the AlertDialog object and return it
                                        builder.create();
                                        builder.show();
                                        edt_email_cadastro.setText("");
                                        edt_senha_cadastro.setText("");
                                        edt_confirm.setText("");

                                        //toast2.show();
                                    }
                                }
                            });
                }
            }
        });

    }
}