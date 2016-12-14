package com.groceriescoach.woolworths.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Correction {

    @JsonProperty("Word")
    private String word;

    @JsonProperty("CorrectionList")
    private String correctionList[];

}
