package com.example.onurp.betc.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.onurp.betc.ItemDivider;
import com.example.onurp.betc.MarketDetailActivity;
import com.example.onurp.betc.R;
import com.example.onurp.betc.adapter.FavoriteAdapter;
import com.example.onurp.betc.adapter.MarketCoinAdapter;
import com.example.onurp.betc.api.MarketCoinAPIClient;
import com.example.onurp.betc.api.MarketCoinAPInterface;
import com.example.onurp.betc.graphs.GraphActivity;
import com.example.onurp.betc.interfaces.MarketsOnItemClickListener;
import com.example.onurp.betc.model.FavCoins;
import com.example.onurp.betc.model.exchanges.Exchanges;
import com.example.onurp.betc.model.exchanges.MarketCoinInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class FavoriteFragment extends Fragment {
    private static int STATUS_CODE = 12;
    private ArrayList<FavCoins> favCoinList;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private ValueEventListener mListener;
    private MarketCoinAPInterface apiCoinInterface;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private int i=0;
    private ArrayList<MarketCoinInfo> coins;
    @BindView(R.id.progressBar_favorites)ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this,view);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = view.findViewById(R.id.my_recycler_view_favorites);
        coins = new ArrayList<MarketCoinInfo>();

        retrieveFavData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Favorites");
    }

    public void retrieveFavData(){
        favCoinList = new ArrayList<FavCoins>();
       mListener = databaseReference.child("favorites").child(user.getUid()).orderByChild("fromSymbol").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    FavCoins favCoins = child.getValue(FavCoins.class);
                    favCoinList.add(favCoins);
                }

                layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.addItemDecoration(new ItemDivider(getActivity()));
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(new FavoriteAdapter(getContext(), new ArrayList<MarketCoinInfo>(), new ArrayList<FavCoins>()));

                loadCoinData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadCoinData(){

        if(favCoinList.size() > i){
            apiCoinInterface = MarketCoinAPIClient.getClient().create(MarketCoinAPInterface.class);
            Call<Exchanges> call = apiCoinInterface.getCoinInfo(favCoinList.get(i).getFromSymbol(), favCoinList.get(i).getToSymbol(), favCoinList.get(i).getMarketName());
            call.enqueue(new Callback<Exchanges>() {
                @Override
                public void onResponse(Call<Exchanges> call, Response<Exchanges> response) {
                    Exchanges markets = response.body();
                    Log.d("TAG","TIKLANAN COIN:"+ markets.getResults());
                    coins.add(markets.getResults().get("DISPLAY").get(favCoinList.get(i).getFromSymbol()).get(favCoinList.get(i).getToSymbol()));
                    Log.d("TAG","TIKLANAN COIN:"+coins.get(i).getCOINNAME());
                    i++;
                    loadCoinData();
                }

                @Override
                public void onFailure(Call<Exchanges> call, Throwable t) {

                }
            });
        }
        else {
            progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(new FavoriteAdapter(getContext(), coins, favCoinList, new MarketsOnItemClickListener() {
                    @Override
                    public void onMarketClick(View v, int position) {
                        Log.d("TAG","TIKLANAN COIN:"+ coins.get(position).getCOINNAME());
                        Intent intent = new Intent(getActivity(), GraphActivity.class);
                        intent.putExtra("currency",favCoinList.get(position).getToSymbol());
                        intent.putExtra("coinSymbol",coins.get(position).getCOINNAME());
                        intent.putExtra("marketName",favCoinList.get(position).getMarketName());
                        intent.putExtra("currencySymbol",coins.get(position).getTOSYMBOL());

                        if(favCoinList.get(position).getCoinImageUrl() != null){
                           // Log.d("TAG","COIN FULLNAME: "+ hashMap.get(coins.get(position).getCOINNAME()).getFullName());
                            intent.putExtra("coinFullName",favCoinList.get(position).getCoinFullName());
                            intent.putExtra("coinImageUrl",favCoinList.get(position).getCoinImageUrl());
                        }
                        else {
                            intent.putExtra("coinFullName",coins.get(position).getCOINNAME());
                            intent.putExtra("coinImageUrl","null");
                        }



                        startActivityForResult(intent,STATUS_CODE);
                    }
                }));
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == STATUS_CODE) {
            i=0;
            coins.clear();
            progressBar.setVisibility(View.VISIBLE);
            retrieveFavData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        databaseReference.child("favorites").child(user.getUid()).orderByChild("fromSymbol").removeEventListener(mListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        databaseReference.child("favorites").child(user.getUid()).orderByChild("fromSymbol").removeEventListener(mListener);
    }
}
