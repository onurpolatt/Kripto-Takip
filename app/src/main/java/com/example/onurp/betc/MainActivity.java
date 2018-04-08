package com.example.onurp.betc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.onurp.betc.api.AllCoinAPIClient;
import com.example.onurp.betc.api.AllCoinAPInterface;
import com.example.onurp.betc.api.MarketAPInterface;
import com.example.onurp.betc.api.MarketsAPIClient;
import com.example.onurp.betc.auth.LoginActivity;
import com.example.onurp.betc.eventbus.ActivityToFragmentEvent;
import com.example.onurp.betc.eventbus.GlobalBus;
import com.example.onurp.betc.eventbus.SendExchangeDataEvent;
import com.example.onurp.betc.fragments.AlertFragment;
import com.example.onurp.betc.fragments.FavoriteFragment;
import com.example.onurp.betc.fragments.HomeFragment;
import com.example.onurp.betc.fragments.MarketFragment;
import com.example.onurp.betc.fragments.NewsFragment;
import com.example.onurp.betc.fragments.ProfileFragment;
import com.example.onurp.betc.model.User;
import com.example.onurp.betc.model.coins.CoinData;
import com.example.onurp.betc.model.markets.Markets;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.nView)NavigationView nView;
    @BindView(R.id.drawer)DrawerLayout dLayout;
    private AllCoinAPInterface apiInterface;
    private MarketAPInterface  marketAPInterface;
    private DatabaseReference mDatabase;
    private Drawer result = null;
    private String name,email;
    private Uri photoUrl;
    private boolean emailVerified;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Log.d("TAG","MAIN GIRILDL");
        loadCoinFullData();
        if (user != null) {

            name = user.getDisplayName();
            email = user.getEmail();
            photoUrl = user.getPhotoUrl();

            emailVerified = user.isEmailVerified();

            String uid = user.getUid();
            writeNewUser(uid,name,email);
        }

        Log.d("MARKET", "MAIN TIME");
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName(name).withEmail(email).withIcon(photoUrl)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .inflateMenu(R.menu.drawer_item)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Fragment fragment = null;
                        switch (position){
                            case 1: Toast.makeText(getApplicationContext(),"HOME",Toast.LENGTH_SHORT).show();
                                    fragment = new HomeFragment();
                                    break;
                            case 2: Toast.makeText(getApplicationContext(),"PROFILE",Toast.LENGTH_SHORT).show();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("Username", name);
                                    bundle.putString("Email", email);
                                    bundle.putString("PhotoUrl", photoUrl.toString());
                                    fragment = new ProfileFragment();
                                    fragment.setArguments(bundle);
                                    break;
                            case 3: Toast.makeText(getApplicationContext(),"NEWS",Toast.LENGTH_SHORT).show();
                                    fragment = new NewsFragment();
                                    break;
                            case 4: Toast.makeText(getApplicationContext(),"EXCHANGES",Toast.LENGTH_SHORT).show();
                                    fragment = new MarketFragment();
                                    break;
                            case 5: Toast.makeText(getApplicationContext(),"FAVORITES",Toast.LENGTH_SHORT).show();
                                    fragment = new FavoriteFragment();
                                    break;
                            case 6: Toast.makeText(getApplicationContext(),"ALERTS",Toast.LENGTH_SHORT).show();
                                    fragment = new AlertFragment();
                                    break;
                            case 7: Toast.makeText(getApplicationContext(),"SETTINGS",Toast.LENGTH_SHORT).show();
                                    break;
                            case 8: Toast.makeText(getApplicationContext(),"LOGOUT",Toast.LENGTH_SHORT).show();
                                    logout();
                                    break;

                        }

                        if (fragment != null) {
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.flContent, fragment);
                            ft.commit();
                        }
                        return false;
                    }
                }).build();

        SharedPreferences mPreferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("firstTime", true);
        editor.commit();

        result.setSelectionAtPosition(1,true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);
        mDatabase.child("users").child(userId).setValue(user);
    }

    private void logout(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }

    public void openDrawer() {
        result.openDrawer();
    }

    public void loadCoinFullData(){
        apiInterface = AllCoinAPIClient.getClient().create(AllCoinAPInterface.class);
        Call<CoinData> call = apiInterface.getAllCoins();
        call.enqueue(new Callback<CoinData>() {
            @Override
            public void onResponse(Call<CoinData> call, Response<CoinData> response) {
                CoinData coinList = response.body();
                GlobalBus.getBus().postSticky(new ActivityToFragmentEvent(coinList));
            }

            @Override
            public void onFailure(Call<CoinData> call, Throwable t) {

                Log.d("MARKET", "COINLIST:"+t);
            }});

        marketAPInterface = MarketsAPIClient.getClient().create(MarketAPInterface.class);
        Call<Markets> callExhange = marketAPInterface.getMarket();
        callExhange.enqueue(new Callback<Markets>() {
            @Override
            public void onResponse(Call<Markets> call, Response<Markets> response) {
                Markets markets = response.body();
                GlobalBus.getBus().postSticky(new SendExchangeDataEvent(markets));
            }

            @Override
            public void onFailure(Call<Markets> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
