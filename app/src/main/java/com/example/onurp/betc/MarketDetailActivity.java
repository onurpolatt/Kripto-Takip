package com.example.onurp.betc;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.onurp.betc.adapter.MarketCoinAdapter;
import com.example.onurp.betc.adapter.NewsAdapter;
import com.example.onurp.betc.adapter.RecycleAdapter;
import com.example.onurp.betc.api.APIClient;
import com.example.onurp.betc.api.APInterface;
import com.example.onurp.betc.api.MarketAPInterface;
import com.example.onurp.betc.api.MarketCoinAPIClient;
import com.example.onurp.betc.api.MarketCoinAPInterface;
import com.example.onurp.betc.api.MarketsAPIClient;
import com.example.onurp.betc.api.NewsAPIClient;
import com.example.onurp.betc.api.NewsAPInterface;
import com.example.onurp.betc.model.Articles;
import com.example.onurp.betc.model.Coins;
import com.example.onurp.betc.model.LargeDataPass;
import com.example.onurp.betc.model.News;
import com.example.onurp.betc.model.coins.CoinInfo;
import com.example.onurp.betc.model.exchanges.Exchanges;
import com.example.onurp.betc.model.exchanges.MarketCoinInfo;
import com.example.onurp.betc.model.markets.MarketDeserializer;
import com.example.onurp.betc.model.markets.Markets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

/**
 * Created by onurp on 26.03.2018.
 */

public class MarketDetailActivity extends AppCompatActivity {
    private static MarketCoinAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    MarketAPInterface apiInterface;
    MarketCoinAPInterface apiCoinInterface;
    String marketName;
    private ArrayList<String> marketExchanges;
    private ArrayList<String> marketExchangesTemp;
    private ArrayList<String> marketCurrency;
    private Set<String> setMarketCurrency;
    private String currency;
    RecyclerView recyclerView;
    private LinearLayout linearLayout;
    public HashMap<String,CoinInfo> hashMap;
    private final int maxSize=300;
    @BindView(R.id.toolbar_market)Toolbar toolbar;
    @BindView(R.id.progressBar_markets)ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        currency = "USD";
        Intent intent = getIntent();
        marketName= getIntent().getExtras().getString("MarketName");
        int sync = getIntent().getIntExtra("bigdata:synccode", -1);
        hashMap = LargeDataPass.get().getLargeData(sync);
        Log.d("TAG","ETHEREUM "+hashMap.get("ETH").getFullName());
        loadExchangeJSON();
        getSupportActionBar().setTitle(marketName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadExchangeJSON(){
        progressBar.setVisibility(View.VISIBLE);
        apiInterface = MarketsAPIClient.getClient().create(MarketAPInterface.class);
        Call<Markets> call = apiInterface.getMarket();
        call.enqueue(new Callback<Markets>() {
            @Override
            public void onResponse(Call<Markets> call, Response<Markets> response) {
                Markets markets = response.body();
                marketExchanges = new ArrayList<String>();
                marketCurrency = new ArrayList<String>();
                setMarketCurrency = new HashSet<String>();
                marketExchanges.addAll(markets.getExchangePairs().get(marketName).keySet());
                getCurreny(markets);
            }

            @Override
            public void onFailure(Call<Markets> call, Throwable t) {

            }
        });
    }

    public void getCurreny(Markets markets){
        marketExchangesTemp = new ArrayList<String>(marketExchanges);

        for(int i=0;i<marketExchangesTemp.size();i++){
            if(marketExchangesTemp.get(i).contains("*") || (marketExchangesTemp.get(i) == currency)){
                marketExchangesTemp.remove(i);
                i--;
            }
            else if(!Arrays.asList(markets.getExchangePairs().get(marketName).get(marketExchangesTemp.get(i))).contains("\""+currency+"\""))
            {
                marketCurrency.addAll(Arrays.asList(markets.getExchangePairs().get(marketName).get(marketExchangesTemp.get(i))));
                marketExchangesTemp.remove(i);
                i--;
            }
        }
        setMarketCurrency.addAll(marketCurrency);
        Log.d("TAG","MARKET CURRENCY: "+setMarketCurrency+"----"+marketExchangesTemp);
        marketCurrency = new ArrayList<String>(setMarketCurrency);
        loadCoinData(marketExchangesTemp);
    }

    public void loadCoinData(final ArrayList<String> marketExchangesTemp){
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_markets_coin);
        layoutManager = new LinearLayoutManager(getApplication());
        recyclerView.addItemDecoration(new ItemDivider(getApplication()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MarketCoinAdapter(getApplicationContext(),new ArrayList<MarketCoinInfo>(),hashMap));

        final String mExchanges = TextUtils.join(",",marketExchangesTemp);
        apiCoinInterface = MarketCoinAPIClient.getClient().create(MarketCoinAPInterface.class);
        Call<Exchanges> call = apiCoinInterface.getCoinInfo(mExchanges, currency, marketName);
        call.enqueue(new Callback<Exchanges>() {
            @Override
            public void onResponse(Call<Exchanges> call, Response<Exchanges> response) {
                Exchanges markets = response.body();
                ArrayList<MarketCoinInfo> coins = new ArrayList<>();

                for(int i=0;i<(marketExchangesTemp.size()>maxSize?maxSize:marketExchangesTemp.size());i++){
                    coins.add(markets.getResults().get("DISPLAY").get(marketExchangesTemp.get(i)).get(currency));
                }
                progressBar.setVisibility(View.INVISIBLE);
                adapter = new MarketCoinAdapter(getApplicationContext(),coins,hashMap);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Exchanges> call, Throwable t) {

            }
        });
    }
}
