package com.example.onurp.betc.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.onurp.betc.R;
import com.example.onurp.betc.WebViewActivity;
import com.example.onurp.betc.interfaces.MarketsOnItemClickListener;
import com.example.onurp.betc.model.coins.CoinInfo;
import com.example.onurp.betc.model.exchanges.MarketCoinInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarketCoinAdapter extends RecyclerView.Adapter<MarketCoinAdapter.MyViewHolder> {

    private ArrayList<MarketCoinInfo> dataSet;
    private HashMap<String,CoinInfo> coinList;
    public Context mContext;
    public LayoutInflater layoutInflater;

    public MarketCoinAdapter(Context mContext, ArrayList<MarketCoinInfo> data, HashMap<String,CoinInfo> coinList) {
        this.mContext= mContext;
        this.dataSet = data;
        this.coinList = coinList;
        layoutInflater = LayoutInflater.from(mContext);
        Log.d("MARKET", "MARKET constructor");
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.coin_symbol)TextView mSymbol;
        @BindView(R.id.coin_name)TextView mName;
        @BindView(R.id.volume24h)TextView mVolume;
        @BindView(R.id.coin_price)TextView mPrice;
        @BindView(R.id.percent_change)TextView mPercent;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            Log.d("MARKET COIN", "MARKET COIN view holder");
        }
    }




    @Override
    public MarketCoinAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout, parent, false);


        final MarketCoinAdapter.MyViewHolder myViewHolder = new MarketCoinAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MarketCoinAdapter.MyViewHolder holder, final int listPosition) {

        MarketCoinInfo markets = dataSet.get(listPosition);
         Log.d("TAG","DOGE INFO"+coinList.get(markets.getCOINNAME()));
        holder.mName.setText(coinList.get(markets.getCOINNAME())!=null?coinList.get(markets.getCOINNAME()).getFullName():markets.getCOINNAME());
        holder.mSymbol.setText(markets.getCOINNAME());
        holder.mVolume.setText(markets.getVOLUME24HOUR());
        holder.mPrice.setText(markets.getPRICE());
        holder.mPercent.setText(markets.getCHANGEPCT24HOUR());

        if(!lessThanZero(markets.getCHANGEPCT24HOUR())){
            holder.mPercent.setTextColor(Color.parseColor("#8BC34A"));
        }
        else {
            holder.mPercent.setTextColor(Color.parseColor("#F44336"));
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public boolean lessThanZero(String percentChange){
        boolean result;
        if(Double.parseDouble(percentChange) > 0){
            result = false;
        } else{
            result = true;
        }
        return result;
    }
}
