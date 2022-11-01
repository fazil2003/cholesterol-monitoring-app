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

public class AddDataActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textViewHeading;
    EditText editTextData, editTextPassword;
    Button buttonLogin, buttonSignup;
    ProgressBar progressBar;

    String cookie_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.gradient));
        getSupportActionBar().setElevation(0);

        cookie_id = getIntent().getStringExtra("user_id");

        editTextData = findViewById(R.id.edittext_data);
        progressBar = findViewById(R.id.progress_bar);
        buttonLogin = findViewById(R.id.button_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cholestrolData = editTextData.getText().toString();
                progressBar.setVisibility(View.VISIBLE);

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[2];
                        field[0] = "cookie_id";
                        field[1] = "data";
                        //Creating array for data
                        String[] data = new String[2];
                        data[0] = cookie_id;
                        data[1] = cholestrolData;

                        PutData putData = new PutData(getResources().getString(R.string.website_address) + "add.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progressBar.setVisibility(View.GONE);
                                String result = putData.getResult();

                                if(result.contains("failed")){
                                    Toast.makeText(AddDataActivity.this, "Failed.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(AddDataActivity.this, "Success.", Toast.LENGTH_SHORT).show();

                                }
                                //End ProgressBar (Set visibility to GONE)
                            }
                        }
                        //End Write and Read data with URL
                    }
                });

            }
        });

    }
}