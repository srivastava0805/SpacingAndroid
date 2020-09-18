package com.spacing.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.spacing.R;

public class FragmentTellUsYourRequirement extends Fragment {

    private ConstraintLayout viewRent;
    private ConstraintLayout viewBuy;
    private ConstraintLayout viewPg_CoLiving;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tell_us_your_requirement, null);
        findViews(root);
        return root;
    }

    private void findViews(View root) {
        viewRent = root.findViewById(R.id.constraintlayout_tellusrequirement_rent);
        viewBuy = root.findViewById(R.id.constraintlayout_tellusrequirement_buy);
        viewPg_CoLiving = root.findViewById(R.id.constraintlayout_tellusrequirement_pgcoliving);
        setActions();
    }

    private void setActions() {
        viewRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentSelectCity fragment = new FragmentSelectCity();
                fragmentTransaction.add(R.id.container_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        viewBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewRent.performClick();
            }
        });

        viewPg_CoLiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewRent.performClick();
            }
        });
    }
}
