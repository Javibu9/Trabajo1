package com.example.trabajo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private EditText EditTextEmail;
    private EditText EditTextContraseña;
    private Button ButtonLogin;
    private Switch remember;
    private String email="";
    private String contraseña="";
    private FirebaseAuth Autorizacion;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindUI();
        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        Autorizacion= FirebaseAuth.getInstance();


        ButtonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                email= EditTextEmail.getText().toString();
                contraseña= EditTextContraseña.getText().toString();
                //si los campos email y contraseña no estan vacios el usuario se logea
                if (loginUser()){
                    AdvanceActivity();
                    saveOnPreferences(email, contraseña);
                }
                if (!email.isEmpty() && !contraseña.isEmpty()){
                    loginUser();
                }
                //si los campos email y contraseña estan vacios escribe el siguiente mensaje
                else {
                    Toast.makeText(MainActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    private void saveOnPreferences(String email, String contraseña) {
        if (remember.isChecked()) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("email", email);
            editor.putString("pass", contraseña);
            editor.apply();
        }
    }
    private void AdvanceActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void bindUI() {
        remember= (Switch) findViewById(R.id.remember);
        EditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        EditTextContraseña = (EditText) findViewById(R.id.editTextPassword);
        ButtonLogin = (Button) findViewById(R.id.btnLogin);
    }

    private void loginUser(){
        Autorizacion.signInWithEmailAndPassword(email, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //metodo para logearse
                if (task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, SecondActivity.class));
                    finish();
                }
                //si al logearse los datos son incorrectos se escribira el siguiente mensaje
                else{
                    Toast.makeText(MainActivity.this, "ERROR. comprueba los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
