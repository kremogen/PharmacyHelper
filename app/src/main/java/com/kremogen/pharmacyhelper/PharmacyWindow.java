package com.kremogen.pharmacyhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Objects;

public class PharmacyWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_window);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}