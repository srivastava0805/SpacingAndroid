package com.spacing.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import com.spacing.PopularCitiesDataStructure;
import com.spacing.R;
import com.spacing.activity.MainActivity;
import com.spacing.adapters.PopularPlacesAdapter;
import com.spacing.networking.APIClient;
import com.spacing.networking.APIInterface;

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
        if (popularCitiesList == null || popularCitiesList.size() == 0)
            getDataForPopularCities();
        else setAdapter(popularCitiesList);
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
                    setAdapter(response.body());
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

    private void setAdapter(List<PopularCitiesDataStructure> cities) {
        rcvPopularCities.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        rcvPopularCities.setLayoutManager(layoutManager);
        mAdapter = new PopularPlacesAdapter(cities);
        rcvPopularCities.setAdapter(mAdapter);
    }

    private void setActions() {
        useMyCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((MainActivity) getActivity()).getCityByLocation() == null) {
                    ((MainActivity) getActivity()).checkPermissionAndGetLocation(new MainActivity.GotUpdateLocationInterface() {
                        @Override
                        public void onUpdated(String city) {
                            editTextMyCity.setText(city);
                            replaceToLocalityFragment();
                        }
                    });

                } else {
                    editTextMyCity.setText(((MainActivity) getActivity()).getCityByLocation());
                    replaceToLocalityFragment();
                }
            }
        });

    }

    private void replaceToLocalityFragment() {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentSelectLocality fragment = new FragmentSelectLocality();
                fragmentTransaction.add(R.id.container_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }, 1000);
    }


}
