package com.example.onurp.betc.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onurp.betc.ItemDivider;
import com.example.onurp.betc.R;
import com.example.onurp.betc.adapter.MarketAdapter;
import com.example.onurp.betc.adapter.RecycleAdapter;
import com.example.onurp.betc.adapter.SpinnerAdapter;
import com.example.onurp.betc.model.Coins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by onurp on 23.03.2018.
 */

public class MarketFragment extends Fragment {
    private List<Integer> marketLogo;

    private String[] myMarkets;
    private MarketAdapter marketAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market, container, false);
        //ButterKnife.bind(this,view);
        marketLogo = Arrays.asList(R.drawable.bitfinex,R.drawable.bitstamp,R.drawable.coinbase,
                R.drawable.kraken,R.drawable.gemini,R.drawable.hitbtc,R.drawable.itbit,R.drawable.poloniex,R.drawable.bittrex,
                R.drawable.trustdex,R.drawable.cexio,R.drawable.lakebtc,R.drawable.livecoin,R.drawable.coincap,R.drawable.exmo);

        String[] myMarkets = getResources().getStringArray(R.array.markets);
        List<String> myMarketsList = Arrays.asList(myMarkets);

        Log.d("MARKET", "MARKETS"+myMarketsList);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_markets);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new MarketAdapter(getContext(),myMarketsList,marketLogo));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
