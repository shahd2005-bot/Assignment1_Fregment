package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.Fragment2Binding;

public class Fragment2 extends Fragment {

    // Listener interface with callback to the host Activity
    public interface OnFragment2Listener {
        void onContinueFromFragment2(String name, String email,
                                     String gender, String extra);
    }

    private OnFragment2Listener mListener;
    private Fragment2Binding binding;

    public Fragment2() {
        // Required empty public constructor
    }

    public static Fragment2 newInstance() {
        return new Fragment2();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment2Listener) {
            mListener = (OnFragment2Listener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragment2Listener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = Fragment2Binding.inflate(inflater, container, false);

        binding.buttonContinueF2.setOnClickListener(v -> {
            String name = binding.editText.getText().toString().trim();
            String email = binding.editTextEmail.getText().toString().trim();
            String extra = binding.editTextUniversity.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                binding.editText.setError("Enter name!");
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.editTextEmail.setError("Enter a valid email");
                return;
            }

            String gender = "";
            int id = binding.radioGroupGender.getCheckedRadioButtonId();
            if (id == R.id.radioMale) gender = "Male";
            else if (id == R.id.radioFemale) gender = "Female";

            mListener.onContinueFromFragment2(name, email, gender, extra);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
