package com.psgtech.cholestrol.load_messages;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_messages, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewHolder holder, int position) {
        // Initialize Main Data
        MainData data = dataArrayList.get(position);

        // Set ID on text view
        holder.textViewId.setText(data.getMessageID());
        holder.textViewDate.setText(data.getMessageDate());
        holder.textViewQuery.setText(data.getMessageQuery());
        holder.textViewResponse.setText(data.getMessageResponse());
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Initialize Variable
        ImageView imageView;
        TextView textViewId, textViewDate, textViewQuery, textViewResponse;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            //Assign variables
            textViewId = itemView.findViewById(R.id.textview_id);
            textViewDate = itemView.findViewById(R.id.textview_date);
            textViewQuery = itemView.findViewById(R.id.textview_query);
            textViewResponse = itemView.findViewById(R.id.textview_response);
        }
    }
}
