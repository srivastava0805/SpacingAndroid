
package com.spacing.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocationId implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("project")
    @Expose
    private Object project;
    @SerializedName("sectorNo")
    @Expose
    private String sectorNo;
    @SerializedName("plotNo")
    @Expose
    private Object plotNo;
    @SerializedName("pocketNo")
    @Expose
    private String pocketNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Object getProject() {
        return project;
    }

    public void setProject(Object project) {
        this.project = project;
    }

    public String getSectorNo() {
        return sectorNo;
    }

    public void setSectorNo(String sectorNo) {
        this.sectorNo = sectorNo;
    }

    public Object getPlotNo() {
        return plotNo;
    }

    public void setPlotNo(Object plotNo) {
        this.plotNo = plotNo;
    }

    public String getPocketNo() {
        return pocketNo;
    }

    public void setPocketNo(String pocketNo) {
        this.pocketNo = pocketNo;
    }

}
