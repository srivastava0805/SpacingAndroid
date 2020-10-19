package com.spacing.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.spacing.R;

public class FragmentVerifyPhoneNumber extends Fragment {

    private AppCompatButton btnSubmit;
    private AppCompatTextView txtSkip;
    private AppCompatEditText edtPhoneNumber;
    private String regexMobileNumber = "^[6-9][0-9]{9}$";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.verify_number_or_skip, null);
        findViews(root);
        return root;
    }

    private void findViews(View root) {
        btnSubmit = root.findViewById(R.id.button_verifynumber_submit);
        txtSkip = root.findViewById(R.id.textview_verifynumber_skip);
        edtPhoneNumber = root.findViewById(R.id.edittext_verifynumber_mobilenumber);
        setActions();
    }

    private void setActions() {
        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceTellUsRequirementFragment();

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNumber = edtPhoneNumber.getText().toString().trim();
                if (mobileNumber.isEmpty()) {
                    edtPhoneNumber.setError(getString(R.string.mobile_number_empty));
                } else if (mobileNumber.length() < 10) {
                    edtPhoneNumber.setError(getString(R.string.mobile_number_length_smaller));
                } else if (!mobileNumber.matches(regexMobileNumber)) {
                    edtPhoneNumber.setError(getString(R.string.mobile_number_regex_not_valid));
                } else replaceTellUsRequirementFragment();
            }
        });
    }

    private void replaceTellUsRequirementFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentTellUsYourRequirement fragment = new FragmentTellUsYourRequirement();
        fragmentTransaction.add(R.id.container_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
