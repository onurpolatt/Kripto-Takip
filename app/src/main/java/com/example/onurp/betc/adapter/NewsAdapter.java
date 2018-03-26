package com.example.onurp.betc.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.example.onurp.betc.fragments.NewsFragment;
import com.example.onurp.betc.model.Articles;
import com.example.onurp.betc.model.Coins;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by onurp on 20.03.2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private List<Articles> dataSet;
    public Context mContext;
    LayoutInflater layoutInflater;

    public NewsAdapter(Context mContext, List<Articles> data) {
        this.mContext= mContext;
        this.dataSet = data;
        layoutInflater = LayoutInflater.from(mContext);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.thumbnail)ImageView thumbnail;
        @BindView(R.id.title_news)TextView newsTitle;
        @BindView(R.id.description)TextView description;
        @BindView(R.id.news_date)TextView newsDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }




    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_cardview_news, parent, false);


        NewsAdapter.MyViewHolder myViewHolder = new NewsAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final NewsAdapter.MyViewHolder holder, final int listPosition) {

        Articles articles = dataSet.get(listPosition);

        holder.newsTitle.setText(articles.getTitle());
        holder.description.setText(articles.getDescription());
        try {
            holder.newsDate.setText(sdf.parse(articles.getPublishedAt()).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(mContext).load(articles.getUrlToImage()).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CLICK", "resime tıklanıldı-" + listPosition);
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
