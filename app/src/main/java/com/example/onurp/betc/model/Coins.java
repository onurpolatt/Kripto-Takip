package com.example.onurp.betc.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;
import java.util.Comparator;

/**
 * Created by onurp on 9.03.2018.
 */

public class Coins implements Comparable<Coins>
{
    private String total_supply;

    private String id;

    private String percent_change_24h;

    @SerializedName("24h_volume_usd")
    @Expose
    private String volume_24h;

    private String rank;

    private String symbol;

    private String available_supply;

    private String percent_change_1h;

    private String name;

    private String price_usd;

    private String last_updated;

    private String percent_change_7d;

    private String price_try;

    private String price_eur;

    private String price_eth;

    private String price_btc;

    private String market_cap_usd;

    public String getTotal_supply ()
    {
        return total_supply;
    }

    public void setTotal_supply (String total_supply)
    {
        this.total_supply = total_supply;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getPercent_change_24h ()
    {
        return percent_change_24h;
    }

    public void setPercent_change_24h (String percent_change_24h)
    {
        this.percent_change_24h = percent_change_24h;
    }

    public String getVolume_24h ()
    {
        return volume_24h;
    }

    public void setVolume_24h (String volume_24h)
    {
        this.volume_24h = volume_24h;
    }

    public String getRank ()
    {
        return rank;
    }

    public void setRank (String rank)
    {
        this.rank = rank;
    }

    public String getSymbol ()
    {
        return symbol;
    }

    public void setSymbol (String symbol)
    {
        this.symbol = symbol;
    }

    public String getAvailable_supply ()
    {
        return available_supply;
    }

    public void setAvailable_supply (String available_supply)
    {
        this.available_supply = available_supply;
    }

    public String getPercent_change_1h ()
    {
        return percent_change_1h;
    }

    public void setPercent_change_1h (String percent_change_1h)
    {
        this.percent_change_1h = percent_change_1h;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getPrice_usd ()
    {
        return price_usd;
    }

    public void setPrice_usd (String price_usd)
    {
        this.price_usd = price_usd;
    }

    public String getLast_updated ()
    {
        return last_updated;
    }

    public void setLast_updated (String last_updated)
    {
        this.last_updated = last_updated;
    }

    public String getPercent_change_7d ()
    {
        return percent_change_7d;
    }

    public void setPercent_change_7d (String percent_change_7d)
    {
        this.percent_change_7d = percent_change_7d;
    }

    public String getPrice_btc ()
    {
        return price_btc;
    }

    public void setPrice_btc (String price_btc)
    {
        this.price_btc = price_btc;
    }

    public String getMarket_cap_usd ()
    {
        return market_cap_usd;
    }

    public void setMarket_cap_usd (String market_cap_usd)
    {
        this.market_cap_usd = market_cap_usd;
    }

    public String getPrice_try() {
        return price_try;
    }

    public void setPrice_try(String price_try) {
        this.price_try = price_try;
    }

    public String getPrice_eur() {
        return price_eur;
    }

    public void setPrice_eur(String price_eur) {
        this.price_eur = price_eur;
    }

    public String getPrice_eth() {
        return price_eth;
    }

    public void setPrice_eth(String price_eth) {
        this.price_eth = price_eth;
    }

    @Override
    public int compareTo(@NonNull Coins otherCoins) {
        return this.getName().compareTo(otherCoins.getName());
    }

    public static final Comparator<Coins> price_comparator_desc = new Comparator<Coins>() {
        public int compare(Coins coin, Coins otherCoin) {
            return Integer.compare((int)Math.round(Double.parseDouble(coin.price_usd)),(int)Math.round(Double.parseDouble(otherCoin.price_usd)));
        }
    };

    public static final Comparator<Coins> price_comparator_asc = new Comparator<Coins>() {
        public int compare(Coins coin, Coins otherCoin) {
            return Integer.compare((int)Math.round(Double.parseDouble(otherCoin.price_usd)),(int)Math.round(Double.parseDouble(coin.price_usd)));
        }
    };



    @Override
    public String toString()
    {
        return "ClassPojo [total_supply = "+total_supply+", " + "id = "+id+", percent_change_24h = "+percent_change_24h+", 24h_volume_usd = "+volume_24h+", rank = "+rank+", symbol = "+symbol+", available_supply = "+available_supply+", percent_change_1h = "+percent_change_1h+", name = "+name+", price_usd = "+price_usd+", last_updated = "+last_updated+", percent_change_7d = "+percent_change_7d+", price_btc = "+price_btc+", market_cap_usd = "+market_cap_usd+"]";
    }
}