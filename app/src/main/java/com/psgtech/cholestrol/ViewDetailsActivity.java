package com.psgtech.cholestrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.psgtech.cholestrol.report.ReportActivity;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class ViewDetailsActivity extends AppCompatActivity {

    LinearLayout layoutForPatient;
    String userType, userId, userName, userEmail, userPhone;
    TextView tvName, tvPhone, tvEmail, tvAge, tvGender, tvAddress;
    TextView tvOPNumber, tvIPNumber, tvRace, tvHeight, tvWeight, tvBmi, tvHistory, tvProfession;
    ProgressBar progressBar;
    Button buttonGenerateReportAll, buttonGenerateReportMonth, buttonGenerateReportYear, buttonGiveSuggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        userType = getIntent().getStringExtra("user_type");
        userId = getIntent().getStringExtra("user_id");

        progressBar = findViewById(R.id.progress_bar);

        layoutForPatient = findViewById(R.id.layout_for_patient);
        if(userType.equals("3")){
            layoutForPatient.setVisibility(View.VISIBLE);
        }

        tvName = findViewById(R.id.textview_name);
        tvEmail = findViewById(R.id.textview_email);
        tvPhone = findViewById(R.id.textview_phone);
        tvAge = findViewById(R.id.textview_age);
        tvGender = findViewById(R.id.textview_gender);
        tvAddress = findViewById(R.id.textview_address);

        tvOPNumber = findViewById(R.id.textview_opnumber);
        tvIPNumber = findViewById(R.id.textview_ipnumber);
        tvRace = findViewById(R.id.textview_race);
        tvHeight = findViewById(R.id.textview_height);
        tvWeight = findViewById(R.id.textview_weight);
        tvBmi = findViewById(R.id.textview_bmi);
        tvHistory = findViewById(R.id.textview_history);
        tvProfession = findViewById(R.id.textview_profession);

        buttonGenerateReportAll = findViewById(R.id.button_generate_report_all);
        buttonGenerateReportMonth = findViewById(R.id.button_generate_report_month);
        buttonGenerateReportYear = findViewById(R.id.button_generate_report_year);
        buttonGiveSuggestion = findViewById(R.id.button_give_suggestion);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        if(sp.getString("from_user","0").equals("1")){
            buttonGiveSuggestion.setVisibility(View.VISIBLE);
        }

        if(userType.equals("3")){
            buttonGenerateReportAll.setVisibility(View.VISIBLE);
            buttonGenerateReportMonth.setVisibility(View.VISIBLE);
            buttonGenerateReportYear.setVisibility(View.VISIBLE);
        }

        buttonGiveSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewDetailsActivity.this, DoctorResponseActivity.class);
                assert i != null;
                i.putExtra("user_id", userId);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        buttonGenerateReportAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewDetailsActivity.this, ReportActivity.class);
                assert i != null;
                i.putExtra("user_type", userType);
                i.putExtra("user_id", userId);
                i.putExtra("report_type", "1");
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        buttonGenerateReportMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewDetailsActivity.this, ReportActivity.class);
                assert i != null;
                i.putExtra("user_type", userType);
                i.putExtra("user_id", userId);
                i.putExtra("report_type", "2");
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        buttonGenerateReportYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewDetailsActivity.this, ReportActivity.class);
                assert i != null;
                i.putExtra("user_type", userType);
                i.putExtra("user_id", userId);
                i.putExtra("report_type", "3");
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[2];
                field[0] = "user_type";
                field[1] = "user_id";
                //Creating array for data
                String[] data = new String[2];
                data[0] = userType;
                data[1] = userId;

                PutData putData = new PutData(getResources().getString(R.string.website_address) + "user_details.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        progressBar.setVisibility(View.GONE);
                        String result = putData.getResult();

                        if(result.contains("failed")){
                            Toast.makeText(ViewDetailsActivity.this, "Failed.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String[] arr = result.split(":::");
                            tvName.setText(arr[1]);
                            tvEmail.setText(arr[2]);
                            tvPhone.setText(arr[3]);
                            tvAge.setText(arr[4]);
                            tvGender.setText(arr[5]);
                            tvAddress.setText(arr[6]);

                            if(userType.equals("3")){
                                tvOPNumber.setText(arr[7]);
                                tvIPNumber.setText(arr[8]);
                                tvRace.setText(arr[9]);
                                tvHeight.setText(arr[10]);
                                tvWeight.setText(arr[11]);
                                tvBmi.setText(arr[12]);
                                tvHistory.setText(arr[13]);
                                tvProfession.setText(arr[14]);
                            }

                        }
                        //End ProgressBar (Set visibility to GONE)
                    }
                }
                //End Write and Read data with URL
            }
        });


    }
}