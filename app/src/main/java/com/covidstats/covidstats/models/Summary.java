
package com.covidstats.covidstats.models;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Summary {

    @SerializedName("Global")
    @Expose
    private Global global;
    @SerializedName("Countries")
    @Expose
    private List<Country> countries = null;
    @SerializedName("Date")
    @Expose
    private String date;

    public Global getGlobal() {
        return global;
    }

    public void setGlobal(Global global) {
        this.global = global;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Summary{" +
                "global=" + global.toString() +
                ", countries=" + countries.get(2).getSlug() +
                ", date='" + date + '\'' +
                '}';
    }
}
