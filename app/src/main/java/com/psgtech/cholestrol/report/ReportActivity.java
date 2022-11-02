package com.psgtech.cholestrol.report;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.android.material.snackbar.Snackbar;
import com.psgtech.cholestrol.R;
import com.psgtech.cholestrol.ViewDetailsActivity;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class ReportActivity extends AppCompatActivity {

    private WebView webView;
    ProgressBar progressBar;
    ProgressDialog progressDialog;

    Button buttonDownloadResume, buttonPublishResume;
    ImageButton buttonDownloadResumeTop, buttonPublishResumeTop;

    String fileTitle;
    TextView textViewResumeTitle;

    // Creating object of WebView
    WebView printWeb;

    String mLine = "";

    String resume_id;
    StringBuilder htmlCode;

    String subscribed_or_not;

    String userId, userType, reportType;


    @SuppressLint({"WrongThread", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // REMOVE DARK MODE
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // ACTION BAR
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_webview);
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.gradient));
        getSupportActionBar().setElevation(0);
        // ACTION BAR

        // SUBSCRIBE BUTTON AT THE TOP
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        subscribed_or_not = sp.getString("subscribed_or_not","0");

        userType = getIntent().getStringExtra("user_type");
        userId = getIntent().getStringExtra("user_id");
        reportType = getIntent().getStringExtra("report_type");

        // CHANGE THE ACTION BAR TEXT
        textViewResumeTitle = findViewById(R.id.textview_title);
        textViewResumeTitle.setText(fileTitle);

        // PRINT WEBPAGE
        buttonDownloadResumeTop = findViewById(R.id.button_download_resume_top);
        buttonDownloadResume = findViewById(R.id.button_download_resume);

        buttonDownloadResumeTop.setOnClickListener(this::printFunction);
        buttonDownloadResume.setOnClickListener(this::printFunction);

        progressBar = findViewById(R.id.progress);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setDomStorageEnabled(true);


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new webClient());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
                progressDialog.show();
                if(newProgress==100){
                    progressBar.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
                super.onProgressChanged(view, newProgress);
            }
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {

                String consoleMsg = "Line " + consoleMessage.lineNumber() + " : "+
                        consoleMessage.message();

                // Create a snackbar
                Snackbar.make(webView, consoleMsg, Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();

//                Toast.makeText(ReportActivity.this, consoleMsg, Toast.LENGTH_SHORT).show();
                // Toast.makeText(RunLocalCodeActivity.this, "Console Message: " + consoleMessage.message(), Toast.LENGTH_SHORT).show();
                return super.onConsoleMessage(consoleMessage);
            }
        });


        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[3];
                field[0] = "user_type";
                field[1] = "user_id";
                field[2] = "report_type";
                //Creating array for data
                String[] data = new String[3];
                data[0] = userType;
                data[1] = userId;
                data[2] = reportType;

                PutData putData = new PutData(getResources().getString(R.string.website_address) + "user_details.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        progressBar.setVisibility(View.GONE);
                        String result = putData.getResult();

                        if(result.contains("failed")){
                            Toast.makeText(ReportActivity.this, "Failed.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String selectedTemplatePath = "templates/report.html";
                            try (BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(getAssets().open(selectedTemplatePath), StandardCharsets.UTF_8))) {
                                mLine = reader.lines().collect(Collectors.joining());
                            } catch (IOException e) {
                                //log the exception
                            }

                            String[] arr = result.split(":::");
                            htmlCode = new StringBuilder("<p class='d_name'>" + arr[1] + "</p>");
                            htmlCode.append("<p class='d_age'>" + arr[4] + "</p>");
                            htmlCode.append("<p class='d_gender'>" + arr[5] + "</p>");
                            htmlCode.append("<p class='d_doctor'>" + arr[15] + "</p>");
                            htmlCode.append("<p class='d_message_date'>" + arr[16] + "</p>");
                            htmlCode.append("<p class='d_message_query'>" + arr[17] + "</p>");
                            htmlCode.append("<p class='d_message_response'>" + arr[18] + "</p>");

                            StringBuilder tableCode = new StringBuilder("");
                            StringBuilder chartCodeX = new StringBuilder("<script>var xValues = [");
                            StringBuilder chartCodeY = new StringBuilder("<script>var yValues = [");

                            tableCode.append("<table class='d_table'>");
                            tableCode.append("<tr><th>Date and Time</th><th>Level</th><th>Change</th></tr>");
                            int prevLevel = 0;
                            int diffLevel = 0;
                            for(int i = 19; i < arr.length; i+=2){
                                tableCode.append("<tr><td>" + arr[i] + "</td>");
                                String[] xDate = arr[i].split(" ");
                                String[] xDate1 = xDate[0].split("-");
                                String xValue = xDate1[0] + "." + xDate1[1] + "." + xDate1[2];

                                chartCodeX.append("'" + xDate[0] + "', ");
                                chartCodeY.append("'" + arr[i+1] + "', ");

                                diffLevel = Integer.parseInt(arr[i+1]) - prevLevel;
                                prevLevel = Integer.parseInt(arr[i+1]);
                                if(Integer.parseInt(arr[i+1]) >= 240){
                                    tableCode.append("<td class='color-red'>" + arr[i+1] + "</td><td>" + diffLevel + "</td></tr>");
                                }
                                else if(Integer.parseInt(arr[i+1]) >= 200){
                                    tableCode.append("<td class='color-orange'>" + arr[i+1] + "</td><td>" + diffLevel + "</td></tr>");
                                }
                                else{
                                    tableCode.append("<td class='color-green'>" + arr[i+1] + "</td><td>" + diffLevel + "</td></tr>");
                                }
                                // tableCode.append("<td>" + arr[i+1] + "</td></tr>");
                            }
                            tableCode.append("</table>");
                            chartCodeX.append("];</script>");
                            chartCodeY.append("];</script>");
                            htmlCode.append(chartCodeX);
                            htmlCode.append(chartCodeY);
                            htmlCode.append(mLine);
                            htmlCode.append(tableCode);

                            webView.loadData(htmlCode.toString(), "text/html", null);
                        }
                        //End ProgressBar (Set visibility to GONE)
                    }
                }
                //End Write and Read data with URL
            }
        });

    }



    private void printFunction(View view){

        if (printWeb != null) {
            // Calling createWebPrintJob()
            PrintTheWebPage(printWeb);
        } else {
            // Showing Toast message to user
            Toast.makeText(ReportActivity.this, "WebPage not fully loaded", Toast.LENGTH_SHORT).show();
        }

    }


    // PRINT WEBVIEW
    // object of print job
    PrintJob printJob;

    // a boolean to check the status of printing
    boolean printBtnPressed = false;

    private void PrintTheWebPage(WebView webView) {

        // set printBtnPressed true
        printBtnPressed = true;

        // Creating  PrintManager instance
        PrintManager printManager = (PrintManager) this
                .getSystemService(ReportActivity.PRINT_SERVICE);

        // setting the name of job
        // String jobName = getString(R.string.app_name) + " webpage" + webView.getUrl();
        String jobName = "Report";

        // Creating  PrintDocumentAdapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

        // Create a print job with name and adapter instance
        assert printManager != null;
        printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (printJob != null && printBtnPressed) {
            if (printJob.isCompleted()) {
                // Showing Toast Message - Completed
                Toast.makeText(this, "Resume saved successfully!!!", Toast.LENGTH_SHORT).show();
            } else if (printJob.isStarted()) {
                // Showing Toast Message - Started
//                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

            } else if (printJob.isBlocked()) {
                // Showing Toast Message - Blocked
//                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

            } else if (printJob.isCancelled()) {
                // Showing Toast Message - Cancelled
//                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

            } else if (printJob.isFailed()) {
                // Showing Toast Message - Failed
                Toast.makeText(this, "Failed to save the resume.", Toast.LENGTH_SHORT).show();

            } else if (printJob.isQueued()) {
                // Showing Toast Message - Queued
//                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

            }
            // set printBtnPressed false
            printBtnPressed = false;
        }
    }
    // PRINT WEBVIEW - CLOSED

    public class webClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            // initializing the printWeb Object
            printWeb = webView;
        }
    }
}