package com.romainpiel.bitcointracker.network.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TimeDto {

    @JsonProperty
    Date updatedISO;

    public TimeDto() {
    }

    public TimeDto(Date updatedISO) {
        this.updatedISO = updatedISO;
    }

    public Date getUpdatedISO() {
        return updatedISO;
    }

    public void setUpdatedISO(Date updatedISO) {
        this.updatedISO = updatedISO;
    }
}
