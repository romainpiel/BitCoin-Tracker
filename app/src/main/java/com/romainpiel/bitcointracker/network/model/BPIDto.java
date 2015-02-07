package com.romainpiel.bitcointracker.network.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BPIDto {

    @JsonProperty("rate_float")
    Float rate;

    public BPIDto() {
    }

    public BPIDto(Float rate) {
        this.rate = rate;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}
