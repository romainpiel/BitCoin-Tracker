package com.romainpiel.bitcointracker.network.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class HistoryDto {

    @JsonProperty
    LinkedHashMap<String, Double> bpi;

    public HashMap<String, Double> getBpi() {
        return bpi;
    }

    public void setBpi(LinkedHashMap<String, Double> bpi) {
        this.bpi = bpi;
    }
}
