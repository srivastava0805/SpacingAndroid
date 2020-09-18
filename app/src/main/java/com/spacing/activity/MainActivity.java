package com.spacing.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.spacing.R;
import com.spacing.fragment.FragmentSelectBudget;
import com.spacing.fragment.FragmentVerifyPhoneNumber;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout lookingForProperty;
    private ConstraintLayout lookingToPostProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.looking_for_step1);
        findViews();
    }

    private void findViews() {
        lookingForProperty = findViewById(R.id.constraintlayout_lookingfor_homesearchparent);
        setActionsOnView();
    }

    private void setActionsOnView() {
        lookingForProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentVerifyPhoneNumber fragment = new FragmentVerifyPhoneNumber();
                fragmentTransaction.add(R.id.container_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    public void onBackClicked(View view) {
        onBackPressed();
    }
}