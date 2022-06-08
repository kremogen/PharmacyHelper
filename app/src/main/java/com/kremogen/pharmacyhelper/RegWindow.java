package com.kremogen.pharmacyhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class RegWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_reg_window);
        Button button = (Button) findViewById(R.id.signup);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent(RegWindow.this, Registration.class);
                RegWindow.this.startActivity(mainIntent);
                RegWindow.this.finish();
            }
        });

        Button buttonSignIn = (Button) findViewById(R.id.signin);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent(RegWindow.this, SignInWindow.class);
                RegWindow.this.startActivity(mainIntent);
                RegWindow.this.finish();
            }
        });
    }
}