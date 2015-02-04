package com.romainpiel.bitcointracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class BPI implements Parcelable {

    private Date date;
    private Float close;
    private Float change;

    public BPI(Date date, Float close) {
        this(date, close, null);
    }

    public BPI(Date date, Float close, Float change) {
        this.date = date;
        this.close = close;
        this.change = change;
    }

    private BPI(Parcel in) {
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.close = (Float) in.readValue(Float.class.getClassLoader());
        this.change = (Float) in.readValue(Float.class.getClassLoader());
    }

    public Date getDate() {
        return date;
    }

    public Float getClose() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(date != null ? date.getTime() : -1);
        dest.writeValue(this.close);
        dest.writeValue(this.change);
    }

    public static final Creator<BPI> CREATOR = new Creator<BPI>() {
        public BPI createFromParcel(Parcel source) {
            return new BPI(source);
        }

        public BPI[] newArray(int size) {
            return new BPI[size];
        }
    };
}
