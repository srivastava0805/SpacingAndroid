package com.spacing.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spacing.R;
import com.spacing.activity.MainActivity;
import com.spacing.adapters.PopularPlacesAdapter;
import com.spacing.models.PopularLocalityDataStructure;
import com.spacing.networking.APIClient;
import com.spacing.networking.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSelectLocality extends Fragment {

    private AppCompatTextView useMyCurrentLocation;
    private AppCompatEditText editTextMyLocality;
    private List<PopularLocalityDataStructure> popularLocalityList;
    private APIInterface apiInterface;
    private LinearLayoutManager layoutManager;
    private PopularPlacesAdapter mAdapter;
    private RecyclerView rcvPopularLocality;
    private List<PopularLocalityDataStructure> mSearchResultsList;
    private List<PopularLocalityDataStructure> mSearchResultsListFiltered = new ArrayList<>();
    private RecyclerView mRecylerviewSelectlocalitySearchedlocalities;
    private String cityName;
    private boolean useFilter = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.select_locality, null);
        cityName = ((MainActivity) getActivity()).getcity();
        if (cityName == null || cityName.isEmpty())
            return root;
        findViews(root);
        return root;
    }

    private void findViews(View root) {
        useMyCurrentLocation = root.findViewById(R.id.textview_selectlocality_usemylocation);
        editTextMyLocality = root.findViewById(R.id.edittext_selectlocality_searchcity);
        rcvPopularLocality = root.findViewById(R.id.recylerview_selectlocality_popularlocalities);
        setActions();
        if (popularLocalityList == null || popularLocalityList.size() == 0)
            getDataForPopularCities();
        else setAdapter(popularLocalityList, rcvPopularLocality);
        setActions();
        mRecylerviewSelectlocalitySearchedlocalities = root.findViewById(R.id.recylerview_selectlocality_searchedlocalities);
    }

    private void setAdapter(List<PopularLocalityDataStructure> cities, RecyclerView theView) {
        theView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        theView.setLayoutManager(layoutManager);
        mAdapter = new PopularPlacesAdapter(cities, new MainActivity.GotUpdateLocationInterface() {
            @Override
            public void onUpdated(String city) {
                useFilter = false;
                editTextMyLocality.setText(city);
                replaceToLocalityFragment();
            }
        });
        theView.setAdapter(mAdapter);
    }

    private void getDataForPopularCities() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        /**
         GET  popular cities
         **/
        Call<List<PopularLocalityDataStructure>> call = apiInterface.doGetPopularLocality(cityName);
        call.enqueue(new Callback<List<PopularLocalityDataStructure>>() {
            @Override
            public void onResponse(Call<List<PopularLocalityDataStructure>> call, Response<List<PopularLocalityDataStructure>> response) {
                Log.e("Localities", response.body() + "");
                if (response.isSuccessful() && response.code() == 200) {
                    popularLocalityList = response.body();
                    setAdapter(response.body(), rcvPopularLocality);
                } else {
                    Toast.makeText(getActivity(), "Something went wrong, try again later", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<PopularLocalityDataStructure>> call, Throwable t) {
                call.cancel();
            }
        });

    }

    private void filterAlreadyPresentList(String searchedString) {
        for (int i = 0; i < mSearchResultsList.size(); i++) {
            if (mSearchResultsList.get(i).getLocalityName().toLowerCase().contains(searchedString.toLowerCase())) {
                mSearchResultsListFiltered.add(mSearchResultsList.get(i));
            }
        }
        setAdapter(mSearchResultsListFiltered, mRecylerviewSelectlocalitySearchedlocalities);
    }


    private void getCityWithSearchedString(String searchString) {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        /**
         GET  popular cities
         **/
        Call<List<PopularLocalityDataStructure>> call = apiInterface.doGetLocalityBySearchString(searchString, cityName);
        call.enqueue(new Callback<List<PopularLocalityDataStructure>>() {
            @Override
            public void onResponse(Call<List<PopularLocalityDataStructure>> call, Response<List<PopularLocalityDataStructure>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    mSearchResultsList = response.body();
                    setAdapter(mSearchResultsList, mRecylerviewSelectlocalitySearchedlocalities);
                    mRecylerviewSelectlocalitySearchedlocalities.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getActivity(), "Something went wrong, try again later", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<PopularLocalityDataStructure>> call, Throwable t) {
                call.cancel();
            }
        });

    }

    private void setActions() {
        useMyCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((MainActivity) getActivity()).getLocality() == null) {
                    ((MainActivity) getActivity()).checkPermissionAndGetLocation(city -> {
                        editTextMyLocality.setText(city);
                        replaceToLocalityFragment();
                    });

                } else {
                    editTextMyLocality.setText(((MainActivity) getActivity()).getLocality());
                    replaceToLocalityFragment();
                }
            }
        });

        editTextMyLocality.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() < 1) {
                    mRecylerviewSelectlocalitySearchedlocalities.setVisibility(View.GONE);
                    mSearchResultsList = null;
                    useFilter = true;
                    return;
                }
                if (useFilter)
                    if (editable.toString().length() > 0 && mSearchResultsList == null) {
                        getCityWithSearchedString(editable.toString());
                    } else {
                        mSearchResultsListFiltered.clear();
                        filterAlreadyPresentList(editable.toString());
                    }

            }
        });

    }

    private void replaceToLocalityFragment() {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!editTextMyLocality.getText().toString().isEmpty())
                    ((MainActivity) getActivity()).setLocality(editTextMyLocality.getText().toString());
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentSelectBudget fragment = new FragmentSelectBudget();
                fragmentTransaction.add(R.id.container_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                mRecylerviewSelectlocalitySearchedlocalities.setVisibility(View.GONE);
            }
        }, 1000);
    }
}
