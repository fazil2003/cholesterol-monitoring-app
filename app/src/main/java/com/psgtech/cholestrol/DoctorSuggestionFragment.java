package com.psgtech.cholestrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.psgtech.cholestrol.R;
import com.psgtech.cholestrol.load_messages.MainAdapter;
import com.psgtech.cholestrol.load_messages.MainData;
import com.psgtech.cholestrol.load_messages.MainInterface;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class DoctorSuggestionFragment extends Fragment {

    //Initialize Variable
    NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<MainData> dataArrayList = new ArrayList<>();
    MainAdapter adapter;
    // start is the start, limit is limit, cookie_id is cookie_id
    // page defines home, explore or profile
    // project_id is for view question
    // q is for query in search page

    // 1-Open Saved, 2-Search, 3-ViewProject

    int start = 0, limit = 10;
    String cookie_id = "1";

    TextView textViewMaximumProjects, textViewNoInternetConnection;

    EditText editTextMessage;
    Button buttonSendMessage;

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_suggestion, container, false);

        cookie_id = requireActivity().getIntent().getStringExtra("cookie_id");

        textViewMaximumProjects = view.findViewById(R.id.textview_maximum_projects);
        textViewMaximumProjects.setTextSize(24);
        textViewNoInternetConnection = view.findViewById(R.id.textview_no_internet_connection);

        //Assign variable
        nestedScrollView = view.findViewById(R.id.scroll_view);
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);

        editTextMessage = view.findViewById(R.id.edittext_message);
        buttonSendMessage = view.findViewById(R.id.button_send_message);
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
                        data[0] = cookie_id;
                        data[1] = message;

                        PutData putData = new PutData(getResources().getString(R.string.website_address) + "add_query.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progressBar.setVisibility(View.GONE);
                                String result = putData.getResult();

                                if(result.contains("failed")){
                                    Toast.makeText(requireActivity(), "Failed.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(requireActivity(), "Success.", Toast.LENGTH_SHORT).show();

                                }
                                //End ProgressBar (Set visibility to GONE)
                            }
                        }
                        //End Write and Read data with URL
                    }
                });

            }
        });

//        Bundle arguments = getArguments();
//        assert arguments != null;
//        String searchQuery = arguments.getString("q");
//        if(!searchQuery.equals("")){
//            q = searchQuery;
//            page = 4;
//            //Toast.makeText(getActivity(), q, Toast.LENGTH_SHORT).show();
//        }

        // Initialize Adapter
        adapter = new MainAdapter(DoctorSuggestionFragment.this, dataArrayList);

        // Set Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity().getApplicationContext()));

        // Set Adapter
        recyclerView.setAdapter(adapter);

        if(isNetworkConnected()){
            // Create get data method
            getData(cookie_id, start, limit);
        }
        else{
            progressBar.setVisibility(View.GONE);
            textViewNoInternetConnection.setVisibility(View.VISIBLE);
        }

        return view;
    }


    // FOR CHECKING THE NETWORK CONNECTION
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    private void getData(String cookie_id, int start, int limit) {

        String website_address = getResources().getString(R.string.website_address);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(website_address)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        // Create main interface
        MainInterface mainInterface = retrofit.create(MainInterface.class);

        // Initialize Call
        Call<String> call = mainInterface.STRING_CALL(cookie_id, start, limit);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // Check condition
                if(response.isSuccessful() && response.body() != null){
                    // When response is successful and not empty
                    // Hide progress bar
                    progressBar.setVisibility(View.GONE);

                    try {
                        // Initialize JSON array
                        JSONArray jsonArray = new JSONArray(response.body());

                        // Parse Json array
                        parseResult(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void parseResult(JSONArray jsonArray) {

        if(jsonArray.length() == 0){
            // Show the number of projects.
            textViewNoInternetConnection.setVisibility(View.VISIBLE);
            textViewNoInternetConnection.setText("No Messages Found.");
            // Show the number of projects
        }

        textViewMaximumProjects.setText("\uD83D\uDCC1 Messages");

        // Use For Loop
        for(int i=0; i<jsonArray.length(); i++){
            try {
                // Initialize json object
                JSONObject object = jsonArray.getJSONObject(i);

                // Initialize Main Data
                MainData data = new MainData();

                // Set ID of Question
                data.setMessageID(object.getString("id"));
                data.setMessageDate(object.getString("date"));
                data.setMessageDoctorID(object.getString("doctor_id"));
                data.setMessagePatientID(object.getString("patient_id"));
                data.setMessageQuery(object.getString("query"));
                data.setMessageResponse(object.getString("response"));

                // Add data in array list
                dataArrayList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            // Initialize Adapter
            adapter = new MainAdapter(DoctorSuggestionFragment.this, dataArrayList);

            // Set adapter
            recyclerView.setAdapter(adapter);

        }
    }

}