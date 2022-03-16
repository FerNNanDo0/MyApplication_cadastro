package com.usuario.cadastro;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.LENGTH_SHORT;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.SharedPreferences;
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

public class Log_Activity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    public FirebaseAuth mAuth;
    public String EmailSalvo;
    public String logmail;
    public String logsenha;
    public Intent intentMain;
    public EditText log_email1;
    public EditText log_senha1;
    private Button button;
    public CheckBox checkBox;
    private SharedPreferences preferences;
    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            user.reload().addOnCompleteListener(this,
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                startActivity(intentMain);
                            }
                        }
                    });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loguin);

    //___________ // __________  // _________ //_______ //________ //___________ // _____ \\_____

        mAuth = FirebaseAuth.getInstance();
        intentMain = new Intent(this, MainActivity.class);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_CODE);
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

        preferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
        veriff();

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
                            .addOnCompleteListener(Log_Activity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                              // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");

                                        startActivity(intentMain);
                                        intentMain.setAction(Intent.ACTION_MAIN);
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
        final CheckBox check = findViewById(R.id.checkBox);
        boolean checked = check.isChecked();

        // pega o ID da view "CHECKBOX"
        switch(view.getId()){
            case R.id.checkBox:

                SharedPreferences.Editor editor = preferences.edit();

                if(checked){
                    logmail = log_email1.getText().toString().trim();

                    if ( !logmail.isEmpty() ){
                        editor.putString("Email", logmail);
                        editor.commit();
                        }
                }else{
                    editor.putString("Email", null);
                    editor.commit();
                    //EmailSalvo = preferences.getString("Email","");
                }
                break;
        }
    }

    public void veriff(){

        EmailSalvo = preferences.getString("Email","");
        if ( !EmailSalvo.isEmpty() ){

            log_email1.setText(EmailSalvo);
            System.out.println(" Email Salvo >> " + EmailSalvo);
            checkBox.setChecked(true);
        }
        // nova Thread
        Atualize atualize = new Atualize();
        new Thread( atualize ).start();
    }

  // Botão para ir para a tela de cadastro
    public void cadastrar(View view) {

        Intent intentCadastro = new Intent(this, Cadastro_Activity.class);
        startActivity(intentCadastro);
    }

    public class Atualize implements Runnable {

        int lenn;
        int len;

        @Override
        public void run() {

            len = log_email1.getText().length();
            while (len > 0) {
                lenn = log_email1.getText().length();
                if (lenn < EmailSalvo.length()) {
                    checkBox.setChecked(false);
                }
            }
        }
    }



    // fim class MAIN
 }