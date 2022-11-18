package com.pucmm.ecommerceapp_proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextView btnRegistrar, btnOlvide;
    TextInputLayout edMail, edPass;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //reload();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLoginL);
        btnRegistrar = findViewById(R.id.btnRegisterL);
        btnOlvide = findViewById(R.id.btnForgetL);
        edMail = findViewById(R.id.edEmailL);
        edPass = findViewById(R.id.edPassL);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    public void openRegister(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }

    public void openForget(View view) {
        startActivity(new Intent(getApplicationContext(), ForgetActivity.class));
    }

    public Boolean validation(String email, String pass) {
        Boolean correct = true;

        if (!RegisterActivity.isValidEmail(email)) {
            edMail.setError("El email no tiene el formato correcto, favor verificar.");
            correct = false;
        } else {
            if (edMail.getError() != null) {
                edMail.setError(null);
            }
        }
        if (pass.length() < 6) {
            edPass.setError("La contraseña debe contener más de 6 caracteres.");
            correct = false;
        } else {
            if (edPass.getError() != null) {
                edPass.setError(null);
            }
        }

        return correct;
    }

    public void GoLogin(View view) {
        String email = edMail.getEditText().getText().toString();
        String pass = edPass.getEditText().getText().toString();

        if(validation(email,pass))
        {
            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecto.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        }

    }
}