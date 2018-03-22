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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by onurp on 21.03.2018.
 */

public class SpinnerAdapter extends RecyclerView.Adapter<SpinnerAdapter.MyViewHolder> {
    private List<String> dataSet;
    public Context mContext;
    LayoutInflater layoutInflater;

    public SpinnerAdapter(Context mContext, List<String> data) {
        this.mContext= mContext;
        this.dataSet = data;
        layoutInflater = LayoutInflater.from(mContext);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_currency)ImageView image;
        @BindView(R.id.txt_spinner_currency)TextView tCurrency;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }




    @Override
    public SpinnerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_spinner, parent, false);


        SpinnerAdapter.MyViewHolder myViewHolder = new SpinnerAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final SpinnerAdapter.MyViewHolder holder, final int listPosition) {
        String currency = dataSet.get(listPosition);
        holder.tCurrency.setText(currency);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
