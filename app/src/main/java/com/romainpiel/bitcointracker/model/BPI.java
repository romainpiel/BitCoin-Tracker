package com.romainpiel.bitcointracker.model;

public class BPI {

    private String date;
    private Double close;
    private Float change;

    public BPI(String date, Double close) {
        this.date = date;
        this.close = close;
    }

    public String getDate() {
        return date;
    }

    public Double getClose() {
        return close;
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
        if (close != null ? !close.equals(bpi.close) : bpi.close != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (close != null ? close.hashCode() : 0);
        result = 31 * result + (change != null ? change.hashCode() : 0);
        return result;
    }
}
