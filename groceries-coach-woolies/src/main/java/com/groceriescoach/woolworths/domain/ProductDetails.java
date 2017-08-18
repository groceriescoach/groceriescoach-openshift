package com.groceriescoach.woolworths.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachProduct;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.groceriescoach.core.com.groceriescoach.core.utils.MathUtils.roundToTwoDecimalPlaces;
import static com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils.removeHtml;


public class ProductDetails implements Serializable {

    private static final long serialVersionUID = 3253996340428188193L;


    @JsonProperty("Stockcode")
    private String stockCode;

    @JsonProperty("Barcode")
    private String barcode;

    @JsonProperty("GtinFormat")
    private String gtinFormat;

    @JsonProperty("CupPrice")
    private Double cupPrice;

    @JsonProperty("CupMeasure")
    private String cupMeasure;

    @JsonProperty("CupString")
    private String cupString;

    @JsonProperty("HasCupPrice")
    private Boolean hasCupPrice;

    @JsonProperty("Price")
    private Double price;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("UrlFriendlyName")
    private String urlFriendlyName;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("SmallImageFile")
    private String smallImageFile;

    @JsonProperty("MediumImageFile")
    private String mediumImageFile;

    @JsonProperty("LargeImageFile")
    private String largeImageFile;

    @JsonProperty("IsNew")
    private Boolean newProduct;

    @JsonProperty("IsOnSpecial")
    private Boolean onSpecial;

    @JsonProperty("IsEdrSpecial")
    private Boolean edrSpecial;

    @JsonProperty("SavingsAmount")
    private Double savingsAmount;

    @JsonProperty("WasPrice")
    private Double wasPrice;

    @JsonProperty("QuantityInTrolley")
    private int quantityInTrolley;

    @JsonProperty("Unit")
    private String unit;

    @JsonProperty("MinimumQuantity")
    private int minimumQuantity;

    @JsonProperty("IsInTrolley")
    private Boolean inTrolley;

    @JsonProperty("Source")
    private String source;

    @JsonProperty("SupplyLimit")
    private int supplyLimit;

    @JsonProperty("PackageSize")
    private String packageSize;

    @JsonProperty("IsPmDelivery")
    private Boolean pmDelivery;

    @JsonProperty("IsForCollection")
    private Boolean forCollection;

    @JsonProperty("IsForDelivery")
    private Boolean forDelivery;

    @JsonProperty("ImageTag")
    private Tag imageTag;

    @JsonProperty("CentreTag")
    private Tag centreTag;

    @JsonProperty("TextTag")
    private Tag textTag;

    @JsonProperty("HeaderTag")
    private Tag headerTag;

    @JsonProperty("FooterTag")
    private Tag footerTag;

    @JsonProperty("HtmlTag")
    private Tag htmlTag;

    @JsonProperty("IsCentreTag")
    private boolean centreTagFlag;

    @JsonProperty("HasHeaderTag")
    private Boolean headerTagFlag;

    @JsonProperty("IsFooterEnabled")
    private Boolean footerEnabled;

    @JsonProperty("UnitWeightInGrams")
    private Double unitWeightInGrams;

    @JsonProperty("SupplyLimitMessage")
    private String supplyLimitMessage;

    @JsonProperty("SmallFormatDescription")
    private String smallFormatDescription;

    @JsonProperty("IsAvailable")
    private Boolean available;

    @JsonProperty("AgeRestricted")
    private Boolean ageRestricted;

    @JsonProperty("DisplayQuantity")
    private int displayQuantity;

    @JsonProperty("RichDescription")
    private String richDescription;

    @JsonProperty("IsDeliveryPass")
    private Boolean deliveryPass;

    @JsonProperty("HideWasSavedPrice")
    private Boolean hideWasSavedPrice;

    @JsonProperty("FullDescription")
    private String fullDescription;

    @JsonProperty("SapCategories")
    private String sapCategories;

