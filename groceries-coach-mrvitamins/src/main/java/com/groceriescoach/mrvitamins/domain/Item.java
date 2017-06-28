package com.groceriescoach.mrvitamins.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groceriescoach.core.com.groceriescoach.core.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class Item {

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("link")
    private String link;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("list_price")
    private Double listPrice;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("product_code")
    private String productCode;

    @JsonProperty("image_link")
    private String image;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getListPrice() {
        return listPrice;
    }

    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private MrVitaminsProduct toProduct() {
        MrVitaminsProduct mrVitaminsProduct = new MrVitaminsProduct();
        mrVitaminsProduct.setImageUrl(image);
        mrVitaminsProduct.setName(title);
        mrVitaminsProduct.setDescription(description);
        mrVitaminsProduct.setPrice(price);
        mrVitaminsProduct.setWasPrice(listPrice);
        mrVitaminsProduct.setUrl(link);
        return mrVitaminsProduct;
    }

    static List<MrVitaminsProduct> toProducts(List<Item> items) {
        List<MrVitaminsProduct> products = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(items)) {
            for (Item item : items) {
                products.add(item.toProduct());
            }
        }
        return products;
    }
}
