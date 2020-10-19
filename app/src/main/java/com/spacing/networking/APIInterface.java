package com.spacing.networking;


import com.spacing.PopularCitiesDataStructure;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("api/CityManage/Popularcity")
    Call<List<PopularCitiesDataStructure>> doGetPopularCities();

}