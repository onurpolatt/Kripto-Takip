package com.example.onurp.betc.model.markets;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MarketDeserializer implements JsonDeserializer<Markets> {
    private static final String TAG = MarketDeserializer.class.getSimpleName();

    @Override
    public Markets deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final Map<String, Map<String, String[]>> exchangePairs = readPairMap(jsonObject);
        Markets result = new Markets();
        result.setExchangePairs(exchangePairs);
        return result;
    }

    @Nullable
    private Map<String, Map<String, String[]>> readPairMap(@NonNull final JsonObject jsonObject) {
        final Map<String, Map<String, String[]>> result = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String exchange = entry.getKey();
            String fromSymbol;
            String[] toSymbols;
            JsonObject fsymbolObj = entry.getValue().getAsJsonObject();

            final Map<String, String[]> pairsPerCoin = new HashMap<>();

            for (Map.Entry<String, JsonElement> mEntry : fsymbolObj.entrySet()) {
                fromSymbol = mEntry.getKey();
                toSymbols = toStringArray(mEntry.getValue().getAsJsonArray());
                pairsPerCoin.put(fromSymbol, toSymbols);
            }
            result.put(exchange, pairsPerCoin);
            Log.d("TAG","PAIRS:"+exchange);
        }
        return result;
    }

    private static String[] toStringArray(JsonArray array) {
        if (array == null) return null;
        String[] arr = new String[array.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = array.get(i).toString();
        }
        return arr;
    }
}
