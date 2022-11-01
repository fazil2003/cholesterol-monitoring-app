package com.psgtech.cholestrol.load_data;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.psgtech.cholestrol.R;
import com.psgtech.cholestrol.ViewDetailsActivity;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    // Initialize Variable
    private ArrayList<MainData> dataArrayList;
    private Fragment activity;

    // Create Constructor
    public MainAdapter(Fragment activity, ArrayList<MainData> dataArrayList){
        this.activity = activity;
        this.dataArrayList = dataArrayList;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        // Initialize View
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_profile, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewHolder holder, int position) {
        // Initialize Main Data
        MainData data = dataArrayList.get(position);

        // Set ID on text view
        holder.textViewId.setText(data.getId());
        holder.textViewType.setText(data.getType());
        holder.textViewName.setText(data.getName());
        holder.textViewEmail.setText(data.getEmail());
        holder.textViewPhone.setText("+91 " + data.getPhone());
        holder.textViewDate.setText(data.getDate());

        holder.answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity.requireActivity(), ViewDetailsActivity.class);
                assert i != null;
                i.putExtra("user_type", data.getType());
                i.putExtra("user_id", data.getId());
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(i);
            }
        });

        holder.shareButton.setVisibility(View.GONE);
        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentString = "https://www.aloask.com/quesans/view_answer.php?id="+data.getId();
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String body = currentString;
                String sub = currentString;
                myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                myIntent.putExtra(Intent.EXTRA_TEXT,body);
                activity.startActivity(Intent.createChooser(myIntent, "Share Using"));
            }
        });




    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Initialize Variable
        ImageView imageView;
        TextView textViewId, textViewType, textViewName, textViewEmail, textViewPhone, textViewDate;
        Button answerButton, shareButton;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            //Assign variables
            textViewId = itemView.findViewById(R.id.textview_id);
            textViewType = itemView.findViewById(R.id.textview_type);
            textViewName = itemView.findViewById(R.id.textview_name);
            textViewEmail = itemView.findViewById(R.id.textview_email);
            textViewPhone = itemView.findViewById(R.id.textview_phone);
            textViewDate = itemView.findViewById(R.id.textview_date);

            answerButton = itemView.findViewById(R.id.view_button);
            shareButton = itemView.findViewById(R.id.share_button);
        }
    }
}
