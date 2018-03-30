package com.example.onurp.betc.model.coins;

import android.widget.SectionIndexer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CoinData implements Serializable {

    transient String response;

    transient String message;

    transient String baseImageUrl;

    transient String baseLinkUrl;

    transient String defaultWatchList;

    @SerializedName("Data")
    @Expose
    private HashMap<String, CoinInfo> result;

    public HashMap<String, CoinInfo> getResult() {
        return result;
    }

    public void setResult(HashMap<String, CoinInfo> result) {
        this.result = result;
    }
}
