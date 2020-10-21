package com.spacing.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.spacing.R;
import com.spacing.activity.MainActivity;
import com.spacing.activity.ViewPropertiesActivity;
import com.spacing.models.SearchForPropertyResultsDataStruct;
import com.spacing.networking.APIClient;
import com.spacing.networking.APIInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSelectConfiguration extends Fragment {
    private TextView oneRoomKitchen;
    private TextView oneBathRoomHallKitchen;
    private TextView twoBathRoomHallKitchen;
    private TextView threeBathRoomHallKitchen;
    private TextView threeBathRoomHallKitchenPlus;
    private Button btnNext;
    private APIInterface apiInterface;
    private SearchForPropertyResultsDataStruct searchResultsForPropertyQuery;
    private ProgressDialog loadingCityProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.select_confugration, null);
        findViews(root);
        return root;

    }

    private void findViews(View root) {
        oneRoomKitchen = root.findViewById(R.id.textview_selectconfiguration_1RK);
        oneBathRoomHallKitchen = root.findViewById(R.id.textview_selectconfiguration_1BHK);
        twoBathRoomHallKitchen = root.findViewById(R.id.textview_selectconfiguration_2BHK);
        threeBathRoomHallKitchen = root.findViewById(R.id.textview_selectconfiguration_3Bhk);
        threeBathRoomHallKitchenPlus = root.findViewById(R.id.textview_selectconfiguration_3Bhkplus);
        btnNext = root.findViewById(R.id.button_selectconfiguration_next);
        setActionsOnView();
    }

    private void setActionsOnView() {
        oneRoomKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedOrUnselected(view);

            }
        });
        oneBathRoomHallKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedOrUnselected(view);
            }
        });
        twoBathRoomHallKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedOrUnselected(view);
            }
        });
        threeBathRoomHallKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedOrUnselected(view);
            }
        });
        threeBathRoomHallKitchenPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedOrUnselected(view);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> arrayListOfConfiguration = new ArrayList<>();
                if (oneRoomKitchen.isSelected()) {
                    arrayListOfConfiguration.add(oneRoomKitchen.getText().toString());
                }

                if (oneBathRoomHallKitchen.isSelected()) {
                    arrayListOfConfiguration.add(oneBathRoomHallKitchen.getText().toString());
                }

                if (twoBathRoomHallKitchen.isSelected()) {
                    arrayListOfConfiguration.add(twoBathRoomHallKitchen.getText().toString());
                }

                if (threeBathRoomHallKitchen.isSelected()) {
                    arrayListOfConfiguration.add(threeBathRoomHallKitchen.getText().toString());
                }

                if (threeBathRoomHallKitchenPlus.isSelected()) {
                    arrayListOfConfiguration.add(threeBathRoomHallKitchenPlus.getText().toString());
                }

                getDataForPopularCities();
            }
        });
    }

    private void setSelectedOrUnselected(View view) {
        if (view.isSelected()) {
            view.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.curved_corner_black_stroke));
            view.setSelected(false);
            ((TextView) view).setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        } else {
            view.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.curved_corner_filled_white_stroke));
            view.setSelected(true);
            ((TextView) view).setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        }
    }


    private void getDataForPopularCities() {
        MainActivity ctx = ((MainActivity) getActivity());
        apiInterface = APIClient.getClient().create(APIInterface.class);
        /**
         GET  Search Results
         **/
        loadingCityProgressBar = new ProgressDialog(getActivity());
        loadingCityProgressBar.setCancelable(true);
        loadingCityProgressBar.setMessage(getString(R.string.wait__));
        loadingCityProgressBar.show();
        Call<SearchForPropertyResultsDataStruct> call = apiInterface.diGetPropertiesByFilter(ctx.getcity(), ctx.getLocality(),
                ctx.getEndBudget(), "2Bhk");
        call.enqueue(new Callback<SearchForPropertyResultsDataStruct>() {
            @Override
            public void onResponse(Call<SearchForPropertyResultsDataStruct> call, Response<SearchForPropertyResultsDataStruct> response) {
                loadingCityProgressBar.dismiss();
                if (response.isSuccessful() && response.code() == 200) {
                    searchResultsForPropertyQuery = response.body();
                    if (searchResultsForPropertyQuery.getAllDetails().size() > 0) {
                        startActivity(new Intent(getActivity(), ViewPropertiesActivity.class));
                        getActivity().finish();
                    } else {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("No Data")
                                .setMessage(getResources().getString(R.string.no_matching_property))
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Something went wrong, try again later", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SearchForPropertyResultsDataStruct> call, Throwable t) {
                call.cancel();
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
