package com.example.onurp.betc.model.exchanges;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.onurp.betc.model.markets.Markets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExchangeDeserializer implements JsonDeserializer<Exchanges> {
    private static final String TAG = com.example.onurp.betc.model.exchanges.ExchangeDeserializer.class.getSimpleName();

    @Override
    public Exchanges deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final Map<String,Map<String,Map<String, MarketCoinInfo>>>exchangePairs = readPairMap(jsonObject);
        Exchanges result = new Exchanges();
        result.setResults(exchangePairs);

        return result;
    }

    @Nullable
    private Map<String,Map<String,Map<String, MarketCoinInfo>>> readPairMap(@NonNull final JsonObject jsonObject) {
        final Map<String,Map<String,Map<String, MarketCoinInfo>>> result = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String exchange = entry.getKey();
            String fromSymbol;
            String tSymbol;
            JsonObject toSymbol;
            JsonObject fsymbolObj = entry.getValue().getAsJsonObject();

            final Map<String,Map<String, MarketCoinInfo>> pairsPerCoin = new HashMap<>();

            for (Map.Entry<String, JsonElement> mEntry : fsymbolObj.entrySet()) {
                tSymbol = mEntry.getKey();
                toSymbol = mEntry.getValue().getAsJsonObject();

                    final Map<String, MarketCoinInfo> innerObject = new HashMap<>();

                    for (Map.Entry<String, JsonElement> innerEntry : toSymbol.entrySet()) {
                        fromSymbol = innerEntry.getKey();
                        toSymbol = innerEntry.getValue().getAsJsonObject();

                        String price = toSymbol.get("PRICE").getAsString();
                        String volume = toSymbol.get("VOLUME24HOURTO").getAsString();
                        String fSymbol = toSymbol.get("FROMSYMBOL").getAsString();
                        String currencySymbol = toSymbol.get("TOSYMBOL").getAsString();
                        String cPercent = toSymbol.get("CHANGEPCT24HOUR").getAsString();
                        String coinName = tSymbol;

                        MarketCoinInfo marketCoinInfo = new MarketCoinInfo(price,volume,fSymbol,cPercent,tSymbol,currencySymbol);
                        innerObject.put(fromSymbol,marketCoinInfo);
                    }
                pairsPerCoin.put(tSymbol, innerObject);
            }
            result.put(exchange, pairsPerCoin);
        }
        return result;
    }

}