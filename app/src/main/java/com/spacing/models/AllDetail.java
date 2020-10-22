
package com.spacing.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AllDetail implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("basicDetailId")
    @Expose
    private BasicDetailId basicDetailId;
    @SerializedName("locationId")
    @Expose
    private LocationId locationId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("uniqueID")
    @Expose
    private Integer uniqueID;
    @SerializedName("isConfirmed")
    @Expose
    private Boolean isConfirmed;
    @SerializedName("isDecline")
    @Expose
    private Boolean isDecline;

    private List<Img> propertyImages;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BasicDetailId getBasicDetailId() {
        return basicDetailId;
    }

    public void setBasicDetailId(BasicDetailId basicDetailId) {
        this.basicDetailId = basicDetailId;
    }

    public LocationId getLocationId() {
        return locationId;
    }

    public void setLocationId(LocationId locationId) {
        this.locationId = locationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(Integer uniqueID) {
        this.uniqueID = uniqueID;
    }

    public Boolean getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public Boolean getIsDecline() {
        return isDecline;
    }

    public void setIsDecline(Boolean isDecline) {
        this.isDecline = isDecline;
    }

    public List<Img> getPropertyImages() {
        return propertyImages;
    }

    public void setPropertyImages(List<Img> propertyImages) {
        this.propertyImages = propertyImages;
    }
}
