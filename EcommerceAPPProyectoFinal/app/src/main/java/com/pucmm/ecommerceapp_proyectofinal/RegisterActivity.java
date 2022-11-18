package com.pucmm.ecommerceapp_proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextInputLayout edMail, edPass, edNombre, edPhone;
    TextView btnLogin;
    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        edMail =  findViewById(R.id.edEmailR);
        edPass =  findViewById(R.id.edPassR);
        edNombre =  findViewById(R.id.edNameR);
        edPhone =  findViewById(R.id.edPhoneR);
        btnLogin = findViewById(R.id.btnLoginR);
        btnContinue = findViewById(R.id.btnContinueR);
    }


    public void openLogin(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void saveUser(View view) {
        String email = edMail.getEditText().getText().toString();
        String phone = edPhone.getEditText().getText().toString();
        String pass = edPass.getEditText().getText().toString();
        String nombre = edNombre.getEditText().getText().toString();
        if(validation(nombre, email, pass,phone))
        {
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                        UUID userId = UUID.fromString(task.getResult().getUser().getUid());
                        User user = new(userId, )
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Error registrando: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public Boolean validation(String nombre, String email, String pass, String phone) {
        Boolean correct = true;

        if (nombre.length() < 6) {
            edNombre.setError("El nombre debe contener mas de 6 caracteres.");
            correct = false;
        } else {
            if (edNombre.getError() !=null) {
                edNombre.setError(null);
            }
        }

        if (!isValidEmail(email)) {
            edMail.setError("El email no tiene el formato correcto, favor verificar.");
            correct = false;
        } else {
            //FIND BY MAIL en la BD
            if (edMail.getError() != null) {
                edMail.setError(null);
            }
        }

        if (pass.length() < 6) {
            edPass.setError("La contraseña debe contener mas de 6 caracteres.");
            correct = false;
        } else {
            if (edPass.getError()!= null) {
                edPass.setError(null);
            }
        }

        if (phone.length() != 10) {
            edPhone.setError("El numero telefonico no tiene el formato correcto, favor verificar.");
            correct = false;
        } else {
            //FIND BY PHONE en la BD
            if (edPhone.getError() != null) {
                edPhone.setError(null);
            }
        }

        return correct;
    }
}