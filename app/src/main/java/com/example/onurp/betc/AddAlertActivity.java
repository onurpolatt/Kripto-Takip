package com.example.onurp.betc;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.onurp.betc.adapter.FavoriteAdapter;
import com.example.onurp.betc.adapter.RecycleAdapter;
import com.example.onurp.betc.adapter.SpinnerCoinListAdapter;
import com.example.onurp.betc.adapter.SpinnerCurrencyAdapter;
import com.example.onurp.betc.api.APIClient;
import com.example.onurp.betc.api.APInterface;
import com.example.onurp.betc.api.CurrentPriceAPIClient;
import com.example.onurp.betc.api.CurrentPriceAPInterface;
import com.example.onurp.betc.eventbus.ActivityToFragmentEvent;
import com.example.onurp.betc.eventbus.GlobalBus;
import com.example.onurp.betc.eventbus.SendExchangeDataEvent;
import com.example.onurp.betc.graphs.GraphActivity;
import com.example.onurp.betc.model.Coins;
import com.example.onurp.betc.model.Currency;
import com.example.onurp.betc.model.CurrentPrice;
import com.example.onurp.betc.model.FavCoins;
import com.example.onurp.betc.model.coins.CoinInfo;
import com.example.onurp.betc.model.exchanges.MarketCoinInfo;
import com.example.onurp.betc.model.markets.Markets;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableListDialog;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.fabiomsr.moneytextview.MoneyTextView;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAlertActivity extends AppCompatActivity {
    private Markets markets;
    private static String toolbarTitle = "Add Alert";
    public HashMap<String,CoinInfo> hashMap;
    private Boolean isFabButtonClicked = null;
    private ArrayList<String> coinSymbolList;
    private ArrayList<Currency> currencyList;
    private ArrayList<CoinInfo> arraylistCoinInfo;
    private DatabaseReference databaseReference;
    private ValueEventListener mListener;
    private CurrentPriceAPInterface apiInterface;
    private String marketName = "CCCAGG";
    private String defMarketName = "CCCAGG";
    private Set<String> setMarketCurrency;

    @BindView(R.id.greater_or_less_spinner)SearchableSpinner glSpinner;
    @BindView(R.id.from_symbol_spinner)SearchableSpinner fromSymbolSpinner;
    @BindView(R.id.to_symbol_spinner)SearchableSpinner toSymbolSpinner;
    @BindView(R.id.exchange_spinner)SearchableSpinner exchangeSpinner;
    @BindView(R.id.toolbar_add_alert)Toolbar toolbar;
    @BindView(R.id.txt_current_value)MoneyTextView moneyTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alert);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        fetchCurrencyData();
        String[] names = new String[]{">","<"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(AddAlertActivity.this,android.R.layout.simple_spinner_dropdown_item,names);
        setMarketCurrency = new HashSet<String>();
        coinSymbolList = new ArrayList<String>();
        arraylistCoinInfo = new ArrayList<CoinInfo>();


        glSpinner.setAdapter(arrayAdapter);


        isFabButtonClicked = getIntent().getBooleanExtra("add_alert",true);

        getSupportActionBar().setTitle(toolbarTitle);
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
        Fragment searchableSpinnerDialog = getFragmentManager().findFragmentByTag("TAG");

        if (searchableSpinnerDialog != null && searchableSpinnerDialog.isAdded()) {
            getFragmentManager().beginTransaction().remove(searchableSpinnerDialog).commit();
        }

        GlobalBus.getBus().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(ActivityToFragmentEvent messageEvent){
        hashMap = messageEvent.getCoinData().getResult();
        final Map<String,CoinInfo> newSortedMap = SortHashMapByValue();

        coinSymbolList.addAll(newSortedMap.keySet());
        arraylistCoinInfo.addAll(newSortedMap.values());
        SpinnerCoinListAdapter spinnerCoinListAdapter = new SpinnerCoinListAdapter(getApplicationContext(),coinSymbolList,R.layout.row_spinner_coinlist,arraylistCoinInfo);
        fromSymbolSpinner.setAdapter(spinnerCoinListAdapter);
        fromSymbolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TAG","SET LIST"+currencyList.get(toSymbolSpinner.getSelectedItemPosition()).toString());

                if(!isCoinAvailableİnMarket(coinSymbolList.get(i),
                        currencyList.get(toSymbolSpinner.getSelectedItemPosition()).toString().split("[\\(\\)]")[1],
                        marketName,markets)){
                    marketName = defMarketName;
                    exchangeSpinner.setSelection(0);
                    Log.d("TAG","SET LIST-"+currencyList.get(toSymbolSpinner.getSelectedItemPosition()).toString());
                }
                else{
                    Log.d("TAG","SET LIST*"+currencyList.get(toSymbolSpinner.getSelectedItemPosition()).toString());
                    getCurrentPrice(coinSymbolList.get(i),currencyList.get(toSymbolSpinner.getSelectedItemPosition()).getCc(),marketName);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public Map<String,CoinInfo> SortHashMapByValue(){
        Set<Map.Entry<String,CoinInfo>> mapEntries = hashMap.entrySet();
        List<Map.Entry<String,CoinInfo>> aList = new LinkedList<Map.Entry<String,CoinInfo>>(mapEntries);

        Collections.sort(aList, new Comparator<Map.Entry<String,CoinInfo>>() {
            @Override
            public int compare(Map.Entry<String, CoinInfo> ele1,
                               Map.Entry<String, CoinInfo> ele2) {

                return Integer.parseInt(ele1.getValue().getSortOrder())-Integer.parseInt(ele2.getValue().getSortOrder());
            }
        });

        Map<String,CoinInfo> aMap2 = new LinkedHashMap<String, CoinInfo>();
        for(Map.Entry<String,CoinInfo> entry: aList) {
            aMap2.put(entry.getKey(), entry.getValue());
        }
        return  aMap2;
    }

    public void getCurrentPrice(String fSymbol,String tSymbol,String marketName){
        apiInterface = CurrentPriceAPIClient.getClient().create(CurrentPriceAPInterface.class);
        Call<Map<String, String>> call = apiInterface.getCoinPrice(fSymbol,tSymbol,marketName);
        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                Map<String, String> map = response.body();
                String cPrice = map.values().toString().replaceAll("\\[", "").replaceAll("\\]","");
                moneyTextView.setAmount(Float.parseFloat(cPrice));
                moneyTextView.setSymbol(currencyList.get(toSymbolSpinner.getSelectedItemPosition()).getSymbol());
                StartAnimations();
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {

            }
        });
    }

    public void StartAnimations(){
        ObjectAnimator animationBase = ObjectAnimator.ofInt(moneyTextView, "baseColor", Color.GREEN, Color.BLACK);
        ObjectAnimator animationDecimal = ObjectAnimator.ofInt(moneyTextView, "decimalsColor", Color.GREEN, Color.BLACK);
        ObjectAnimator animationSymbol = ObjectAnimator.ofInt(moneyTextView, "symbolColor", Color.GREEN, Color.BLACK);

        animationBase.setDuration(500);
        animationBase.setEvaluator(new ArgbEvaluator());
        animationBase.start();

        animationDecimal.setDuration(500);
        animationDecimal.setEvaluator(new ArgbEvaluator());
        animationDecimal.start();

        animationSymbol.setDuration(500);
        animationSymbol.setEvaluator(new ArgbEvaluator());
        animationSymbol.start();
    }

    public void fetchCurrencyData(){
        currencyList = new ArrayList<Currency>();
        mListener = databaseReference.child("currency").orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Currency currencies = child.getValue(Currency.class);
                    currencyList.add(currencies);
                }
                SpinnerCurrencyAdapter toSymbolAdapter = new SpinnerCurrencyAdapter(getApplicationContext(),currencyList);
                toSymbolSpinner.setAdapter(toSymbolAdapter);
                toSymbolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        //Log.d("TAG","SET LIST"+toSymbolSpinner.getSelectedItem().toString().split("[\\(\\)]")[1]);
                        if(!isCoinAvailableİnMarket(coinSymbolList.get(fromSymbolSpinner.getSelectedItemPosition()), coinSymbolList.get(i), marketName,markets)){
                            marketName = defMarketName;
                            exchangeSpinner.setSelection(0);
                            Log.d("TAG","SET LIST-"+currencyList.get(toSymbolSpinner.getSelectedItemPosition()).toString());
                        }
                        else{
                            Log.d("TAG","SET LIST*"+currencyList.get(toSymbolSpinner.getSelectedItemPosition()).toString());
                            getCurrentPrice(coinSymbolList.get(fromSymbolSpinner.getSelectedItemPosition()),currencyList.get(i).getCc(),marketName);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(SendExchangeDataEvent messageEvent){
        markets = messageEvent.getMarkets();
        ArrayList<String> exchangeName = new ArrayList<String>();
        Log.d("TAG","activity to fragment:"+markets.getExchangePairs());
        exchangeName.add(0,"All Exchanges");
        exchangeName.addAll(markets.getExchangePairs().keySet());
        ArrayAdapter<String> exchangeAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.simple_spinner_dropdown_item,exchangeName);
        exchangeSpinner.setAdapter(exchangeAdapter);
        exchangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setMarketCurrency.clear();
                String tSymbol = toSymbolSpinner.getSelectedItem().toString().split("[\\(\\)]")[1];
                String fSymbol = fromSymbolSpinner.getSelectedItem().toString().split("[\\(\\)]")[1];
                marketName = exchangeSpinner.getSelectedItem().toString() == "All Exchanges"?defMarketName:exchangeSpinner.getSelectedItem().toString();
                if(!isCoinAvailableİnMarket(fSymbol,tSymbol,marketName,markets)){
                    marketName = defMarketName;
                    exchangeSpinner.setSelection(0);
                }
                else{
                    getCurrentPrice(fSymbol,tSymbol,marketName);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public Boolean isCoinAvailableİnMarket(String fromSymbol,String toSymbol,String marketName,Markets markets){
        boolean isfSymbolContain = false;
        boolean istSymbolContain = false;
        boolean returnValue = false;

        if(marketName != defMarketName ){
            ArrayList<String> marketExchangesTemp = new ArrayList<String>(markets.getExchangePairs().get(marketName).keySet());
            ArrayList<String> marketCurrency = new ArrayList<String>();
            isfSymbolContain = markets.getExchangePairs().get(marketName).keySet().toString().contains(fromSymbol);

            for(int i=0;i<marketExchangesTemp.size();i++){
                marketCurrency.addAll(Arrays.asList(markets.getExchangePairs().get(marketName).get(marketExchangesTemp.get(i))));
            }
            setMarketCurrency.addAll(marketCurrency);

            istSymbolContain = setMarketCurrency.contains("\""+toSymbol+"\"");
            if(isfSymbolContain && istSymbolContain){
                returnValue = true;
            }
        }
        else{
            returnValue = true;
        }

        return returnValue;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_alert_item, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {

    if(!isFabButtonClicked){
        menu.findItem(R.id.delete_alert).setVisible(true);
    }
    else{
        menu.findItem(R.id.delete_alert).setVisible(false);
    }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.delete_alert:

                return true;
            case R.id.add_alert:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
