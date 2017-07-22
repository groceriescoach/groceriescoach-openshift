package com.groceriescoach.pharmacy4less.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Price implements Serializable {

    private static final long serialVersionUID = -1792157229351639566L;
    @JsonProperty("AUD")
    private CurrencyCost aud;

    public CurrencyCost getAud() {
        return aud;
    }

    public void setAud(CurrencyCost aud) {
        this.aud = aud;
    }
}
