package com.psgtech.cholestrol;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Objects;

public class DisplayActivity extends AppCompatActivity {

    LinearLayout layout;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.gradient));
        getSupportActionBar().setElevation(0);

        layout = findViewById(R.id.layout);

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("data.csv")));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                String[] lines = mLine.split(",");

                CardView cardView = new CardView(this);
                LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                LinearLayout.LayoutParams textviewLayoutParams = new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                );

                int marginDp = 5;
                float density = getResources().getDisplayMetrics().density;
                int marginPixel = (int)(marginDp * density);
                cardViewParams.setMargins(marginPixel, marginPixel, marginPixel, 0);
                int paddingDp = 10;
                int paddingPixel = (int)(paddingDp * density);
                cardView.setLayoutParams(cardViewParams);
                cardView.setClickable(true);
                cardView.setForeground(ContextCompat.getDrawable(this, R.drawable.cardview_ripple_effect));
                cardView.setRadius(10);

                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setLayoutParams(linearLayoutParams);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel);

                TextView textView1 = new TextView(this);
                textView1.setLayoutParams(textviewLayoutParams);
                textView1.setText(lines[0]);
                textView1.setTextColor(ContextCompat.getColor(this, R.color.black));
                textView1.setTextSize(18);
                textView1.setGravity(Gravity.LEFT);

                TextView textView2 = new TextView(this);
                textView2.setLayoutParams(textviewLayoutParams);
                textView2.setText(lines[1]);
                textView2.setTextColor(ContextCompat.getColor(this, R.color.dodgerblue));
                textView2.setTextSize(20);
                textView2.setTypeface(null, Typeface.BOLD);
                textView2.setGravity(Gravity.RIGHT);

                TextView textView3 = new TextView(this);
                textView3.setLayoutParams(textviewLayoutParams);
                String result = "";
                int data = Integer.parseInt(lines[1]);
                if(data <= 200){
                    result = "Normal";
                    textView3.setTextColor(ContextCompat.getColor(this, R.color.seagreen));
                }
                else if(data < 240){
                    result = "Borderline";
                    textView3.setTextColor(ContextCompat.getColor(this, R.color.orange));
                }
                else {
                    result = "high";
                    textView3.setTextColor(ContextCompat.getColor(this, R.color.tomato));
                }
                textView3.setText(result);
                textView3.setTextSize(18);
                textView3.setTypeface(null, Typeface.BOLD);
                textView3.setGravity(Gravity.RIGHT);

                linearLayout.addView(textView1);
                linearLayout.addView(textView3);
                linearLayout.addView(textView2);

                cardView.addView(linearLayout);
                layout.addView(cardView);
            }
        } catch (IOException e) {
            Toast.makeText(this, "Except", Toast.LENGTH_SHORT).show();
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    //log the exception
                }
            }
        }

    }
}