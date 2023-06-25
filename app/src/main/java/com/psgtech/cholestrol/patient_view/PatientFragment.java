package com.psgtech.cholestrol.patient_view;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psgtech.cholestrol.AddDataActivity;
import com.psgtech.cholestrol.DoctorSuggestionFragment;
import com.psgtech.cholestrol.DoctorViewActivity;
import com.psgtech.cholestrol.HomeActivity;
import com.psgtech.cholestrol.LoginActivity;
import com.psgtech.cholestrol.PatientViewActivity;
import com.psgtech.cholestrol.R;
import com.psgtech.cholestrol.UpdateDoctorActivity;
import com.psgtech.cholestrol.ViewDetailsActivity;
import com.psgtech.cholestrol.report.ReportActivity;

public class PatientFragment extends Fragment {

    CardView cardViewCholestrolDetails, cardViewProfileDetails, cardViewDoctorDetails, cardViewCaretakerDetails,
    cardViewDoctorSuggestions, cardViewUpdateDetails;
    String cookie_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient, container, false);

        cookie_id = requireActivity().getIntent().getStringExtra("cookie_id");

        cardViewCholestrolDetails = view.findViewById(R.id.cardview_cholestrol_details);
        cardViewProfileDetails = view.findViewById(R.id.cardview_profile_details);
        cardViewDoctorDetails = view.findViewById(R.id.cardview_assigned_doctor);
        cardViewCaretakerDetails = view.findViewById(R.id.cardview_assigned_caretaker);
        cardViewDoctorSuggestions = view.findViewById(R.id.cardview_doctor_suggestions);
        cardViewUpdateDetails = view.findViewById(R.id.cardview_update_details);

        cardViewCholestrolDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(requireActivity(), AddDataActivity.class);
                assert i != null;
                i.putExtra("user_id", cookie_id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        cardViewProfileDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(requireActivity(), ViewDetailsActivity.class);
                assert i != null;
                i.putExtra("user_type", "3");
                i.putExtra("user_id", cookie_id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        cardViewDoctorDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = new OpenDoctorFragment();
                Bundle arguments = new Bundle();
                arguments.putString("cookie_id", cookie_id);
                selectedFragment.setArguments(arguments);
                ((PatientViewActivity) requireActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
            }
        });

        cardViewCaretakerDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = new OpenCaretakerFragment();
                Bundle arguments = new Bundle();
                arguments.putString("cookie_id", cookie_id);
                selectedFragment.setArguments(arguments);
                ((PatientViewActivity) requireActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
            }
        });

        cardViewDoctorSuggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = new DoctorSuggestionFragment();
                Bundle arguments = new Bundle();
                arguments.putString("cookie_id", cookie_id);
                selectedFragment.setArguments(arguments);
                ((PatientViewActivity) requireActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
            }
        });

        cardViewUpdateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(requireActivity(), UpdateDoctorActivity.class);
                assert i != null;
                i.putExtra("user_type", "3");
                i.putExtra("user_id", cookie_id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        return view;
    }
}