    @JsonProperty("Brand")
    private String brand;

    @JsonProperty("Diagnostics")
    private String diagnostics;

    @JsonProperty("IsBundle")
    private Boolean bundle;

    @JsonProperty("ChildProducts")
    private List<ChildProduct> childProducts;



    private static final Logger logger = LoggerFactory.getLogger(ProductDetails.class);



    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public Double getCupPrice() {
        return cupPrice;
    }

    public void setCupPrice(Double cupPrice) {
        this.cupPrice = cupPrice;
    }

    public String getCupMeasure() {
        return cupMeasure;
    }

    public void setCupMeasure(String cupMeasure) {
        this.cupMeasure = cupMeasure;
    }

    public String getCupString() {
        return cupString;
    }

    public void setCupString(String cupString) {
        this.cupString = cupString;
    }

    public Boolean getHasCupPrice() {
        return hasCupPrice;
    }

    public void setHasCupPrice(Boolean hasCupPrice) {
        this.hasCupPrice = hasCupPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSmallImageFile() {
        return smallImageFile;
    }

    public void setSmallImageFile(String smallImageFile) {
        this.smallImageFile = smallImageFile;
    }

    public String getMediumImageFile() {
        return mediumImageFile;
    }

    public void setMediumImageFile(String mediumImageFile) {
        this.mediumImageFile = mediumImageFile;
    }

    public String getLargeImageFile() {
        return largeImageFile;
    }

    public void setLargeImageFile(String largeImageFile) {
        this.largeImageFile = largeImageFile;
    }

    public Boolean getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(Boolean newProduct) {
        this.newProduct = newProduct;
    }

    public Boolean getOnSpecial() {
        return onSpecial;
    }

    public void setOnSpecial(Boolean onSpecial) {
        this.onSpecial = onSpecial;
    }

    public Boolean getEdrSpecial() {
        return edrSpecial;
    }

    public void setEdrSpecial(Boolean edrSpecial) {
        this.edrSpecial = edrSpecial;
    }

    public Double getSavingsAmount() {
        return savingsAmount;
    }

    public void setSavingsAmount(Double savingsAmount) {
        this.savingsAmount = savingsAmount;
    }

    public Double getWasPrice() {
        return wasPrice;
    }

    public void setWasPrice(Double wasPrice) {
        this.wasPrice = wasPrice;
    }

    public int getQuantityInTrolley() {
        return quantityInTrolley;
    }

    public void setQuantityInTrolley(int quantityInTrolley) {
        this.quantityInTrolley = quantityInTrolley;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public Boolean getInTrolley() {
        return inTrolley;
    }

    public void setInTrolley(Boolean inTrolley) {
        this.inTrolley = inTrolley;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getSupplyLimit() {
        return supplyLimit;
    }

    public void setSupplyLimit(int supplyLimit) {
        this.supplyLimit = supplyLimit;
    }

    public String getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(String packageSize) {
        this.packageSize = packageSize;
    }

    public Boolean getPmDelivery() {
        return pmDelivery;
    }

    public void setPmDelivery(Boolean pmDelivery) {
        this.pmDelivery = pmDelivery;
    }

    public Boolean getForCollection() {
        return forCollection;
    }

    public void setForCollection(Boolean forCollection) {
        this.forCollection = forCollection;
    }

    public Boolean getForDelivery() {
        return forDelivery;
    }

    public void setForDelivery(Boolean forDelivery) {
        this.forDelivery = forDelivery;
    }

    public Tag getImageTag() {
        return imageTag;
    }

    public void setImageTag(Tag imageTag) {
        this.imageTag = imageTag;
    }

    public Tag getCentreTag() {
        return centreTag;
    }

    public void setCentreTag(Tag centreTag) {
        this.centreTag = centreTag;
    }

    public Tag getTextTag() {
        return textTag;
    }

    public void setTextTag(Tag textTag) {
        this.textTag = textTag;
    }

    public Tag getFooterTag() {
        return footerTag;
    }

    public void setFooterTag(Tag footerTag) {
        this.footerTag = footerTag;
    }

    public Tag getHtmlTag() {
        return htmlTag;
    }

    public void setHtmlTag(Tag htmlTag) {
        this.htmlTag = htmlTag;
    }

    public boolean isCentreTagFlag() {
        return centreTagFlag;
    }

    public void setCentreTagFlag(boolean centreTagFlag) {
        this.centreTagFlag = centreTagFlag;
    }

    public Boolean getFooterEnabled() {
        return footerEnabled;
    }

    public void setFooterEnabled(Boolean footerEnabled) {
        this.footerEnabled = footerEnabled;
    }

    public Double getUnitWeightInGrams() {
        return unitWeightInGrams;
    }

    public void setUnitWeightInGrams(Double unitWeightInGrams) {
        this.unitWeightInGrams = unitWeightInGrams;
    }

    public String getSupplyLimitMessage() {
        return supplyLimitMessage;
    }

    public void setSupplyLimitMessage(String supplyLimitMessage) {
        this.supplyLimitMessage = supplyLimitMessage;
    }

    public String getSmallFormatDescription() {
        return smallFormatDescription;
    }

    public void setSmallFormatDescription(String smallFormatDescription) {
        this.smallFormatDescription = smallFormatDescription;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getAgeRestricted() {
        return ageRestricted;
    }

    public void setAgeRestricted(Boolean ageRestricted) {
        this.ageRestricted = ageRestricted;
    }

    public int getDisplayQuantity() {
        return displayQuantity;
    }

    public void setDisplayQuantity(int displayQuantity) {
        this.displayQuantity = displayQuantity;
    }

    public String getRichDescription() {
        return richDescription;
    }

    public void setRichDescription(String richDescription) {
        this.richDescription = richDescription;
    }

    public Boolean getDeliveryPass() {
        return deliveryPass;
    }

    public void setDeliveryPass(Boolean deliveryPass) {
        this.deliveryPass = deliveryPass;
    }

    public Tag getHeaderTag() {
        return headerTag;
    }

    public void setHeaderTag(Tag headerTag) {
        this.headerTag = headerTag;
    }

    public Boolean getHeaderTagFlag() {
        return headerTagFlag;
    }

    public void setHeaderTagFlag(Boolean headerTagFlag) {
        this.headerTagFlag = headerTagFlag;
    }

    public Boolean getHideWasSavedPrice() {
        return hideWasSavedPrice;
    }

    public void setHideWasSavedPrice(Boolean hideWasSavedPrice) {
        this.hideWasSavedPrice = hideWasSavedPrice;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public static List<WoolworthsProduct> toProducts(ProductDetails productDetailsArray[], String keywords) {
        List<WoolworthsProduct> products = new ArrayList<>();
        for (ProductDetails productDetails: productDetailsArray) {
            products.add(productDetails.toGroceriesCoachProduct(keywords));
        }
        return products;
    }

    private WoolworthsProduct toGroceriesCoachProduct(String keywords) {
        WoolworthsProduct product = new WoolworthsProduct();
        product.setName(removeHtml(name));
        product.setDescription(removeHtml(description));
        product.setImageUrl(largeImageFile);
        product.setUrl("https://www.woolworths.com.au/shop/productdetails/" + stockCode + "/" + urlFriendlyName);
        product.setPrice(price);
        product.setWasPrice(wasPrice);
        product.setPackageSize(packageSize);
        product.setSaving(savingsAmount);
        product.setUnitPrice(cupPrice);
        product.setUnitSize(cupMeasure);
        product.setUnitPriceStr(cupString);

        if (isCentreTagFlag()) {
            product.setQuantityPriceList(createQuantityPriceList());
        }

        return product;
    }

    private List<GroceriesCoachProduct.QuantityPrice> createQuantityPriceList() {

        List<GroceriesCoachProduct.QuantityPrice> quantityPriceList = new ArrayList<>();

        GroceriesCoachProduct.QuantityPrice quantityPrice = new GroceriesCoachProduct.QuantityPrice();

        String tagContent = centreTag.getTagContent();
//        logger.debug("tagContent = {}", tagContent);
        Document document = Jsoup.parse(tagContent);

        if (document.childNodes().get(0).childNode(1).childNode(0).childNode(0).toString().contains("Was ")) {
            return null;
        } else if (document.childNodes().get(0).childNode(1).childNode(0).childNode(0).childNodes().isEmpty()) {
            return null;
        }
        String quantityPriceStr = document.childNodes().get(0).childNode(1).childNode(0).childNode(0).childNode(0).toString();
        String[] quantityPriceElements = quantityPriceStr.split("for");
        quantityPrice.setQuantity(Integer.parseInt(StringUtils.trim(quantityPriceElements[0])));
        quantityPrice.setPrice(Double.parseDouble(StringUtils.removeCurrencySymbols(quantityPriceElements[1])));

        String quantityUnitPriceStr = StringUtils.trimToEmpty(document.childNodes().get(0).childNode(1).childNode(0).childNode(1).toString());
        if (StringUtils.isNotBlank(quantityUnitPriceStr)) {
            quantityPrice.setUnitPriceStr(quantityUnitPriceStr);
            quantityPrice.setUnitPrice(Double.parseDouble(StringUtils.removeCurrencySymbols(quantityUnitPriceStr.split("/")[0])));
            quantityPrice.setUnitSize(quantityUnitPriceStr.split("/")[1]);
        } else {
            quantityPrice.setUnitSize(cupMeasure);
            quantityPrice.setUnitPrice(roundToTwoDecimalPlaces(quantityPrice.getPrice() * cupPrice / (quantityPrice.getQuantity() * price)));
            quantityPrice.setUnitPriceStr("$" + quantityPrice.getUnitPrice() + " / " + quantityPrice.getUnitSize());
        }

        quantityPriceList.add(quantityPrice);


        return quantityPriceList;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("stockCode", stockCode)
                .append("cupPrice", cupPrice)
                .append("cupMeasure", cupMeasure)
                .append("cupString", cupString)
                .append("hasCupPrice", hasCupPrice)
                .append("price", price)
                .append("name", name)
                .append("urlFriendlyName", urlFriendlyName)
                .append("description", description)
                .append("smallImageFile", smallImageFile)
                .append("mediumImageFile", mediumImageFile)
                .append("largeImageFile", largeImageFile)
                .append("onSpecial", onSpecial)
                .append("edrSpecial", edrSpecial)
                .append("savingsAmount", savingsAmount)
                .append("wasPrice", wasPrice)
                .append("quantityInTrolley", quantityInTrolley)
                .append("unit", unit)
                .append("minimumQuantity", minimumQuantity)
                .append("inTrolley", inTrolley)
                .append("source", source)
                .append("supplyLimit", supplyLimit)
                .append("packageSize", packageSize)
                .append("pmDelivery", pmDelivery)
                .append("forCollection", forCollection)
                .append("forDelivery", forDelivery)
                .append("imageTag", imageTag)
                .append("centreTag", centreTag)
                .append("textTag", textTag)
                .append("footerTag", footerTag)
                .append("htmlTag", htmlTag)
                .append("centreTagFlag", centreTagFlag)
                .append("footerEnabled", footerEnabled)
                .append("unitWeightInGrams", unitWeightInGrams)
                .append("supplyLimitMessage", supplyLimitMessage)
                .append("smallFormatDescription", smallFormatDescription)
                .append("available", available)
                .append("ageRestricted", ageRestricted)
                .append("displayQuantity", displayQuantity)
                .append("richDescription", richDescription)
                .append("deliveryPass", deliveryPass)
                .toString();
    }
}
