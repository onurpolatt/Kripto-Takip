package com.example.onurp.betc.fragments;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.onurp.betc.ItemDivider;
import com.example.onurp.betc.R;
import com.example.onurp.betc.adapter.RecycleAdapter;
import com.example.onurp.betc.api.APIClient;
import com.example.onurp.betc.api.APInterface;
import com.example.onurp.betc.dialogs.SpinnerDialog;
import com.example.onurp.betc.model.Coins;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by onurp on 9.03.2018.
 */

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener{
    private static RecycleAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private boolean checkSortName = false;
    private boolean checkSortPrice = false;
    private ArrayList<Coins> coins;
    private List<Coins> filteredModelList;
    private double deger = 9226.82;
    private SearchView searchView;
    APInterface apiInterface;
    RecyclerView recyclerView;
    private Toolbar toolbar;
    public static final int DIALOG_REQUEST_CODE = 1;
    private String currency = "USD";
    private TextView textView;
    @BindView(R.id.progressBar)ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        getActivity().setTitle("Home");
        setHasOptionsMenu(true);

        initView(view);
        loadJSON(currency);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void initView(View view){
        final AppCompatActivity act = (AppCompatActivity) getActivity();
        if (act.getSupportActionBar() != null) {
            toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            textView = (TextView)toolbar.findViewById(R.id.txt_currency);
            ImageView ımageView = (ImageView)toolbar.findViewById(R.id.img_down_arrows);
            ımageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SpinnerDialog dialog = SpinnerDialog.newInstance();
                    dialog.setTargetFragment(HomeFragment.this, DIALOG_REQUEST_CODE);
                    dialog.show(getActivity().getSupportFragmentManager(), "dialog");
                }
            });
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
    }


    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_item, menu);

        MenuItem item = menu.findItem(R.id.search);
        searchView =  (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                adapter.setFilter(coins);
                menu.findItem(R.id.sort).setVisible(false);
                menu.findItem(R.id.refresh).setVisible(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                menu.findItem(R.id.sort).setVisible(true);
                menu.findItem(R.id.refresh).setVisible(true);
                getActivity().invalidateOptionsMenu();
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh:
                searchView.setIconified(true);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.VISIBLE);
                coins.clear();
                loadJSON(currency);
                return false;
            case R.id.sort_name:
                sortCoinsByName(checkSortName);
                return false;
            case R.id.sort_price:
                sortCoinsByPrice(checkSortPrice);
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filteredModelList = filter(coins, newText);
        adapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Coins> filter(List<Coins> models, String query) {
        query = query.toLowerCase();
        final List<Coins> filteredModelList = new ArrayList<>();

        for (Coins coins : models) {
            final String textName = coins.getName().toLowerCase();
            final String textSymbol = coins.getSymbol().toLowerCase();
            if (textName.contains(query) || textSymbol.contains(query)) {
                filteredModelList.add(coins);
            }
        }
        return filteredModelList;
    }

    public void loadJSON(final String currency){

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.addItemDecoration(new ItemDivider(getActivity()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RecycleAdapter(getContext(),new ArrayList<Coins>(),currency));
        progressBar.setVisibility(View.VISIBLE);

        apiInterface = APIClient.getClient().create(APInterface.class);
        Call<List<Coins>> call = apiInterface.getCurrencyCoins(currency);
        call.enqueue(new Callback<List<Coins>>() {
            @Override
            public void onResponse(Call<List<Coins>> call, Response<List<Coins>> response) {
                coins = new ArrayList<>(response.body());
                adapter = new RecycleAdapter(getContext(),coins,currency);
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Coins>> call, Throwable t) {

            }
        });
    }

    private void sortCoinsByName(boolean check) {
        check = !check;

        if (check) {
            Collections.sort(coins);
            checkSortName = check;
        }
        else {
            Collections.reverse(coins);
            checkSortName = check;
        }

        adapter.notifyDataSetChanged();
    }

    private void sortCoinsByPrice(boolean check){
        check = !check;

        if (check) {
            Collections.sort(coins,Coins.price_comparator_asc);
            checkSortName = check;
        }
        else {
            Collections.sort(coins,Coins.price_comparator_desc);
            checkSortName = check;
        }

        adapter.notifyDataSetChanged();
    }
    public String getSymbol(String data){
       String[] parts = data.split("\\(");
       parts[1] = parts[1].replace(")", "");
       return parts[1];
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DIALOG_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                if (data.getExtras().containsKey("currency")) {

                    String resultCurrency = data.getExtras().getString("currency");
                    Log.d("RESULT","RESULT DATA:"+resultCurrency);

                    if(currency == resultCurrency ){
                        //bir şey yapma
                    }
                    else{
                        currency = getSymbol(resultCurrency);
                        textView.setText(currency);
                        loadJSON(currency);//UI guncelle
                    }
                }
            }
        }
    }

}
