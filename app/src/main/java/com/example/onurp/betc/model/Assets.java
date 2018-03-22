package com.example.onurp.betc.model;

/**
 * Created by onurp on 16.03.2018.
 */

public class Assets {
    private String assetName;
    private String assetSymbol;
    private String assetValue;
    private String assetAmount;

    public Assets(String assetName,String assetSymbol,String assetValue,String assetAmount){
        this.assetName=assetName;
        this.assetSymbol=assetSymbol;
        this.assetValue=assetValue;
        this.assetAmount=assetAmount;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetSymbol() {
        return assetSymbol;
    }

    public void setAssetSymbol(String assetSymbol) {
        this.assetSymbol = assetSymbol;
    }

    public String getAssetValue() {
        return assetValue;
    }

    public void setAssetValue(String assetValue) {
        this.assetValue = assetValue;
    }

    public String getAssetAmount() {
        return assetAmount;
    }

    public void setAssetAmount(String assetAmount) {
        this.assetAmount = assetAmount;
    }
}
