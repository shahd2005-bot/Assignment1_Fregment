package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment3Binding;

public class Fragment3 extends Fragment {


    private static final String ARG_NAME = "name";

    public interface OnFragment3Listener {
        void onCheckChanged(boolean isChecked);  // checkbox -> Activity
        void onFinishClicked();                  // button   -> Activity
    }

    private OnFragment3Listener mListener;
    private Fragment3Binding binding;
    private String mName = "";

    public Fragment3() {
        // Required empty public constructor
    }

    public static Fragment3 newInstance(String name) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment3Listener) {
            mListener = (OnFragment3Listener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragment3Listener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(ARG_NAME, "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = Fragment3Binding.inflate(inflater, container, false);

        binding.textViewName.setText("Welcome, " + mName);
        setButtonState(binding.checkBoxConfirm.isChecked()); // disabled + "Continue"

        binding.checkBoxConfirm.setOnCheckedChangeListener((btn, isChecked) ->
                mListener.onCheckChanged(isChecked));
        binding.buttonContinueF3.setOnClickListener(v -> mListener.onFinishClicked());

        return binding.getRoot();
    }

    // Called by the host Activity after it receives onCheckChanged()
    public void setButtonState(boolean enabled) {
        if (binding == null) return;
        binding.buttonContinueF3.setEnabled(enabled);
        binding.buttonContinueF3.setText(enabled ? "Finish" : "Continue");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
