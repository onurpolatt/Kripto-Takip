package com.example.onurp.betc.model;

import com.example.onurp.betc.model.coins.CoinInfo;

import java.util.HashMap;

public class LargeDataPass{

   public static LargeDataPass instance;

    public synchronized static LargeDataPass get() {
        if (instance == null) {
            instance = new LargeDataPass ();
        }
        return instance;
    }

    private int sync = 0;

    private HashMap<String,CoinInfo> largeData;
    public  int setLargeData(HashMap<String,CoinInfo> largeData) {
        this.largeData = largeData;
        return ++sync;
    }

    public HashMap<String,CoinInfo> getLargeData(int request) {
        return (request == sync) ? largeData : null;
    }
}
