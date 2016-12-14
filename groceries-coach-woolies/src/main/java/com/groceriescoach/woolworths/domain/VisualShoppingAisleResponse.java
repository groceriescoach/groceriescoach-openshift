package com.groceriescoach.woolworths.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class VisualShoppingAisleResponse implements Serializable {

    private static final long serialVersionUID = -8963912773378018187L;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("UrlFriendlyName")
    private String urlFriendlyName;

    @JsonProperty("Color")
    private String color;

    @JsonProperty("TextColor")
    private String textColor;

    @JsonProperty("NumberColor")
    private String numberColor;

    @JsonProperty("IconFile")
    private String iconFile;

    @JsonProperty("ChildCategories")
    private ChildCategory childCategories[];

    @JsonProperty("HasLandingPage")
    private Boolean hasLandingPage;

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getNumberColor() {
        return numberColor;
    }

    public void setNumberColor(String numberColor) {
        this.numberColor = numberColor;
    }

    public String getIconFile() {
        return iconFile;
    }

    public void setIconFile(String iconFile) {
        this.iconFile = iconFile;
    }

    public ChildCategory[] getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(ChildCategory[] childCategories) {
        this.childCategories = childCategories;
    }

    public Boolean getHasLandingPage() {
        return hasLandingPage;
    }

    public void setHasLandingPage(Boolean hasLandingPage) {
        this.hasLandingPage = hasLandingPage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("urlFriendlyName", urlFriendlyName)
                .append("color", color)
                .append("textColor", textColor)
                .append("numberColor", numberColor)
                .append("iconFile", iconFile)
                .append("childCategories", childCategories)
                .append("hasLandingPage", hasLandingPage)
                .toString();
    }
}
