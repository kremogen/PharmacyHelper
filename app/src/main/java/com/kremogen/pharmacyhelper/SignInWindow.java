package com.kremogen.pharmacyhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SignInWindow extends AppCompatActivity {

    EditText email, pass;
    Button signin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_window);
        Objects.requireNonNull(getSupportActionBar()).hide();

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        signin = findViewById(R.id.signin);

        ImageButton buttonBack = (ImageButton) findViewById(R.id.back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent(SignInWindow.this, RegWindow.class);
                SignInWindow.this.startActivity(mainIntent);
                SignInWindow.this.finish();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });
    }

    private void performLogin() {
        String inputEmail = email.getText().toString();
        String inputPass = pass.getText().toString();
        progressDialog = new ProgressDialog(this);

        if (!inputEmail.matches(emailPattern)) {
            email.setError("???????????????? ???????????? ??????????!");
            email.requestFocus();
        } else if (inputPass.isEmpty() || inputPass.length() < 8) {
            pass.setError("???????????????? ???????????? ????????????!");
            pass.requestFocus();
        } else {
            progressDialog.setMessage("????????????????????, ?????????????????? ???????? ?? ?????????????????? ??????...");
            progressDialog.setTitle("??????????????????????");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(inputEmail, inputPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(SignInWindow.this, "???? ?????????????? ????????????????????????!", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(SignInWindow.this, "???????????? ?????????????? ??????????????!"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendUserToNextActivity() {
        Intent intent = new Intent(SignInWindow.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}