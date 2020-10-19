package com.spacing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PopularCitiesDataStructure {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("citynName")
    @Expose
    private String citynName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCitynName() {
        return citynName;
    }

    public void setCitynName(String citynName) {
        this.citynName = citynName;
    }

}