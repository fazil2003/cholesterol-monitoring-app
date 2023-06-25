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

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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

    String profile_id;

    String[] arrayMonth = {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };

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
        editTextAddress = findViewById(R.id.edittext_address);

        editTextHospital = findViewById(R.id.edittext_hospital);
        editTextDoctorID = findViewById(R.id.edittext_doctor_id);

        editTextOPNumber = findViewById(R.id.edittext_opnumber);
        editTextIPNumber = findViewById(R.id.edittext_ipnumber);
        editTextRace = findViewById(R.id.edittext_race);

        editTextHeight = findViewById(R.id.edittext_height);
        editTextWeight = findViewById(R.id.edittext_weight);
        editTextHistory = findViewById(R.id.edittext_history);
        editTextProfession = findViewById(R.id.edittext_profession);

        editTextCaretaker1 = findViewById(R.id.edittext_caretaker_1);
        editTextCaretaker2 = findViewById(R.id.edittext_caretaker_2);
        editTextCaretaker3 = findViewById(R.id.edittext_caretaker_3);
        editTextCaretaker4 = findViewById(R.id.edittext_caretaker_4);
        editTextCaretaker5 = findViewById(R.id.edittext_caretaker_5);

        spinnerDay = findViewById(R.id.spinner_day);
        spinnerMonth = findViewById(R.id.spinner_month);
        spinnerYear = findViewById(R.id.spinner_year);

        progressBar = findViewById(R.id.progress_bar);
        buttonLogin = findViewById(R.id.button_login);
        buttonSignup = findViewById(R.id.button_signup);

        if (profile_id.equals("1")){
            textViewHeading.setText("Doctor's Registration");
            imageView.setBackgroundResource(R.drawable.image_doctor);
        }
        else if (profile_id.equals("2")){
            textViewHeading.setText("Caretaker's Registration");
            imageView.setBackgroundResource(R.drawable.image_dietitian);
        }
        else{
            textViewHeading.setText("Patient's Registration");
            imageView.setBackgroundResource(R.drawable.image_patient);
        }

        if(profile_id.equals("2") || profile_id.equals("1")){
            editTextCaretaker1.setVisibility(View.GONE);
            editTextCaretaker2.setVisibility(View.GONE);
            editTextCaretaker3.setVisibility(View.GONE);
            editTextCaretaker4.setVisibility(View.GONE);
            editTextCaretaker5.setVisibility(View.GONE);

            editTextOPNumber.setVisibility(View.GONE);
            editTextIPNumber.setVisibility(View.GONE);
            editTextRace.setVisibility(View.GONE);
            editTextHeight.setVisibility(View.GONE);
            editTextWeight.setVisibility(View.GONE);
            editTextHistory.setVisibility(View.GONE);
            editTextProfession.setVisibility(View.GONE);
        }

        if(profile_id.equals("2") || profile_id.equals("3")){
            editTextHospital.setVisibility(View.GONE);
            editTextDoctorID.setVisibility(View.GONE);
        }

        Resources res = getResources();
        String[] category = res.getStringArray(R.array.string_spinner_gender);
        Spinner mySpinner = findViewById(R.id.spinner_gender);
        mySpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<CharSequence> aa = new ArrayAdapter<>(SignupActivity.this, R.layout.spinner_item, category);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(aa);

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
                                    Toast.makeText(SignupActivity.this, "Failed.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    String[] category = result.split(":::");
                                    ArrayAdapter<CharSequence> aa = new ArrayAdapter<>(SignupActivity.this, R.layout.spinner_item, category);
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

        if(profile_id.equals("3")){
            spinnerHospital.setVisibility(View.VISIBLE);
            spinnerDoctor.setVisibility(View.VISIBLE);
        }



        // YEAR
        List<String> arrayListYear= new ArrayList<>();
        SimpleDateFormat targetFormatYear = new SimpleDateFormat("yyyy" );
        String currentYearString = targetFormatYear.format(new Date());
        Integer currentYear = Integer.parseInt(currentYearString);
        for(int i = currentYear - 200; i <= currentYear; i++){
            arrayListYear.add(String.valueOf(i));
        }
        String[] arrayYear = new String[arrayListYear.size()];
        arrayYear = arrayListYear.toArray(arrayYear);
        spinnerYear = findViewById(R.id.spinner_year);
        spinnerYear.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<CharSequence> arrayAdapterYear = new ArrayAdapter<>(SignupActivity.this, R.layout.spinner_item, arrayYear);
        arrayAdapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(arrayAdapterYear);
        ArrayAdapter myAdapterYear = (ArrayAdapter) spinnerYear.getAdapter();
        int spinnerPositionYear = myAdapterYear.getPosition(currentYearString);
        spinnerYear.setSelection(spinnerPositionYear);


        // MONTH
        List<String> arrayListMonth = new ArrayList<>();
        SimpleDateFormat targetFormatMonth = new SimpleDateFormat("MM" );
        String currentMonthString = targetFormatMonth.format(new Date());
        Integer currentMonth = Integer.parseInt(currentMonthString);
        spinnerMonth = findViewById(R.id.spinner_month);
        spinnerMonth.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<CharSequence> arrayAdapterMonth = new ArrayAdapter<>(SignupActivity.this, R.layout.spinner_item, arrayMonth);
        arrayAdapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(arrayAdapterMonth);
        ArrayAdapter myAdapterMonth = (ArrayAdapter) spinnerMonth.getAdapter();
        int spinnerPositionMonth = myAdapterMonth.getPosition(arrayMonth[currentMonth - 1]);
        spinnerMonth.setSelection(spinnerPositionMonth);


        // DAY
        List<String> arrayListDay = new ArrayList<>();
        SimpleDateFormat targetFormatDay = new SimpleDateFormat("dd" );
        String currentDayString = targetFormatDay.format(new Date());
        Integer currentDay = Integer.parseInt(currentDayString);
        for(int i = 1; i <= 31; i++){
            arrayListDay.add(String.valueOf(i));
        }
        String[] arrayDay = new String[arrayListDay.size()];
        arrayDay = arrayListDay.toArray(arrayDay);
        spinnerDay = findViewById(R.id.spinner_day);
        spinnerDay.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<CharSequence> arrayAdapterDay = new ArrayAdapter<>(SignupActivity.this, R.layout.spinner_item, arrayDay);
        arrayAdapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(arrayAdapterDay);
        ArrayAdapter myAdapterDay = (ArrayAdapter) spinnerDay.getAdapter();
        int spinnerPositionDay = myAdapterDay.getPosition(currentDayString);
        spinnerDay.setSelection(spinnerPositionDay);


        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String stringYear = spinnerYear.getSelectedItem().toString();
                String stringMonthString = spinnerMonth.getSelectedItem().toString();
                String stringMonth = String.valueOf(Arrays.asList(arrayMonth).indexOf(stringMonthString) + 1);
                String stringDay = spinnerDay.getSelectedItem().toString();
                if (stringDay.length() == 1) {
                    stringDay = "0" + stringDay;
                }
                if (stringMonth.length() == 1) {
                    stringMonth = "0" + stringMonth;
                }
                String inputDate = stringYear + "-" + stringMonth + "-" + stringDay;

                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();

                String age = inputDate;
                String address = editTextAddress.getText().toString();

                String hospital = "";
                String doctorID = "";
                if(profile_id.equals("1")){
                    hospital = editTextHospital.getText().toString();
                    doctorID = editTextDoctorID.getText().toString();
                }

                String OPNumber = "";
                String IPNumber = "";
                String race = "";
                String height = "1";
                String weight = "1";
                String history = "";
                String profession = "";
                String hospitalPatient = "";
                String doctorPatient = "";

                String caretaker1 = "";
                String caretaker2 = "";
                String caretaker3 = "";
                String caretaker4 = "";
                String caretaker5 = "";

                if(profile_id.equals("3")) {
                    hospitalPatient = spinnerHospital.getSelectedItem().toString();
                    doctorPatient = spinnerDoctor.getSelectedItem().toString();
                    OPNumber = editTextOPNumber.getText().toString();
                    IPNumber = editTextIPNumber.getText().toString();
                    race = editTextRace.getText().toString();
                    height = editTextHeight.getText().toString();
                    weight = editTextWeight.getText().toString();
                    history = editTextHistory.getText().toString();
                    profession = editTextProfession.getText().toString();

                    caretaker1 = editTextCaretaker1.getText().toString();
                    caretaker2 = editTextCaretaker2.getText().toString();
                    caretaker3 = editTextCaretaker3.getText().toString();
                    caretaker4 = editTextCaretaker4.getText().toString();
                    caretaker5 = editTextCaretaker5.getText().toString();
                }

                Double h = Double.parseDouble(height);
                Double w = Double.parseDouble(weight);
                h = h / 100;
                Double b = w / (h * h);
                String bmi = String.valueOf(b);

                String text = mySpinner.getSelectedItem().toString();
                String gender = String.valueOf(text);

                Handler handler = new Handler(Looper.getMainLooper());
                String finalOPNumber = OPNumber;
                String finalIPNumber = IPNumber;
                String finalRace = race;
                String finalHeight = height;
                String finalWeight = weight;
                String finalHistory = history;
                String finalProfession = profession;
                String finalHospital = hospital;
                String finalDoctorID = doctorID;
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
                        String[] field = new String[25];
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

                        field[16] = "hospital";
                        field[17] = "doctor_id";

                        field[18] = "hospital_patient";
                        field[19] = "doctor_patient";
                        field[20] = "caretaker1";
                        field[21] = "caretaker2";
                        field[22] = "caretaker3";
                        field[23] = "caretaker4";
                        field[24] = "caretaker5";

                        //Creating array for data
                        String[] data = new String[25];
                        data[0] = username;
                        data[1] = email;
                        data[2] = phone;
                        data[3] = password;
                        data[4] = profile_id;
                        data[5] = age;
                        data[6] = gender;
                        data[7] = address;

                        data[8] = finalOPNumber;
                        data[9] = finalIPNumber;
                        data[10] = finalRace;
                        data[11] = finalHeight;
                        data[12] = finalWeight;
                        data[13] = bmi;
                        data[14] = finalHistory;
                        data[15] = finalProfession;

                        data[16] = finalHospital;
                        data[17] = finalDoctorID;

                        data[18] = finalHospitalPatient;
                        data[19] = finalDoctorPatient;
                        data[20] = finalCaretaker;
                        data[21] = finalCaretaker1;
                        data[22] = finalCaretaker2;
                        data[23] = finalCaretaker3;
                        data[24] = finalCaretaker4;

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

        // Get Hospitals
        if(profile_id.equals("3")){
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
                                Toast.makeText(SignupActivity.this, "Failed.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String[] category = result.split(":::");
                                ArrayAdapter<CharSequence> aa = new ArrayAdapter<>(SignupActivity.this, R.layout.spinner_item, category);
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

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}