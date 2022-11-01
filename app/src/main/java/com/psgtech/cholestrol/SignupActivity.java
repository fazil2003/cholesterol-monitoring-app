package com.psgtech.cholestrol;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView imageView;
    TextView textViewHeading;
    EditText editTextUsername, editTextEmail, editTextPhone, editTextPassword;
    EditText editTextAge, editTextGender, editTextAddress, editTextHeight, editTextWeight, editTextBMI;
    EditText editTextOPNumber, editTextIPNumber, editTextRace, editTextHistory, editTextProfession;
    Spinner spinnerGender;
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

        editTextAge = findViewById(R.id.edittext_age);
        editTextAddress = findViewById(R.id.edittext_address);

        editTextOPNumber = findViewById(R.id.edittext_opnumber);
        editTextIPNumber = findViewById(R.id.edittext_ipnumber);
        editTextRace = findViewById(R.id.edittext_race);

        editTextHeight = findViewById(R.id.edittext_height);
        editTextWeight = findViewById(R.id.edittext_weight);
        editTextHistory = findViewById(R.id.edittext_history);
        editTextProfession = findViewById(R.id.edittext_profession);

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

        if(profile_id.equals("2") || profile_id.equals("1")){
            editTextOPNumber.setVisibility(View.GONE);
            editTextIPNumber.setVisibility(View.GONE);
            editTextRace.setVisibility(View.GONE);
            editTextHeight.setVisibility(View.GONE);
            editTextWeight.setVisibility(View.GONE);
            editTextHistory.setVisibility(View.GONE);
            editTextProfession.setVisibility(View.GONE);
        }

        Resources res = getResources();
        String[] category = res.getStringArray(R.array.string_spinner_gender);
        Spinner mySpinner = findViewById(R.id.spinner_gender);
        mySpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<CharSequence> aa = new ArrayAdapter<>(SignupActivity.this, R.layout.spinner_item, category);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(aa);


        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();

                String age = editTextAge.getText().toString();
                String address = editTextAddress.getText().toString();

                String OPNumber = editTextOPNumber.getText().toString();
                String IPNumber = editTextIPNumber.getText().toString();
                String race = editTextRace.getText().toString();

                String height = editTextHeight.getText().toString();
                String weight = editTextWeight.getText().toString();
                Double h = Double.parseDouble(height);
                Double w = Double.parseDouble(weight);
                Double b = w / (h * h);
                String bmi = String.valueOf(b);
                String history = editTextHistory.getText().toString();
                String profession = editTextProfession.getText().toString();

                String text = mySpinner.getSelectedItem().toString();
                String gender = String.valueOf(text);

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL

                        //Creating array for parameters
                        String[] field = new String[16];
                        field[0] = "username";
                        field[1] = "email";
                        field[2] = "phone";
                        field[3] = "password";
                        field[4] = "login_type";
                        field[5] = "age";
                        field[6] = "gender";
                        field[7] = "address";

                        field[8] = "op_number";
                        field[9] = "ip_number";
                        field[10] = "race";
                        field[11] = "height";
                        field[12] = "weight";
                        field[13] = "bmi";
                        field[14] = "history";
                        field[15] = "profession";

                        //Creating array for data
                        String[] data = new String[16];
                        data[0] = username;
                        data[1] = email;
                        data[2] = phone;
                        data[3] = password;
                        data[4] = profile_id;
                        data[5] = age;
                        data[6] = gender;
                        data[7] = address;

                        data[8] = OPNumber;
                        data[9] = IPNumber;
                        data[10] = race;
                        data[11] = height;
                        data[12] = weight;
                        data[13] = bmi;
                        data[14] = history;
                        data[15] = profession;

                        PutData putData = new PutData(getResources().getString(R.string.website_address) + "signup.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progressBar.setVisibility(View.GONE);
                                String result = putData.getResult();

                                if(result.contains("success")){
                                    Toast.makeText(SignupActivity.this, "Registered successfully.", Toast.LENGTH_SHORT).show();
//                                    Intent i = new Intent(SignupActivity.this, DisplayActivity.class);
//                                    i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    startActivity(i);
//                                    finish();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}