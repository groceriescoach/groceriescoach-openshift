package com.groceriescoach.woolworths.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ChildCategory implements Serializable {

    private static final long serialVersionUID = -7026875569860019287L;

    @JsonProperty("Name")
    private String name;
    @JsonProperty("UrlFriendlyName")
    private String urlFriendlyName;
    @JsonProperty("ImageFile")
    private String imageFile;
    @JsonProperty("ProductCount")
    private int productCount;
    @JsonProperty("IsRestricted")
    private Boolean restricted;
    @JsonProperty("IsEligibleForRanking")
    private Boolean isEligibleForRanking;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlFriendlyName() {
        return urlFriendlyName;
    }

    public void setUrlFriendlyName(String urlFriendlyName) {
        this.urlFriendlyName = urlFriendlyName;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public Boolean getRestricted() {
        return restricted;
    }

    public void setRestricted(Boolean restricted) {
        this.restricted = restricted;
    }

    public Boolean getEligibleForRanking() {
        return isEligibleForRanking;
    }

    public void setEligibleForRanking(Boolean eligibleForRanking) {
        isEligibleForRanking = eligibleForRanking;
    }
}


