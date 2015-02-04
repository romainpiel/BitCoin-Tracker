package com.romainpiel.bitcointracker.network.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class HistoryDto {

    @JsonProperty
    LinkedHashMap<Date, Double> bpi;

    public HashMap<Date, Double> getBpi() {
        return bpi;
    }

    public void setBpi(LinkedHashMap<Date, Double> bpi) {
        this.bpi = bpi;
    }
}
