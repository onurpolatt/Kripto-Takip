package com.example.onurp.betc;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.example.onurp.betc.dialogs.SpinnerDialog;
import com.example.onurp.betc.eventbus.ActivityToFragmentEvent;
import com.example.onurp.betc.eventbus.GlobalBus;
import com.example.onurp.betc.eventbus.SendExchangeDataEvent;
import com.example.onurp.betc.fragments.HomeFragment;
import com.example.onurp.betc.graphs.GraphActivity;
import com.example.onurp.betc.interfaces.MarketsOnItemClickListener;
import com.example.onurp.betc.listener.GetStringCurrencyBack;
import com.example.onurp.betc.model.Articles;
import com.example.onurp.betc.model.Coins;
import com.example.onurp.betc.model.LargeDataPass;
import com.example.onurp.betc.model.News;
import com.example.onurp.betc.model.coins.CoinInfo;
import com.example.onurp.betc.model.coins.CoinListDeserializer;
import com.example.onurp.betc.model.exchanges.Exchanges;
import com.example.onurp.betc.model.exchanges.MarketCoinInfo;
import com.example.onurp.betc.model.markets.MarketDeserializer;
import com.example.onurp.betc.model.markets.Markets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

public class MarketDetailActivity extends AppCompatActivity implements GetStringCurrencyBack {
    private static final int DIALOG_REQUEST_CODE = 1;

    private RecyclerView.LayoutManager layoutManager;
    MarketCoinAPInterface apiCoinInterface;
    String marketName;
    private ArrayList<String> marketExchanges;
    private ArrayList<String> marketExchangesTemp;
    private ArrayList<String> marketCurrency;
    private Set<String> setMarketCurrency;
    private String currency;
    RecyclerView recyclerView;
    public HashMap<String,CoinInfo> hashMap;
    private static final int maxSize=60;
    private TextView textView;
    private Markets markets;
    @BindView(R.id.toolbar_market)Toolbar toolbar;
    @BindView(R.id.progressBar_markets)ProgressBar progressBar;
    @BindView(R.id.empty_view)TextView emptyTextView;
    @BindView(R.id.relative_nodata)RelativeLayout relativeLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        marketExchanges = new ArrayList<String>();
        marketCurrency = new ArrayList<String>();
        setMarketCurrency = new HashSet<String>();

        currency = "USD";
        Intent intent = getIntent();
        marketName= getIntent().getExtras().getString("MarketName");


        initView();
        getSupportActionBar().setTitle(marketName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        GlobalBus.getBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        marketCurrency.clear();
        marketExchanges.clear();
        setMarketCurrency.clear();
        GlobalBus.getBus().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(ActivityToFragmentEvent messageEvent){
        hashMap = messageEvent.getCoinData().getResult();
        Log.d("TAG","activity to fragment:"+messageEvent.getCoinData().getResult().get("BTC").getFullName());
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(SendExchangeDataEvent messageEvent){
        markets = messageEvent.getMarkets();
        progressBar.setVisibility(View.VISIBLE);
        marketExchanges.addAll(messageEvent.getMarkets().getExchangePairs().get(marketName).keySet());
        getCurreny(messageEvent.getMarkets());
        Log.d("TAG","activity to fragment:"+messageEvent.getMarkets().getExchangePairs().get(marketName).keySet());
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


    @Override
    public void onComplete(String currency) {
        textView.setText(currency);
        this.currency = currency;
        marketCurrency.clear();
        setMarketCurrency.clear();
        getCurreny(markets);
        progressBar.setVisibility(View.GONE);
    }

    public void initView(){
        if (getSupportActionBar() != null) {
            toolbar = (Toolbar) this.findViewById(R.id.toolbar_market);
            textView = (TextView)toolbar.findViewById(R.id.txt_currency);
            ImageView ımageView = (ImageView)toolbar.findViewById(R.id.img_down_arrows);
            ımageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeDoubleQuotes(marketCurrency);
                    SpinnerDialog dialog = SpinnerDialog.newInstance(marketCurrency);
                    dialog.show(getSupportFragmentManager(), "dialog");
                }
            });
        }
    }

