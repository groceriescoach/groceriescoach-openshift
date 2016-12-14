package com.groceriescoach.priceline.domain;


import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.Product;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.groceriescoach.core.domain.Store.Priceline;

public class PricelineProduct {

    private String name;
    private String brand;
    private String image;
    private Double price;
    private Double savings;
    private Double wasPrice;



    public static PricelineProduct fromProductElement(Element productElement) {

        PricelineProduct pricelineProduct = new PricelineProduct();

        pricelineProduct.setName(extractNameFromProductElement(productElement));
        pricelineProduct.setBrand(extractBrandFromProductElement(productElement));
        pricelineProduct.setImage(extractImageFromProductElement(productElement));
        pricelineProduct.setPrice(extractPriceFromProductElement(productElement));
        pricelineProduct.setWasPrice(extractWasPriceFromProductElement(productElement));
        pricelineProduct.setSavings(pricelineProduct.calculateSavings());

        return pricelineProduct;
    }

    private Double calculateSavings() {
        if (Objects.nonNull(price) && Objects.nonNull(wasPrice)) {
            return wasPrice - price;
        } else {
            return null;
        }
    }

    private static Double extractWasPriceFromProductElement(Element productElement) {
        Elements oldPriceElements = productElement.select(".old-price .price");
        if (oldPriceElements.isEmpty()) {
            return null;
        } else {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(oldPriceElements.text()));
        }
    }

    private static String extractBrandFromProductElement(Element productElement) {
        return productElement.select(".product-brand").get(0).text();
    }

    private static String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product-link").get(0).text();
    }

    private static Double extractSavingsFromProductElement(Element productElement) {
        Elements savingsElements = productElement.select(".Save");
        if (!savingsElements.isEmpty()) {
            Element savingsSpan = savingsElements.get(0);
            List<TextNode> textNodes = savingsSpan.textNodes();
            for (TextNode textNode : textNodes) {
                if (StringUtils.isNotBlank(textNode.text())) {
                    return Double.parseDouble(StringUtils.removeCurrencySymbols(textNode.text().replaceAll("SAVE", "")));
                }
            }
        }
        return 0D;
    }

    private static String extractImageFromProductElement(Element productElement) {
        return productElement.select("img").get(0).attr("src");
    }

    private static Double extractPriceFromProductElement(Element productElement) {
        Elements regularPriceElements = productElement.select(".regular-price .price");
        if (regularPriceElements.isEmpty()) {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(productElement.select(".special-price .price").text()));
        } else {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(regularPriceElements.text()));
        }
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSavings() {
        return savings;
    }

    public void setSavings(Double savings) {
        this.savings = savings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getWasPrice() {
        return wasPrice;
    }

    public void setWasPrice(Double wasPrice) {
        this.wasPrice = wasPrice;
    }

    public Product toGroceriesCoachProduct() {
        Product product = new Product();
        product.setName(name);
        product.setImageUrl(image);
        product.setPrice(price);
        product.setSaving(savings);
        product.setStore(Priceline);
        product.setWasPrice(wasPrice);
        return product;
    }


    public static List<Product> toProducts(PricelineProduct[] pricelineProducts) {

        List<Product> products = new ArrayList<>();
        for (PricelineProduct pricelineProduct : pricelineProducts) {
            Product product = pricelineProduct.toGroceriesCoachProduct();
            if (product != null) {
                products.add(product);
            }
        }
        return products;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("brand", brand)
                .append("image", image)
                .append("price", price)
                .append("savings", savings)
                .append("wasPrice", wasPrice)
                .toString();
    }
}
