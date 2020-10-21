
package com.spacing.models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchForPropertyResultsDataStruct implements Serializable {

    @SerializedName("allDetails")
    @Expose
    private List<AllDetail> allDetails = null;
    @SerializedName("imgs")
    @Expose
    private List<Img> imgs = null;

    public List<AllDetail> getAllDetails() {
        return allDetails;
    }

    public void setAllDetails(List<AllDetail> allDetails) {
        this.allDetails = allDetails;
    }

    public List<Img> getImgs() {
        return imgs;
    }

    public void setImgs(List<Img> imgs) {
        this.imgs = imgs;
    }

}