    public ArrayList<String> removeDoubleQuotes(ArrayList<String> mCurrency) {

        for(int i=0;i<mCurrency.size();i++){
            marketCurrency.set(i, mCurrency.get(i).substring(1,mCurrency.get(i).length()-1));
        }

        return mCurrency;
    }


    public void getCurreny(Markets markets){
        marketExchangesTemp = new ArrayList<String>(marketExchanges);

        for(int i=0;i<marketExchangesTemp.size();i++){
            marketCurrency.addAll(Arrays.asList(markets.getExchangePairs().get(marketName).get(marketExchangesTemp.get(i))));

            if(marketExchangesTemp.get(i).contains("*") || (marketExchangesTemp.get(i) == currency)){
                marketExchangesTemp.remove(i);
                i--;
            }
            else if(!Arrays.asList(markets.getExchangePairs().get(marketName).get(marketExchangesTemp.get(i))).contains("\""+currency+"\""))
            {
                marketExchangesTemp.remove(i);
                i--;
            }
        }

        if(marketExchangesTemp.size() > maxSize){
            for (int i = marketExchangesTemp.size()-1; i >= maxSize; i--) {
                marketExchangesTemp.remove(i);
            }
        }
        setMarketCurrency.addAll(marketCurrency);
        Log.d("TAG","MARKET CURRENCY: "+setMarketCurrency+"----"+marketExchangesTemp);

        marketCurrency = new ArrayList<String>(setMarketCurrency);
        if(marketExchangesTemp.isEmpty()){
            relativeLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else {
            loadCoinData(marketExchangesTemp);
            relativeLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void loadCoinData(final ArrayList<String> marketExchangesTemp){
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_markets_coin);
        layoutManager = new LinearLayoutManager(getApplication());
        recyclerView.addItemDecoration(new ItemDivider(getApplication()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MarketCoinAdapter(getApplicationContext(), new ArrayList<MarketCoinInfo>(), hashMap));
        final String mExchanges = TextUtils.join(",",marketExchangesTemp);

        apiCoinInterface = MarketCoinAPIClient.getClient().create(MarketCoinAPInterface.class);
        Call<Exchanges> call = apiCoinInterface.getCoinInfo(mExchanges, currency, marketName);
        call.enqueue(new Callback<Exchanges>() {
            @Override
            public void onResponse(Call<Exchanges> call, Response<Exchanges> response) {
                Exchanges markets = response.body();
                final ArrayList<MarketCoinInfo> coins = new ArrayList<>();
                for(int i=0;i<marketExchangesTemp.size();i++){
                    Log.d("TAG","TIKLANAN COIN:");
                    coins.add(markets.getResults().get("DISPLAY").get(marketExchangesTemp.get(i)).get(currency));
                }
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setAdapter(new MarketCoinAdapter(getApplicationContext(), coins, hashMap, new MarketsOnItemClickListener() {
                    @Override
                    public void onMarketClick(View v, int position) {
                        Log.d("TAG","TIKLANAN COIN:"+ coins.get(position).getCOINNAME());
                        Intent intent = new Intent(MarketDetailActivity.this, GraphActivity.class);
                        intent.putExtra("currency",currency);
                        intent.putExtra("coinSymbol",coins.get(position).getCOINNAME());
                        intent.putExtra("marketName",marketName);
                        intent.putExtra("currencySymbol",coins.get(position).getTOSYMBOL());

                            if(hashMap.get(coins.get(position).getCOINNAME()) != null){
                                Log.d("TAG","COIN FULLNAME: "+ hashMap.get(coins.get(position).getCOINNAME()).getFullName());
                                intent.putExtra("coinFullName",hashMap.get(coins.get(position).getCOINNAME()).getFullName());
                                intent.putExtra("coinImageUrl",hashMap.get(coins.get(position).getCOINNAME()).getImageUrl());
                            }
                            else {
                                intent.putExtra("coinFullName",coins.get(position).getCOINNAME());
                                intent.putExtra("coinImageUrl","null");
                            }



                        startActivity(intent);
                    }
                }));

            }

            @Override
            public void onFailure(Call<Exchanges> call, Throwable t) {

            }
        });
    }

}
