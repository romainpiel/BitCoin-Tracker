package com.romainpiel.bitcointracker.network.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class PriceDto {

    @JsonProperty
    TimeDto time;

    @JsonProperty
    HashMap<String, BPIDto> bpi;

    public TimeDto getTime() {
        return time;
    }

    public void setTime(TimeDto time) {
        this.time = time;
    }

    public HashMap<String, BPIDto> getBpi() {
        return bpi;
    }

    public void setBpi(HashMap<String, BPIDto> bpi) {
        this.bpi = bpi;
    }
}
