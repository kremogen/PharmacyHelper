package com.kremogen.pharmacyhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.*;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        Button buttonSignIn = (Button) findViewById(R.id.next);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainIntent = new Intent(MainActivity.this, PharmacyWindow.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        });
    }
}