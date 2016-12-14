package com.groceriescoach.woolworths.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class Tag implements Serializable {

    private static final long serialVersionUID = 2290832433108149407L;

    @JsonProperty("TagContent")
    private String tagContent;
    @JsonProperty("TagLink")
    private String tagLink;
    @JsonProperty("FallbackText")
    private String fallbackText;
    @JsonProperty("TagType")
    private String tagType;


    public String getTagContent() {
        return tagContent;
    }

    public void setTagContent(String tagContent) {
        this.tagContent = tagContent;
    }

    public String getTagLink() {
        return tagLink;
    }

    public void setTagLink(String tagLink) {
        this.tagLink = tagLink;
    }

    public String getFallbackText() {
        return fallbackText;
    }

    public void setFallbackText(String fallbackText) {
        this.fallbackText = fallbackText;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("tagContent", tagContent)
                .append("tagLink", tagLink)
                .append("fallbackText", fallbackText)
                .append("tagType", tagType)
                .toString();
    }
}
