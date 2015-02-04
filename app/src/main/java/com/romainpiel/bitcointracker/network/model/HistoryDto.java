package com.romainpiel.bitcointracker.network.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class HistoryDto {

    @JsonProperty
    LinkedHashMap<Date, Float> bpi;

    public HashMap<Date, Float> getBpi() {
        return bpi;
    }

    public void setBpi(LinkedHashMap<Date, Float> bpi) {
        this.bpi = bpi;
    }
}
