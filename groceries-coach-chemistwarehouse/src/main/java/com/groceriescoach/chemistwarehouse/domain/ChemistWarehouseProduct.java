package com.groceriescoach.chemistwarehouse.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.CollectionUtils;
import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.Product;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.groceriescoach.core.domain.Store.ChemistWarehouse;

public class ChemistWarehouseProduct implements Serializable {

    private String name;

    private String image;

    private Double price;

    private Double savings;


    public static ChemistWarehouseProduct fromProductElement(Element productElement) {

        ChemistWarehouseProduct chemistWarehouseProduct = new ChemistWarehouseProduct();

        chemistWarehouseProduct.setName(extractNameFromProductElement(productElement));
        chemistWarehouseProduct.setImage(extractImageFromProductElement(productElement));
        chemistWarehouseProduct.setPrice(extractPriceFromProductElement(productElement));
        chemistWarehouseProduct.setSavings(extractSavingsFromProductElement(productElement));

        return chemistWarehouseProduct;
    }

    private static String extractNameFromProductElement(Element productElement) {
        return productElement.select("a").get(0).attr("title");
    }

    private static Double extractSavingsFromProductElement(Element productElement) {
        Elements savingsElements = productElement.select(".Save");
        if (CollectionUtils.isNotEmpty(savingsElements)) {
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
        Element priceSpan = productElement.select(".Price").get(0);
        List<TextNode> textNodes = priceSpan.textNodes();
        for (TextNode textNode : textNodes) {
            if (StringUtils.isNotBlank(textNode.text())) {
                return Double.parseDouble(textNode.splitText(1).text());
            }
        }
        return 0D;
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

    public Product toGroceriesCoachProduct() {
        Product product = new Product();
        product.setName(name);
        product.setImageUrl(image);
        product.setPrice(price);
        product.setSaving(savings);
        product.setStore(ChemistWarehouse);
        return product;
    }


    public static List<Product> toProducts(ChemistWarehouseProduct[] chemistWarehouseProducts) {

        List<Product> products = new ArrayList<>();
        for (ChemistWarehouseProduct chemistWarehouseProduct : chemistWarehouseProducts) {
            Product product = chemistWarehouseProduct.toGroceriesCoachProduct();
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
                .append("price", price)
                .append("savings", savings)
                .toString();
    }
}
