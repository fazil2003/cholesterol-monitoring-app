package com.psgtech.cholestrol;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    CardView cardViewDoctorLogin, cardViewPatientLogin, cardViewCaretakerLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.gradient));
        getSupportActionBar().setElevation(0);

        cardViewDoctorLogin = findViewById(R.id.cardview_doctor);
        cardViewDoctorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginWindow("1");
            }
        });

        cardViewCaretakerLogin = findViewById(R.id.cardview_caretaker);
        cardViewCaretakerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginWindow("2");
            }
        });

        cardViewPatientLogin = findViewById(R.id.carddview_patient);
        cardViewPatientLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginWindow("3");
            }
        });
    }

    private void openLoginWindow(String profile_id){
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.putExtra("profile_id", profile_id);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}