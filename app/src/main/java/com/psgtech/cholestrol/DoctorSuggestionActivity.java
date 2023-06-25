package com.psgtech.cholestrol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class DoctorSuggestionActivity extends AppCompatActivity {

    TextView textViewQuery, textViewResponse;
    EditText editTextMessage;
    Button buttonSendMessage;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_suggestion);

        userId = getIntent().getStringExtra("user_id");

        textViewQuery = findViewById(R.id.textview_query);
        textViewResponse = findViewById(R.id.textview_response);
        editTextMessage = findViewById(R.id.edittext_message);
        buttonSendMessage = findViewById(R.id.button_send_message);

        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString();

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[2];
                        field[0] = "cookie_id";
                        field[1] = "message";
                        //Creating array for data
                        String[] data = new String[2];
                        data[0] = userId;
                        data[1] = message;

                        PutData putData = new PutData(getResources().getString(R.string.website_address) + "add_suggestion.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();

                                if(result.contains("failed")){
                                    Toast.makeText(DoctorSuggestionActivity.this, "Failed.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(DoctorSuggestionActivity.this, "Success.", Toast.LENGTH_SHORT).show();

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