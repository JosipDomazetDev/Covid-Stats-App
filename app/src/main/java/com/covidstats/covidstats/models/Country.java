
package com.covidstats.covidstats.models;


import com.covidstats.covidstats.utility.StringUtility;
import com.covidstats.covidstats.utility.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Country {

    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("CountryCode")
    @Expose
    private String countryCode;
    @SerializedName("Slug")
    @Expose
    private String slug;
    @SerializedName("NewConfirmed")
    @Expose
    private Long newConfirmed;
    @SerializedName("TotalConfirmed")
    @Expose
    private Long totalConfirmed;
    @SerializedName("NewDeaths")
    @Expose
    private Long newDeaths;
    @SerializedName("TotalDeaths")
    @Expose
    private Long totalDeaths;
    @SerializedName("NewRecovered")
    @Expose
    private Long newRecovered;
    @SerializedName("TotalRecovered")
    @Expose
    private Long totalRecovered;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Premium")
    @Expose
    private Premium premium;


    private String displayName = null;
    private long displayNumber = 0;


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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Long getNewConfirmed() {
        return newConfirmed;
    }

    public void setNewConfirmed(Long newConfirmed) {
        this.newConfirmed = newConfirmed;
    }

    public Long getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(Long totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public Long getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(Long newDeaths) {
        this.newDeaths = newDeaths;
    }

    public Long getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(Long totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public Long getNewRecovered() {
        return newRecovered;
    }

    public void setNewRecovered(Long newRecovered) {
        this.newRecovered = newRecovered;
    }

    public Long getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(Long totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Premium getPremium() {
        return premium;
    }

    public void setPremium(Premium premium) {
        this.premium = premium;
    }


    public String getDisplayName() {
        if (displayName == null) {
            StringBuilder ret = new StringBuilder();
            for (String s : slug.split("-")) {
                if (s.length() > 0) {
                    ret.append(s.substring(0, 1).toUpperCase()).append(s.substring(1)).append(" ");
                }
            }

            displayName = ret.toString().trim();
        }

        return displayName;
    }

    SortCategory currentSort = new SortCategory(SortCategory.TOTAL_CASES, "");

    public SortCategory getCurrentSort() {
        return currentSort;
    }


    public Double getDisplayNumber(SortCategory sortCategory) {
           /*
         <item>Total Cases</item>
        <item>Total Recovered</item>
        <item>Total Deaths</item>
        <item>Active Cases</item>

        <item>New Cases</item>
        <item>New Recovered</item>
        <item>New Deaths</item>

        <item>Recovery Rate</item>
        <item>Lethality Rate</item>
        */

        this.currentSort = sortCategory;

      /*  if (sortCategory.getType() == SortCategory.TOTAL_CASES) {
            return totalConfirmed.doubleValue();
        } else if (sortCategory.getType() == SortCategory.TOTAL_RECOVERED) {
            return totalRecovered.doubleValue();
        } else if (sortCategory.getType() == SortCategory.TOTAL_DEATHS) {
            return totalDeaths.doubleValue();
        } else if (sortCategory.getType() == SortCategory.ACTIVE_CASES) {
            return getActiveCases();
        } else if (sortCategory.getType() == SortCategory.NEW_CASES) {
            return newConfirmed.doubleValue();
        } else if (sortCategory.getType() == SortCategory.NEW_RECOVERED) {
            return newRecovered.doubleValue();
        } else if (sortCategory.getType() == SortCategory.NEW_DEATHS) {
            return newDeaths.doubleValue();
        } else if (sortCategory.getType() == SortCategory.RECOVERY_RATE) {
            return getRecoveryRate();
        } else if (sortCategory.getType() == SortCategory.LETHALITY_RATE) {
            //return totalDeaths.doubleValue() / (totalDeaths.doubleValue() + totalRecovered.doubleValue());
            return getLethalityRate();
        }
*/
        try {
            return Objects.requireNonNull(getAllNumbers().get(sortCategory.getType())).doubleValue();
        } catch (NullPointerException e) {
            return 1.0;
        }
    }

    public double getLethalityRate() {
        return totalDeaths.doubleValue() / (totalConfirmed.doubleValue());
    }

    public double getRecoveryRate() {
        return 1.0 - (getLethalityRate());
    }

    @NotNull
    public double getActiveCases() {
        return totalConfirmed.doubleValue() - totalRecovered.doubleValue() - totalDeaths.doubleValue();
    }

    public String getFormattedDisplayNumber(Number number, Integer key) {
        if (key == SortCategory.RECOVERY_RATE || key == SortCategory.LETHALITY_RATE) {
            return formatPercentage(number);
        }
        return formatNumber(number);

    }

    public String getFormattedDisplayNumber() {
        return getFormattedDisplayNumber(getDisplayNumber(currentSort), currentSort.getType());
    }


    public String formatNumber(Number number) {
        return StringUtility.Companion.formatNumber(number.doubleValue());
    }

    public String formatPercentage(Number percentage) {
        return StringUtility.Companion.formatNumberPercentage(percentage.doubleValue());
    }


    public String getDisplayPlace(@NotNull List<Country> sortedCountries) {
        return "#" + getPlace(sortedCountries);
    }

    public int getPlace(@NotNull List<Country> sortedCountries) {
        return getPosition(currentSort.getType(), sortedCountries);
    }

    public String getSearchString() {
        return (getDisplayName() + slug + country + countryCode).toLowerCase();
    }


    public void setPlace(int place) {
    }

    private static final String imageURL = "https://www.countryflags.io/%s/flat/64.png";

    public String getImageURL() {
        return String.format(imageURL, countryCode);
    }

    public boolean containsForSearch(@NotNull String searchMsgAsString) {
        if (getDisplayName().toLowerCase().trim().contains(searchMsgAsString)) return true;
        if (slug.toLowerCase().trim().contains(searchMsgAsString)) return true;
        if (country.toLowerCase().trim().contains(searchMsgAsString)) return true;

        return countryCode.toLowerCase().trim().contains(searchMsgAsString);
    }


    private Map<Integer, Number> allNumbers = null;

    @NotNull
    public Map<Integer, Number> getAllNumbers() {
        if (allNumbers != null) return allNumbers;

        allNumbers = new HashMap<>();
        allNumbers.put(SortCategory.TOTAL_CASES, totalConfirmed);
        allNumbers.put(SortCategory.TOTAL_RECOVERED, totalRecovered);
        allNumbers.put(SortCategory.TOTAL_DEATHS, totalDeaths);
        allNumbers.put(SortCategory.ACTIVE_CASES, getActiveCases());

        allNumbers.put(SortCategory.NEW_CASES, newConfirmed);
        allNumbers.put(SortCategory.NEW_RECOVERED, newRecovered);
        allNumbers.put(SortCategory.NEW_DEATHS, newDeaths);

        allNumbers.put(SortCategory.RECOVERY_RATE, getRecoveryRate());
        allNumbers.put(SortCategory.LETHALITY_RATE, getLethalityRate());

        return allNumbers;
    }

    Map<Integer, Integer> positions = null;


    public Integer getPosition(int SORT_CATEGORY, @NotNull List<Country> sortedCountries) {
        return getAllPositions(sortedCountries).get(SORT_CATEGORY);
    }


    @NotNull
    public Map<Integer, Integer> getAllPositions() {
        return positions;
    }

    private static final Map<Integer, List<Country>> mapSortedCountries = new HashMap<>();

    @NotNull
    public Map<Integer, Integer> getAllPositions(@NotNull List<Country> sortedCountries) {
        if (positions != null) return positions;

        positions = new HashMap<>();

        for (Integer key : getAllNumbers().keySet()) {
            if (!mapSortedCountries.containsKey(key)) {
                List<Country> newSortedCountries = FromFuture.Companion.sortWith(sortedCountries, key);
                mapSortedCountries.put(key, newSortedCountries);
            }

            // Watch out, mapSortedCountries is static and might not have reference to this current country
            positions.put(key,
                    mapSortedCountries.get(key).indexOf(this) + 1);
        }

        return positions;
    }

    public long getDisplayNumber() {
        return displayNumber;
    }

}
