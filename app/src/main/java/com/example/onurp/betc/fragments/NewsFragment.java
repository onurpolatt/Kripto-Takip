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
import android.widget.ProgressBar;

import com.example.onurp.betc.ItemDivider;
import com.example.onurp.betc.R;
import com.example.onurp.betc.adapter.NewsAdapter;
import com.example.onurp.betc.adapter.RecycleAdapter;
import com.example.onurp.betc.api.NewsAPIClient;
import com.example.onurp.betc.api.NewsAPInterface;
import com.example.onurp.betc.model.Articles;
import com.example.onurp.betc.model.Coins;
import com.example.onurp.betc.model.News;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by onurp on 20.03.2018.
 */

public class NewsFragment extends Fragment {
    private final static String API_KEY = "c1266f923fb94b1d844afeccb1e22e4f";
    private final static String TAG = "NEWS";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static NewsAdapter adapter;
    @BindView(R.id.progressBar_news)ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this,view);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view_news);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new NewsAdapter(getContext(),new ArrayList<Articles>()));
        progressBar.setVisibility(View.VISIBLE);

        NewsAPInterface apiService = NewsAPIClient.getClient().create(NewsAPInterface.class);

        Call<News> call = apiService.getNews(API_KEY);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News>call, Response<News> response) {
                List<Articles> movies = response.body().getArticles();
                Log.d(TAG, "Number of movies received: " + movies.size());
                adapter = new NewsAdapter(getContext(),movies);
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<News>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
