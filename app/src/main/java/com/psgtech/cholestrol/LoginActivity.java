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

public class LoginActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textViewHeading;
    EditText editTextUsername, editTextPassword;
    Button buttonLogin, buttonSignup;
    ProgressBar progressBar;

    String profile_id;

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

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[3];
                        field[0] = "username";
                        field[1] = "password";
                        field[2] = "login_type";
                        //Creating array for data
                        String[] data = new String[3];
                        data[0] = username;
                        data[1] = password;
                        data[2] = profile_id;

                        PutData putData = new PutData(getResources().getString(R.string.website_address) + "login.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progressBar.setVisibility(View.GONE);
                                String result = putData.getResult();

                                if(result.contains("failed")){
                                    Toast.makeText(LoginActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, "Login successfully.", Toast.LENGTH_SHORT).show();
                                    Intent i = null;
                                    if(profile_id.equals("1")){
                                        i = new Intent(LoginActivity.this, DoctorViewActivity.class);
                                    }
                                    else if(profile_id.equals("2")){
                                        i = new Intent(LoginActivity.this, DoctorViewActivity.class);
                                    }
                                    else if(profile_id.equals("3")){
                                        i = new Intent(LoginActivity.this, PatientViewActivity.class);
                                    }
                                    assert i != null;
                                    i.putExtra("cookie_id", result);
                                    i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();
                                }
                                //End ProgressBar (Set visibility to GONE)
                            }
                        }
                        //End Write and Read data with URL
                    }
                });

            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                i.putExtra("profile_id", profile_id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

    }
}