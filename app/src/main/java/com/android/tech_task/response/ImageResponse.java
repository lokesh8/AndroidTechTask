package com.android.tech_task.response;

import com.android.tech_task.model.Image;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageResponse {
    @SerializedName("totalHits")
    @Expose
    private Integer totalHits;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("hits")
    @Expose
    private List<Image> images = null;

    public Integer getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(Integer totalHits) {
        this.totalHits = totalHits;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
