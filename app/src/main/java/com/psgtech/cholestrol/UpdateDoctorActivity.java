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

import com.psgtech.cholestrol.report.ReportActivity;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UpdateDoctorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView imageView;
    TextView textViewHeading;
    EditText editTextUsername, editTextEmail, editTextPhone, editTextPassword;
    EditText editTextHospital, editTextAddress, editTextHeight, editTextWeight, editTextDoctorID;
    EditText editTextOPNumber, editTextIPNumber, editTextRace, editTextHistory, editTextProfession;
    EditText editTextCaretaker1, editTextCaretaker2, editTextCaretaker3, editTextCaretaker4, editTextCaretaker5;
    Spinner spinnerDay, spinnerMonth, spinnerYear;
    Button buttonLogin, buttonSignup;
    ProgressBar progressBar;

    Spinner spinnerHospital, spinnerDoctor, spinnerPatient;

    String patientID;
    String profile_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_doctor);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.gradient));
        getSupportActionBar().setElevation(0);

        patientID = getIntent().getStringExtra("user_id");

        textViewHeading = findViewById(R.id.textview_heading);

        editTextCaretaker1 = findViewById(R.id.edittext_caretaker_1);
        editTextCaretaker2 = findViewById(R.id.edittext_caretaker_2);
        editTextCaretaker3 = findViewById(R.id.edittext_caretaker_3);
        editTextCaretaker4 = findViewById(R.id.edittext_caretaker_4);
        editTextCaretaker5 = findViewById(R.id.edittext_caretaker_5);

        progressBar = findViewById(R.id.progress_bar);
        buttonSignup = findViewById(R.id.button_signup);

        // Spinner Doctor
        spinnerDoctor = findViewById(R.id.spinner_doctor);
        spinnerDoctor.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        // Spinner Hospital
        spinnerHospital = findViewById(R.id.spinner_hospital);
        spinnerHospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get Doctors
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[1];
                        field[0] = "hospital";
                        String[] data = new String[1];
                        data[0] = spinnerHospital.getSelectedItem().toString();
                        PutData putData = new PutData(getResources().getString(R.string.website_address) + "get_doctors.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.contains("failed")){
                                    Toast.makeText(UpdateDoctorActivity.this, "Failed.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    String[] category = result.split(":::");
                                    ArrayAdapter<CharSequence> aa = new ArrayAdapter<>(UpdateDoctorActivity.this, R.layout.spinner_item, category);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerDoctor.setAdapter(aa);
                                }
                                //End ProgressBar (Set visibility to GONE)
                            }
                        }
                        //End Write and Read data with URL
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerHospital.setVisibility(View.VISIBLE);
        spinnerDoctor.setVisibility(View.VISIBLE);

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String hospitalPatient = "";
                String doctorPatient = "";

                String caretaker1 = "";
                String caretaker2 = "";
                String caretaker3 = "";
                String caretaker4 = "";
                String caretaker5 = "";

                hospitalPatient = spinnerHospital.getSelectedItem().toString();
                doctorPatient = spinnerDoctor.getSelectedItem().toString();
                caretaker1 = editTextCaretaker1.getText().toString();
                caretaker2 = editTextCaretaker2.getText().toString();
                caretaker3 = editTextCaretaker3.getText().toString();
                caretaker4 = editTextCaretaker4.getText().toString();
                caretaker5 = editTextCaretaker5.getText().toString();

                Handler handler = new Handler(Looper.getMainLooper());
                String finalHospitalPatient = hospitalPatient;
                String finalDoctorPatient = doctorPatient;
                String finalCaretaker = caretaker1;
                String finalCaretaker1 = caretaker2;
                String finalCaretaker2 = caretaker3;
                String finalCaretaker3 = caretaker4;
                String finalCaretaker4 = caretaker5;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL

                        //Creating array for parameters
                        String[] field = new String[7];
                        field[0] = "patient_id";
                        field[1] = "doctor_patient";
                        field[2] = "caretaker1";
                        field[3] = "caretaker2";
                        field[4] = "caretaker3";
                        field[5] = "caretaker4";
                        field[6] = "caretaker5";

                        //Creating array for data
                        String[] data = new String[7];
                        data[0] = patientID;
                        data[1] = finalDoctorPatient;
                        data[2] = finalCaretaker;
                        data[3] = finalCaretaker1;
                        data[4] = finalCaretaker2;
                        data[5] = finalCaretaker3;
                        data[6] = finalCaretaker4;

                        PutData putData = new PutData(getResources().getString(R.string.website_address) + "update_doctor.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progressBar.setVisibility(View.GONE);
                                String result = putData.getResult();

                                if(result.contains("success")){
                                    Toast.makeText(UpdateDoctorActivity.this, "Updated successfully.", Toast.LENGTH_SHORT).show();
//                                    Intent i = new Intent(SignupActivity.this, DisplayActivity.class);
//                                    i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    startActivity(i);
//                                    finish();
                                }
                                else{
                                    Toast.makeText(UpdateDoctorActivity.this, "Update failed.", Toast.LENGTH_SHORT).show();
                                }
                                //End ProgressBar (Set visibility to GONE)
                            }
                        }
                        //End Write and Read data with URL
                    }
                });

            }
        });


        // Get Hospitals
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[1];
                field[0] = "user_type";
                String[] data = new String[1];
                data[0] = "3";
                PutData putData = new PutData(getResources().getString(R.string.website_address) + "get_hospitals.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        if(result.contains("failed")){
                            Toast.makeText(UpdateDoctorActivity.this, "Failed.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String[] category = result.split(":::");
                            ArrayAdapter<CharSequence> aa = new ArrayAdapter<>(UpdateDoctorActivity.this, R.layout.spinner_item, category);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerHospital.setAdapter(aa);
                        }
                        //End ProgressBar (Set visibility to GONE)
                    }
                }
                //End Write and Read data with URL
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