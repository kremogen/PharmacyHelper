package com.kremogen.pharmacyhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Registration extends AppCompatActivity {

    EditText email, pass, confirm_pass;
    Button signin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_registration);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        confirm_pass = findViewById(R.id.confirm_pass);
        signin = findViewById(R.id.signin);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermormAuth();
            }
        });
    }

    private void PermormAuth() {
        String inputEmail = email.getText().toString();
        String inputPass = pass.getText().toString();
        String inputConPass = confirm_pass.getText().toString();
        progressDialog = new ProgressDialog(this);

        if (!inputEmail.matches(emailPattern))
        {
            email.setError("Неверный формат почты!");
            email.requestFocus();
        } else if(inputPass.isEmpty() || inputPass.length() < 8) {
            pass.setError("Неверный формат пароля!");
            pass.requestFocus();
        } else if(!inputPass.equals(inputConPass)) {
            confirm_pass.setError("Пароли не совпадают!");
            confirm_pass.requestFocus();
        } else {
            progressDialog.setMessage("Пожалуйста, подождите пока я регистрирую Вас...");
            progressDialog.setTitle("Регистрация");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(inputEmail, inputPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(Registration.this, "Вы успешно зарегестрированы!", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Registration.this, "Вы уже зарегестрированы!"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(Registration.this, PharmacyWindow.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}