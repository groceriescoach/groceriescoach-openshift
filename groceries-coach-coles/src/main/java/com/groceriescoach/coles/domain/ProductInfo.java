package com.groceriescoach.coles.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"A4", "L2", "P8", "W1", "T1", "D"})
public class ProductInfo implements Serializable {


    private static final long serialVersionUID = -1010278820752417183L;

    @JsonProperty("O3")
    private String[] size;

    public String[] getSize() {
        return size;
    }

    public void setSize(String[] size) {
        this.size = size;
    }

    String getPackageSize() {
        if (ArrayUtils.isNotEmpty(size)) {
            return size[0];
        } else {
            return null;
        }
    }
}
