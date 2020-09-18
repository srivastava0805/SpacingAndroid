package com.spacing.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jem.rubberpicker.RubberRangePicker;
import com.spacing.R;

public class FragmentSelectBudget extends Fragment {


    private RubberRangePicker rangeSeekBarBudget;
    private AppCompatTextView textStartRangeBudget;
    private AppCompatTextView textEndRangeBudget;
    private Button btnNext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.select_budget, null);
        findViews(root);
        return root;

    }

    private void findViews(View root) {
        rangeSeekBarBudget = root.findViewById(R.id.rangeSeekBar);
        textStartRangeBudget = root.findViewById(R.id.textview_selectbudget_budgetstartrange);
        textEndRangeBudget = root.findViewById(R.id.textview_selectbudget_budgetendrange);
        btnNext = root.findViewById(R.id.button_selectbudget_next);
        setActionsOnView();
    }

    private void setActionsOnView() {
        rangeSeekBarBudget.setOnRubberRangePickerChangeListener(new RubberRangePicker.OnRubberRangePickerChangeListener() {
            @Override
            public void onStopTrackingTouch(RubberRangePicker rubberRangePicker, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(RubberRangePicker rubberRangePicker, boolean b) {
            }

            @Override
            public void onProgressChanged(RubberRangePicker rubberRangePicker, int startValue, int endValue, boolean b) {
                if (endValue == rubberRangePicker.getMax())
                    textEndRangeBudget.setText(endValue + "k+");
                else
                    textEndRangeBudget.setText(endValue + "k");
                textStartRangeBudget.setText(startValue + "k");
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_main, new FragmentSelectConfiguration());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
