package com.example.onurp.betc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.onurp.betc.R;
import com.example.onurp.betc.model.Coins;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by onurp on 10.03.2018.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    private ArrayList<Coins> dataSet;
    private ArrayList<Coins> mFilteredList;
    private String currency;
    public Context mContext;
    LayoutInflater layoutInflater;

    public RecycleAdapter(Context mContext, ArrayList<Coins> data,String currency) {
        this.mContext=mContext;
        this.dataSet = data;
        this.currency = currency;
        layoutInflater = LayoutInflater.from(mContext);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.coin_symbol)TextView coinSymbol;
        @BindView(R.id.coin_name)TextView coinName;
        @BindView(R.id.volume24h)TextView coinVolume;
        @BindView(R.id.coin_price)TextView coinPrice;
        @BindView(R.id.percent_change)TextView coinPercentChange;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        System.out.println("DENEME "+dataSet.get(listPosition).getSymbol());
        Coins coins = dataSet.get(listPosition);
        holder.coinSymbol.setText(coins.getSymbol());
        holder.coinName.setText(coins.getName());
        holder.coinVolume.setText(changeVolumeFormat(coins.getVolume_24h()));

        switch (currency){
            case "USD":
                holder.coinPrice.setText(changePriceFormat(coins.getPrice_usd(),currency));
                break;
            case "BTC":
                holder.coinPrice.setText(changePriceFormat(coins.getPrice_btc(),currency));
                break;
            case "EUR":
                holder.coinPrice.setText(changePriceFormat(coins.getPrice_eur(),currency));
                break;
            case "TRY":
                holder.coinPrice.setText(changePriceFormat(coins.getPrice_try(),currency));
                break;
            case "ETH":
                holder.coinPrice.setText(changePriceFormat(coins.getPrice_eth(),currency));
                break;
        }


        if(!lessThanZero(coins.getPercent_change_24h())){
            holder.coinPercentChange.setTextColor(Color.parseColor("#8BC34A"));
        }
        else {
            holder.coinPercentChange.setTextColor(Color.parseColor("#F44336"));
        }

        holder.coinPercentChange.setText(changePercentFormat(coins.getPercent_change_24h()));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public void setFilter(List<Coins> coins) {
        dataSet = new ArrayList<>();
        dataSet.addAll(coins);
        notifyDataSetChanged();
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

    public String changePriceFormat(String price_usd,String currency){
        DecimalFormat myFormatter = new DecimalFormat();
        switch (currency){
            case "USD":
                myFormatter = new DecimalFormat("\u0024###,###.########");
                break;
            case "BTC":
                myFormatter = new DecimalFormat("\u0243###,###.########");
                break;
            case "EUR":
                myFormatter = new DecimalFormat("\u20AC###,###.########");
                break;
            case "TRY":
                myFormatter = new DecimalFormat("\u20BA###,###.########");
                break;
            case "ETH":
                myFormatter = new DecimalFormat("\u039E###,###.########");
                break;
        }

        String newFormat = myFormatter.format(Double.parseDouble(price_usd));
        return  newFormat;
    }

    public String changeVolumeFormat(String volume_24h){
        DecimalFormat myFormatter = new DecimalFormat("$###,###.###");
        String newFormat = myFormatter.format(Double.parseDouble(volume_24h));
        return  newFormat;
    }

    public String changePercentFormat(String percent_change_24h){
        DecimalFormat myFormatter = new DecimalFormat(" #,##0.00 '%'");
        String newFormat = myFormatter.format(Double.parseDouble(percent_change_24h));
        return  newFormat;
    }
}