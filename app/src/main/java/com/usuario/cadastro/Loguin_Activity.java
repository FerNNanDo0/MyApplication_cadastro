package com.usuario.cadastro;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.LENGTH_SHORT;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CheckBox;
import android.view.View;
import android.content.Intent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Loguin_Activity extends AppCompatActivity {

    private static final int READ_SOCKET = 1;
    public FirebaseAuth mAuth;
    public String logmail;
    public String logsenha;
    public Intent intentMain;
    public EditText log_email1;
    public EditText log_senha1;
    private Button button;
    public CheckBox checkBox;

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            user.reload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loguin);

        //___________ // __________  // _________ //_______ //________ //___________ // _____ \\_____

        mAuth = FirebaseAuth.getInstance();
        intentMain = new Intent(this, MainActivity.class);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, READ_SOCKET);
        }
        //. VARIAVEL DO BOTÃO

        button = findViewById(R.id.btn);
        log_email1 = findViewById(R.id.log_email);
        log_senha1 = findViewById(R.id.log_senha);
        checkBox = findViewById(R.id.checkBox);

        // toast para mensagem que esta logando o app
        Toast toastLog = Toast.makeText(this, "Logando... ", Toast.LENGTH_LONG);
        Toast toastInfo = Toast.makeText(this, "Dados invalidos ou usuario nao cadastrado", Toast.LENGTH_LONG);
        Toast toast3 = Toast.makeText(this, "preencha os dados acima", LENGTH_SHORT);

        // Click do BOTÃO LOGUIN
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // converte texto dos campos para tipo String
                logmail = log_email1.getText().toString().trim();
                logsenha = log_senha1.getText().toString().trim();

                // verificar os campos
                if (logmail.isEmpty() | logsenha.isEmpty()) {
                    toast3.show();
                }
                else {
                    mAuth.signInWithEmailAndPassword(logmail, logsenha )
                            .addOnCompleteListener(Loguin_Activity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        startActivity(intentMain);
                                        toastLog.show();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        log_senha1.setText("");
                                        toastInfo.show();
                                    }
                                }
                            });
                }
            }
        });

    }

    // função do CHECKBOX
    public void clickCheckBox(View view) {
        // VARIÁVEIS
        final CheckBox check =findViewById(R.id.checkBox);
        boolean checked = check.isChecked();

        // pega o ID da view "CHECKBOX"
        switch(view.getId()){

            case R.id.checkBox:
                if(checked){ }
                else{  }
                break;
        }
    }
    // Botão para ir para a tela de cadastro
    public void cadastrar(View view) {

        Intent intentCadastro = new Intent(this, Cadastro_Activity.class);
        startActivity(intentCadastro);
    }




}