package com.example.onurp.betc.model.coins;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.onurp.betc.model.markets.MarketDeserializer;
import com.example.onurp.betc.model.markets.Markets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CoinListDeserializer implements JsonDeserializer<CoinData>{

    private static final String TAG = MarketDeserializer.class.getSimpleName();

    @Override
    public CoinData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject().get("Data").getAsJsonObject();
        final HashMap<String,CoinInfo> exchangePairs = readPairMap(jsonObject);
        CoinData result = new CoinData();
        result.setResult(exchangePairs);
        return result;
    }

    @Nullable
    private HashMap<String,CoinInfo> readPairMap(@NonNull final JsonObject jsonObject) {
        final HashMap<String,CoinInfo> result = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String mCoinName = entry.getKey();
            JsonObject mCoinInfo = entry.getValue().getAsJsonObject();
            String coinFullName;
            String coinImageUrl;

            if(mCoinInfo.get("FullName") == null){
                coinFullName = null;
            }
            else {
                coinFullName = mCoinInfo.get("FullName").getAsString();
            }
            if(mCoinInfo.get("ImageUrl") == null){
                coinImageUrl = null;
            }
            else{
                coinImageUrl = mCoinInfo.get("ImageUrl").getAsString();
            }

            CoinInfo coinInfo = new CoinInfo(coinImageUrl,coinFullName);


           result.put(mCoinName, coinInfo);
        }
        return result;
    }
}
