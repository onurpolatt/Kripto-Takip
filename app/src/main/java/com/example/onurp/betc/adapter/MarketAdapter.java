package com.example.onurp.betc.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.onurp.betc.model.Articles;

import java.text.ParseException;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by onurp on 23.03.2018.
 */

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.MyViewHolder> {
    private List<String> dataSet;
    private List<Integer> marketImages;
    public Context mContext;
    LayoutInflater layoutInflater;

    public MarketAdapter(Context mContext, List<String> data,List<Integer> marketImages) {
        this.mContext= mContext;
        this.dataSet = data;
        this.marketImages = marketImages;
        layoutInflater = LayoutInflater.from(mContext);
        Log.d("MARKET", "MARKET constructor");
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.market_image)ImageView mImage;
        @BindView(R.id.market_name)TextView mName;
        @BindView(R.id.market_arrow)ImageView mArrow;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            Log.d("MARKET", "MARKET view holder");
        }
    }




    @Override
    public MarketAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_markets, parent, false);


        MarketAdapter.MyViewHolder myViewHolder = new MarketAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MarketAdapter.MyViewHolder holder, final int listPosition) {

        String markets = dataSet.get(listPosition);
        Log.d("MARKET", "MARKETS"+markets);
        holder.mName.setText(markets);
        Glide.with(mContext).load(marketImages.get(listPosition)).into(holder.mImage);

        holder.mArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CLICK", "markete tıklanıldı-" + listPosition);
                Intent intent= new Intent(mContext, WebViewActivity.class);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
