package fadhilah.ramadhan.covid19stats.model;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class DataStats implements Parcelable {
    private int postive, activeCases,cured,death;
    private String country, countryCode, date;

    public DataStats(@Nullable DataStats dataStats){
        super();
        this.country = dataStats.getCountry();
        this.postive = dataStats.getPostive();
        this.cured   = dataStats.getCured();
        this.death   = dataStats.getDeath();
        this.countryCode = dataStats.getCountryCode();
    }

    public int getActiveCases() {
        return activeCases;
    }

    public void setActiveCases(int activeCases) {
        this.activeCases = activeCases;
    }

    public int getCured() {
        return cured;
    }

    public void setCured(int cured) {
        this.cured = cured;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPostive() {
        return postive;
    }

    public void setPostive(int postive) {
        this.postive = postive;
    }

    public DataStats() {

    }

    // Parcelling part
    public DataStats(Parcel in){
        String[] data = new String[7];
            in.readStringArray(data);
            this.activeCases = Integer.parseInt(data[0]);
            this.postive = Integer.parseInt(data[1]);
            this.cured = Integer.parseInt(data[2]);
            this.death = Integer.parseInt(data[3]);
            this.countryCode = data[4];
            this.country = data[5];
            this.date = data[6];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {String.valueOf(this.activeCases), String.valueOf(this.postive),
                String.valueOf(this.cured), String.valueOf(this.death), this.countryCode, this.country, this.date});
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public DataStats createFromParcel(Parcel in) {
            return new DataStats(in);
        }

        public DataStats[] newArray(int size) {
            return new DataStats[size];
        }
    };
}
