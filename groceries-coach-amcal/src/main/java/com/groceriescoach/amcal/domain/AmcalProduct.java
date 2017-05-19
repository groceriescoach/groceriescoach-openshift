package com.groceriescoach.amcal.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.Product;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.groceriescoach.core.domain.Store.Amcal;

public class AmcalProduct implements Serializable {

    private String name;
    private String image;
    private String url;
    private Double price;
    private Double oldPrice;
    private Double savings;


    public static AmcalProduct fromProductElement(Element productElement) {

        AmcalProduct amcalProduct = null;
        if (!productElement.getElementsByClass("product_info").isEmpty()) {
            amcalProduct = new AmcalProduct();
            amcalProduct.setName(extractNameFromProductElement(productElement));
            amcalProduct.setImage(extractImageFromProductElement(productElement));
            amcalProduct.setUrl(extractUrlFromProductElement(productElement));
            amcalProduct.setPrice(extractPriceFromProductElement(productElement));
            amcalProduct.setOldPrice(extractOldPriceFromProductElement(productElement));
            amcalProduct.setSavings(amcalProduct.calculateSavings());
        }
        return amcalProduct;
    }

    private static String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".product_name a").get(0).attr("href");
    }

    private Double calculateSavings() {
        if (Objects.nonNull(price) && Objects.nonNull(oldPrice)) {
            return oldPrice - price;
        } else {
            return null;
        }
    }

    private static String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product_name a").get(0).text();
    }

    private static String extractImageFromProductElement(Element productElement) {
        return "https://www.amcal.com.au" + productElement.select("img").get(0).attr("src");
    }

    private static Double extractPriceFromProductElement(Element productElement) {
        String price = productElement.select(".price").get(0).text();
        if (StringUtils.isNotBlank(price) && price.startsWith("$")) {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(price.substring(1)));
        }
        return 0D;
    }

    public static Double extractOldPriceFromProductElement(Element productElement) {
        Elements oldPriceElements = productElement.select(".old_price");
        if (oldPriceElements != null && !oldPriceElements.isEmpty()) {
            Element oldPriceElement = oldPriceElements.get(0);
            if (oldPriceElement != null) {
                String price = oldPriceElement.text();
                if (StringUtils.isNotBlank(price) && price.startsWith("$")) {
                    return Double.parseDouble(StringUtils.removeCurrencySymbols(price.substring(1)));
                }
                return 0D;
            }
        }
        return null;
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

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product toGroceriesCoachProduct() {
        Product product = new Product();
        product.setName(name);
        product.setImageUrl(image);
        product.setUrl(url);
        product.setPrice(price);
        product.setWasPrice(oldPrice);
        product.setSaving(savings);
        product.setStore(Amcal);
        return product;
    }


    public static List<Product> toProducts(AmcalProduct[] amcalProducts) {

        List<Product> products = new ArrayList<>();
        for (AmcalProduct amcalProduct : amcalProducts) {
            Product product = amcalProduct.toGroceriesCoachProduct();
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
                .append("image", image)
                .append("url", url)
                .append("price", price)
                .append("oldPrice", oldPrice)
                .append("savings", savings)
                .toString();
    }

    public void setSavings(Double savings) {
        this.savings = savings;
    }

    public Double getSavings() {
        return savings;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

