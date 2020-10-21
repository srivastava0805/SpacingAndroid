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
import com.spacing.models.PopularCitiesDataStructure;
import com.spacing.networking.APIClient;
import com.spacing.networking.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSelectCity extends Fragment {

    private AppCompatTextView useMyCurrentLocation;
    private AppCompatEditText editTextMyCity;
    private RecyclerView rcvPopularCities;
    private PopularPlacesAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private APIInterface apiInterface;
    private List<PopularCitiesDataStructure> popularCitiesList;
    private List<PopularCitiesDataStructure> mSearchResultsList;
    private RecyclerView mRecylerviewSelectcitySearchedcities;
    private ArrayList<PopularCitiesDataStructure> mSearchResultsListFiltered = new ArrayList<>();
    private boolean useFilter = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.select_city, null);
        findViews(root);

        return root;
    }

    private void findViews(View root) {
        useMyCurrentLocation = root.findViewById(R.id.textview_selectcity_usemylocation);
        editTextMyCity = root.findViewById(R.id.edittext_selectcity_searchcity);
        rcvPopularCities = root.findViewById(R.id.recylerview_selectcity_popularcities);
        mRecylerviewSelectcitySearchedcities = root.findViewById(R.id.recylerview_selectcity_searchedcities);
        if (popularCitiesList == null || popularCitiesList.size() == 0)
            getDataForPopularCities();
        else setAdapter(popularCitiesList, rcvPopularCities);
        setActions();
    }

    private void getDataForPopularCities() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        /**
         GET  popular cities
         **/
        Call<List<PopularCitiesDataStructure>> call = apiInterface.doGetPopularCities();
        call.enqueue(new Callback<List<PopularCitiesDataStructure>>() {
            @Override
            public void onResponse(Call<List<PopularCitiesDataStructure>> call, Response<List<PopularCitiesDataStructure>> response) {
                Log.d("TAG", response.body() + "");
                if (response.isSuccessful() && response.code() == 200) {
                    popularCitiesList = response.body();
                    setAdapter(response.body(), rcvPopularCities);
                } else {
                    Toast.makeText(getActivity(), "Something went wrong, try again later", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<PopularCitiesDataStructure>> call, Throwable t) {
                call.cancel();
            }
        });

    }

    private void setAdapter(List<PopularCitiesDataStructure> cities, RecyclerView theView) {
        theView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        theView.setLayoutManager(layoutManager);
        mAdapter = new PopularPlacesAdapter(cities, new MainActivity.GotUpdateLocationInterface() {
            @Override
            public void onUpdated(String city) {
                editTextMyCity.setText(city);
                useFilter = false;
                replaceToLocalityFragment();
            }
        });
        theView.setAdapter(mAdapter);
    }

    private void setActions() {
        useMyCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((MainActivity) getActivity()).getcity() == null) {
                    ((MainActivity) getActivity()).checkPermissionAndGetLocation(new MainActivity.GotUpdateLocationInterface() {
                        @Override
                        public void onUpdated(String city) {
                            editTextMyCity.setText(city);
                            replaceToLocalityFragment();
                        }
                    });

                } else {
                    editTextMyCity.setText(((MainActivity) getActivity()).getcity());
                    replaceToLocalityFragment();
                }
            }
        });

        editTextMyCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() < 1) {
                    mRecylerviewSelectcitySearchedcities.setVisibility(View.GONE);
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

    private void filterAlreadyPresentList(String searchedString) {
        for (int i = 0; i < mSearchResultsList.size(); i++) {
            if (mSearchResultsList.get(i).getCitynName().toLowerCase().contains(searchedString.toLowerCase())) {
                mSearchResultsListFiltered.add(mSearchResultsList.get(i));
            }
        }
        setAdapter(mSearchResultsListFiltered, mRecylerviewSelectcitySearchedcities);
    }


    private void getCityWithSearchedString(String searchString) {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        /**
         GET  popular cities
         **/
        Call<List<PopularCitiesDataStructure>> call = apiInterface.doGetCitiesBySearchString(searchString);
        call.enqueue(new Callback<List<PopularCitiesDataStructure>>() {
            @Override
            public void onResponse(Call<List<PopularCitiesDataStructure>> call, Response<List<PopularCitiesDataStructure>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    mSearchResultsList = response.body();
                    setAdapter(mSearchResultsList, mRecylerviewSelectcitySearchedcities);
                    mRecylerviewSelectcitySearchedcities.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getActivity(), "Something went wrong, try again later", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<PopularCitiesDataStructure>> call, Throwable t) {
                call.cancel();
            }
        });

    }

    private void replaceToLocalityFragment() {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!editTextMyCity.getText().toString().isEmpty())
                    ((MainActivity) getActivity()).setCity(editTextMyCity.getText().toString());
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentSelectLocality fragment = new FragmentSelectLocality();
                fragmentTransaction.add(R.id.container_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                mRecylerviewSelectcitySearchedcities.setVisibility(View.GONE);
            }
        }, 1000);
    }


}
