package com.example.trabajo1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trabajo1.Models.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText EditTextNombre;
    private EditText EditTextEmail;
    private EditText EditTextApellidos;
    private EditText EditTextEdad;
    private EditText EditTextContraseña;

    private Button ButtonRegistrar;
    private Button ButtonSendToLogin;
    private Button ButtonSendToDiscoteca;
    private Usuarios A;

    private String nombre;
    private String email;
    private String contraseña;
    private String apellidos;
    private int edad;




    private FirebaseAuth Autorizacion;
    private DatabaseReference BBDD;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgistro);

        Autorizacion = FirebaseAuth.getInstance();
        BBDD = FirebaseDatabase.getInstance().getReference();

        EditTextNombre = (EditText) findViewById(R.id.editTextNombre);
        EditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        EditTextContraseña = (EditText) findViewById(R.id.editTextContraseña);
        EditTextApellidos = (EditText) findViewById(R.id.editTextApellidos);
        EditTextEdad = (EditText) findViewById(R.id.editTextEdad);

        ButtonRegistrar = (Button) findViewById(R.id.btnRegistrar);


        ButtonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = EditTextNombre.getText().toString();
                email = EditTextEmail.getText().toString();
                contraseña = EditTextContraseña.getText().toString();
                apellidos =  EditTextApellidos.getText().toString();
                edad = Integer.parseInt(EditTextEdad.getText().toString());

                if (!nombre.isEmpty() && !email.isEmpty() && !contraseña.isEmpty() && !apellidos.isEmpty() && edad!=0) {
                    if (contraseña.length() >= 6) {
                        registerUser();
                    } else {
                        Toast.makeText(RegisterActivity.this, "La contraseña debe de tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText( RegisterActivity.this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }
    private void registerUser() {
        Autorizacion.createUserWithEmailAndPassword(email, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Usuarios A = new Usuarios(nombre, apellidos, email , edad, contraseña);
                    String id = Autorizacion.getCurrentUser().getUid();

                    BBDD.child("Usuarios").child(id).setValue(A).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish();

                            } else {
                                Toast.makeText(RegisterActivity.this, "No se han podido crear los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText( RegisterActivity.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
