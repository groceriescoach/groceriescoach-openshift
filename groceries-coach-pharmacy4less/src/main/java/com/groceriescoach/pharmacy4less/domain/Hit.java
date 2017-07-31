package com.groceriescoach.pharmacy4less.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groceriescoach.core.domain.GroceriesCoachSortType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Hit implements Serializable {

    private static final long serialVersionUID = 7003433147379560304L;
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;
    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;
    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("price")
    private Price price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public static Collection<Pharmacy4LessProduct> toGroceriesCoachProducts(List<Hit> hits, GroceriesCoachSortType sortType) {
        List<Pharmacy4LessProduct> products = new ArrayList<>();
        for (Hit hit : hits) {
            products.add(hit.toGroceriesCoachProduct(sortType));
        }
        return products;
    }

    private Pharmacy4LessProduct toGroceriesCoachProduct(GroceriesCoachSortType sortType) {
        return Pharmacy4LessProduct.Pharmacy4LessProductBuilder.aPharmacy4LessProduct()
                .withName(name)
                .withImageUrl(imageUrl)
                .withPrice(price.getAud().getDefaultCost())
                .withUrl(url)
                .build(sortType);
    }
}
