package com.psgtech.cholestrol;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textViewHeading;
    EditText editTextUsername, editTextPassword;
    Button buttonLogin;

    String profile_id;
    String sample_user, sample_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.gradient));
        getSupportActionBar().setElevation(0);

        profile_id = getIntent().getStringExtra("profile_id");

        imageView = findViewById(R.id.imageview);
        textViewHeading = findViewById(R.id.textview_heading);
        editTextUsername = findViewById(R.id.edittext_username);
        editTextPassword = findViewById(R.id.edittext_password);
        buttonLogin = findViewById(R.id.button_login);

        if (profile_id.equals("1")){
            sample_user = "psg123";
            sample_pass = "1234";
            textViewHeading.setText("Doctor's Login");
            imageView.setBackgroundResource(R.drawable.image_doctor);
        }
        else if (profile_id.equals("2")){
            sample_user = "user123";
            sample_pass = "pass";
            textViewHeading.setText("Caretaker's Login");
            imageView.setBackgroundResource(R.drawable.image_dietitian);
        }
        else{
            sample_user = "person123";
            sample_pass = "pass";
            textViewHeading.setText("Patient's Login");
            imageView.setBackgroundResource(R.drawable.image_patient);
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                if(username.equals(sample_user) && password.equals(sample_pass)){
                    Intent intent = new Intent(LoginActivity.this, DisplayActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Error Login", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}