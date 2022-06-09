package com.kremogen.pharmacyhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.kremogen.pharmacyhelper.modules.MedItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PharmacyWindow extends AppCompatActivity {

    private ListView listView;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference myRef;

    private List<String> medsNames;
    private List<MedItem> meds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_window);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();

        listView = findViewById(R.id.listView);

        myRef = FirebaseDatabase.getInstance("https://pharmacy-86351-default-rtdb.europe-west1.firebasedatabase.app").getReference("medicines");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        medsNames = new ArrayList<>();
        meds = new ArrayList<>();

        this.addEventListener();
    }

    private void addEventListener() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    MedItem item = child.getValue(MedItem.class);
                    if (item != null) {
                        medsNames.add(item.name);
                        meds.add(item);
                    }
                }

                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PharmacyWindow.this, "Ошибка подключения к базе данных!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, medsNames);
        listView.setAdapter(adapter);
    }
}