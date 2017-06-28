package com.groceriescoach.mrvitamins.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

public class MrVitaminsSearchResult implements Serializable {

    private static final long serialVersionUID = -6541884730040308932L;

    private static final Logger logger = LoggerFactory.getLogger(MrVitaminsSearchResult.class);


    @JsonProperty("totalItems")
    private int totalItems;
    @JsonProperty("startIndex")
    private int startIndex;
    @JsonProperty("itemsPerPage")
    private int itemsPerPage;
    @JsonProperty("currentItemCount")
    private int currentItemCount;
    @JsonProperty("items")
    private List<Item> items;


    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public int getCurrentItemCount() {
        return currentItemCount;
    }

    public void setCurrentItemCount(int currentItemCount) {
        this.currentItemCount = currentItemCount;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<MrVitaminsProduct> toProducts() {
        return Item.toProducts(items);
    }
}
