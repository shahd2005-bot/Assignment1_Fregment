package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityMainBinding;

// Host Activity: implements both fragment interfaces (the only link between fragments)
public class MainActivity extends AppCompatActivity
        implements Fragment2.OnFragment2Listener,
        Fragment3.OnFragment3Listener {

    private static final String TAG_F3 = "fragment3";

    private ActivityMainBinding binding;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, Fragment1.newInstance(null, null))
                    .commit();
        }

        // Activity's Continue button is only for Fragment1 -> Fragment2
        binding.buttonContinue.setOnClickListener(view ->
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, Fragment2.newInstance())
                        .addToBackStack(null)
                        .commit());

        // Show the Activity's button only while Fragment1 is visible
        getSupportFragmentManager().addOnBackStackChangedListener(() ->
                binding.buttonContinue.setVisibility(
                        getSupportFragmentManager().getBackStackEntryCount() == 0
                                ? android.view.View.VISIBLE
                                : android.view.View.GONE));
    }

    // Fragment2 -> Activity -> Fragment3
    @Override
    public void onContinueFromFragment2(String name, String email,
                                        String gender, String extra) {
        this.name = name;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, Fragment3.newInstance(name), TAG_F3)
                .addToBackStack(null)
                .commit();
    }

    // Fragment3 checkbox -> Activity -> Fragment3 button
    @Override
    public void onCheckChanged(boolean isChecked) {
        Fragment3 f = (Fragment3) getSupportFragmentManager().findFragmentByTag(TAG_F3);
        if (f != null) f.setButtonState(isChecked);
    }

    @Override
    public void onFinishClicked() {
        Toast.makeText(this, "Welcome, " + name + "!", Toast.LENGTH_SHORT).show();
    }
}
