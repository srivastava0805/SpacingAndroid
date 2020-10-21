package com.spacing.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PopularLocalityDataStructure {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("localityName")
    @Expose
    private String localityName;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("cityId")
    @Expose
    private Integer cityId;
    @SerializedName("cityName")
    @Expose
    private String cityName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocalityName() {
        return localityName;
    }

    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


}