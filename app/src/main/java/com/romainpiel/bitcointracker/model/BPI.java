package com.romainpiel.bitcointracker.model;

public class BPI {

    private String date;
    private Double value;

    public BPI(String date, Double value) {
        this.date = date;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BPI)) return false;

        BPI bpi = (BPI) o;

        if (date != null ? !date.equals(bpi.date) : bpi.date != null) return false;
        if (value != null ? !value.equals(bpi.value) : bpi.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
