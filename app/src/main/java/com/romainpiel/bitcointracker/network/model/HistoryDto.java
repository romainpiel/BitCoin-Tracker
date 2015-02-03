package com.romainpiel.bitcointracker.network.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class HistoryDto {

    @JsonProperty
    HashMap<String, Double> bpi;
}
