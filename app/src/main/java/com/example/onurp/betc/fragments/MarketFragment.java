package com.example.onurp.betc.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.onurp.betc.ItemDivider;
import com.example.onurp.betc.MarketDetailActivity;
import com.example.onurp.betc.R;
import com.example.onurp.betc.adapter.MarketAdapter;
import com.example.onurp.betc.adapter.RecycleAdapter;
import com.example.onurp.betc.adapter.SpinnerAdapter;
import com.example.onurp.betc.api.AllCoinAPIClient;
import com.example.onurp.betc.api.AllCoinAPInterface;
import com.example.onurp.betc.api.MarketAPInterface;
import com.example.onurp.betc.api.MarketsAPIClient;
import com.example.onurp.betc.interfaces.MarketsOnItemClickListener;
import com.example.onurp.betc.model.Coins;
import com.example.onurp.betc.model.LargeDataPass;
import com.example.onurp.betc.model.coins.CoinData;
import com.example.onurp.betc.model.coins.CoinInfo;
import com.example.onurp.betc.model.markets.Markets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by onurp on 23.03.2018.
 */

public class MarketFragment extends Fragment {
    private List<Integer> marketLogo;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private LinearLayout linearLayout;
    private Toolbar toolbar;
    private Boolean firstTime = null;
    private AllCoinAPInterface apiInterface;
    private  List<String> myMarketsList;
    private HashMap<String,CoinInfo> hashMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market, container, false);
        ButterKnife.bind(this,view);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        linearLayout = (LinearLayout)toolbar.findViewById(R.id.spinnerCurrency);
        marketLogo = Arrays.asList(R.drawable.bitfinex,R.drawable.bitstamp,R.drawable.coinbase,
                R.drawable.kraken,R.drawable.gemini,R.drawable.hitbtc,R.drawable.itbit,R.drawable.poloniex,R.drawable.bittrex,
                R.drawable.trustdex,R.drawable.cexio,R.drawable.lakebtc,R.drawable.livecoin,R.drawable.coincap,R.drawable.exmo);


        String[] myMarkets = getResources().getStringArray(R.array.markets);
        myMarketsList = Arrays.asList(myMarkets);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_markets);
        initView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Exchanges");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    public void initView(){
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new MarketAdapter(getContext(), myMarketsList, marketLogo, new MarketsOnItemClickListener() {
            @Override
            public void onMarketClick(View v, int position) {
                String marketName = myMarketsList.get(position);
                Bundle extras = new Bundle();
                Intent intent = new Intent(getActivity(), MarketDetailActivity.class);
                int sync = LargeDataPass.get().setLargeData(hashMap);
                intent.putExtra("MarketName",marketName);
                intent.putExtra("bigdata:synccode", sync);
                Log.d("MARKET", "MARKET NAME"+myMarketsList.get(position));
                startActivity(intent);
            }
        }));
        linearLayout.setVisibility(View.GONE);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        firstTime = null;
    }
}
