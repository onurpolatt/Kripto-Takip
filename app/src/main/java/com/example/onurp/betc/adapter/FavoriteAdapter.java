package com.example.onurp.betc.adapter;

import android.content.Context;
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
import com.example.onurp.betc.interfaces.MarketsOnItemClickListener;
import com.example.onurp.betc.model.FavCoins;
import com.example.onurp.betc.model.coins.CoinInfo;
import com.example.onurp.betc.model.exchanges.MarketCoinInfo;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>{
    private static String BASE_URL = "https://www.cryptocompare.com";
    private ArrayList<MarketCoinInfo> dataSet;
    private ArrayList<FavCoins> favCoins;
    public Context mContext;
    public LayoutInflater layoutInflater;
    public MarketsOnItemClickListener marketsOnItemClickListener;

    public FavoriteAdapter(Context mContext, ArrayList<MarketCoinInfo> data,ArrayList<FavCoins> favCoins,MarketsOnItemClickListener marketsOnItemClickListener) {
        this.mContext = mContext;
        this.dataSet = data;
        this.favCoins = favCoins;
        this.marketsOnItemClickListener = marketsOnItemClickListener;
        layoutInflater = LayoutInflater.from(mContext);
        Log.d("MARKET", "MARKET constructor");
    }

    public FavoriteAdapter(Context mContext, ArrayList<MarketCoinInfo> data,ArrayList<FavCoins> favCoins) {
        this.mContext = mContext;
        this.dataSet = data;
        this.favCoins = favCoins;
        this.marketsOnItemClickListener = marketsOnItemClickListener;
        layoutInflater = LayoutInflater.from(mContext);
        Log.d("MARKET", "MARKET constructor");
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.coin_image_markets)ImageView mImage;
        @BindView(R.id.coin_name_markets)TextView mName;
        @BindView(R.id.market_text)TextView mMarketName;
        @BindView(R.id.coin_price_markets)TextView mPrice;
        @BindView(R.id.percent_change_markets)TextView mPercent;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            Log.d("MARKET COIN", "MARKET COIN view holder");
        }
    }




    @Override
    public FavoriteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_favorites, parent, false);


        final FavoriteAdapter.MyViewHolder myViewHolder = new FavoriteAdapter.MyViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marketsOnItemClickListener.onMarketClick(v, myViewHolder.getAdapterPosition());
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final FavoriteAdapter.MyViewHolder holder, final int listPosition) {

        MarketCoinInfo markets = dataSet.get(listPosition);

        if(favCoins.get(listPosition).getCoinImageUrl() != "null"){
            Glide.with(mContext).load(BASE_URL+favCoins.get(listPosition).getCoinImageUrl()).into(holder.mImage);
        }
        else {
            Glide.with(mContext).load(R.drawable.ic_crypto).into(holder.mImage);
        }
        holder.mName.setText(favCoins.get(listPosition).getCoinFullName());
        holder.mMarketName.setText(favCoins.get(listPosition).getMarketName());
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
