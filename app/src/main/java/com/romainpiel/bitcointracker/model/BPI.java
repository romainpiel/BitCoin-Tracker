package com.romainpiel.bitcointracker.model;

public class BPI {

    private String date;
    private Double value;
    private Float change;

    public BPI(String date, Double value) {
        this.date = date;
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public Double getValue() {
        return value;
    }

    public Float getChange() {
        return change;
    }

    public void setChange(Float change) {
        this.change = change;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BPI)) return false;

        BPI bpi = (BPI) o;

        if (change != null ? !change.equals(bpi.change) : bpi.change != null) return false;
        if (date != null ? !date.equals(bpi.date) : bpi.date != null) return false;
        if (value != null ? !value.equals(bpi.value) : bpi.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (change != null ? change.hashCode() : 0);
        return result;
    }
}
