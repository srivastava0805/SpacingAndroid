package com.spacing.networking;


import com.spacing.models.PopularCitiesDataStructure;
import com.spacing.models.PopularLocalityDataStructure;
import com.spacing.models.SearchForPropertyResultsDataStruct;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("api/CityManage/Popularcity")
    Call<List<PopularCitiesDataStructure>> doGetPopularCities();

    @GET("api/CityManage/PopularLocality/{cityName}")
    Call<List<PopularLocalityDataStructure>> doGetPopularLocality(@Path("cityName") String cityName);

    @GET("api/CityManage/Suggestcity/{cityName}")
    Call<List<PopularCitiesDataStructure>> doGetCitiesBySearchString(@Path("cityName") String citytext);

    @GET("api/CityManage/Suggestlocality/{localityName}/{cityName}")
    Call<List<PopularLocalityDataStructure>> doGetLocalityBySearchString(@Path("localityName") String locality ,
                                                                         @Path("cityName") String city);

    @GET("api/SerchFilter/SearchBy/{City}/{locality}/{Price}/{Bhk}")
    Call<SearchForPropertyResultsDataStruct> diGetPropertiesByFilter(@Path("City") String city,
    @Path("locality") String locality,
    @Path("Price") String price,
    @Path("Bhk") String bhk);

}