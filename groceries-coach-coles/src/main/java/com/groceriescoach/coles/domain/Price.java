package com.groceriescoach.coles.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class Price implements Serializable {

    private static final long serialVersionUID = 5176421087923800259L;
    @JsonProperty("o")
    private Double now;
    @JsonProperty("l4")
    private Double was;

    public Double getNow() {
        return now;
    }

    public void setNow(Double now) {
        this.now = now;
    }

    public Double getWas() {
        return was;
    }

    public void setWas(Double was) {
        this.was = was;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("now", now)
                .append("was", was)
                .toString();
    }
}
