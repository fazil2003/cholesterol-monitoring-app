package com.psgtech.cholestrol;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textViewHeading;
    EditText editTextUsername, editTextEmail, editTextPhone, editTextPassword;
    Button buttonLogin, buttonSignup;
    ProgressBar progressBar;

    String profile_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
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
        editTextEmail = findViewById(R.id.edittext_email);
        editTextPhone = findViewById(R.id.edittext_phone);
        progressBar = findViewById(R.id.progress_bar);
        buttonLogin = findViewById(R.id.button_login);
        buttonSignup = findViewById(R.id.button_signup);

        if (profile_id.equals("1")){
            textViewHeading.setText("Doctor's Login");
            imageView.setBackgroundResource(R.drawable.image_doctor);
        }
        else if (profile_id.equals("2")){
            textViewHeading.setText("Caretaker's Login");
            imageView.setBackgroundResource(R.drawable.image_dietitian);
        }
        else{
            textViewHeading.setText("Patient's Login");
            imageView.setBackgroundResource(R.drawable.image_patient);
        }

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[5];
                        field[0] = "username";
                        field[1] = "email";
                        field[2] = "phone";
                        field[3] = "password";
                        field[4] = "login_type";
                        //Creating array for data
                        String[] data = new String[5];
                        data[0] = username;
                        data[1] = email;
                        data[2] = phone;
                        data[3] = password;
                        data[4] = profile_id;

                        PutData putData = new PutData(getResources().getString(R.string.website_address) + "signup.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progressBar.setVisibility(View.GONE);
                                String result = putData.getResult();

                                if(result.contains("success")){
                                    Toast.makeText(SignupActivity.this, "Registered successfully.", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(SignupActivity.this, DisplayActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();
                                }
                                else{
                                    Toast.makeText(SignupActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                                }
                                //End ProgressBar (Set visibility to GONE)
                            }
                        }
                        //End Write and Read data with URL
                    }
                });

            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                i.putExtra("profile_id", profile_id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

    }
}