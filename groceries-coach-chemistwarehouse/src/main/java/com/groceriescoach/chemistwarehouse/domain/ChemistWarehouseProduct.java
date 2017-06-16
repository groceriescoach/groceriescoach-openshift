package com.groceriescoach.chemistwarehouse.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.CollectionUtils;
import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.List;

import static com.groceriescoach.core.domain.Store.ChemistWarehouse;

public class ChemistWarehouseProduct extends GroceriesCoachJsoupProduct {

    ChemistWarehouseProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        super(productElement, sortType);
    }

    @Override
    protected String extractBrandFromProductElement(Element productElement) {
        return null;
    }

    @Override
    protected String extractDescriptionFromProductElement(Element productElement) {
        return null;
    }

    protected String extractUrlFromProductElement(Element productElement) {
        return "http://www.chemistwarehouse.com.au" + productElement.select("a").get(0).attr("href");
    }

    protected String extractNameFromProductElement(Element productElement) {
        return productElement.select("a").get(0).attr("title");
    }

    @Override
    protected Double extractOldPriceFromProductElement(Element productElement) {
        return null;
    }

    @Override
    protected Double extractSavingFromProductElement(Element productElement) {
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

    protected String extractImageFromProductElement(Element productElement) {
        return productElement.select("img").get(0).attr("src");
    }

    protected Double extractPriceFromProductElement(Element productElement) {
        Element priceSpan = productElement.select(".Price").get(0);
        List<TextNode> textNodes = priceSpan.textNodes();
        for (TextNode textNode : textNodes) {
            if (StringUtils.isNotBlank(textNode.text())) {
                return Double.parseDouble(textNode.splitText(1).text());
            }
        }
        return 0D;
    }

    @Override
    public Store getStore() {
        return ChemistWarehouse;
    }

}
