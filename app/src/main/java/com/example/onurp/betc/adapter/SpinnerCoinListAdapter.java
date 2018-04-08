package com.example.onurp.betc.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.onurp.betc.R;
import com.example.onurp.betc.model.coins.CoinInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SpinnerCoinListAdapter extends ArrayAdapter {
    private static String BASE_URL = "https://www.cryptocompare.com";
    private Context context;
    private ArrayList<String> contactlist;
    private ArrayList<CoinInfo> contactlistInfo;

    public SpinnerCoinListAdapter(Context context, ArrayList<String> contactlistName,int resource,ArrayList<CoinInfo> contactlistInfo) {
        super(context, resource, contactlistInfo);
        this.context = context;
        this.contactlist= contactlistName;
        this.contactlistInfo = contactlistInfo;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder vh;

        if(view == null){
            LayoutInflater inflator = (LayoutInflater)   context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflator.inflate(R.layout.row_spinner_coinlist, parent, false);
            vh = new ViewHolder();
            vh.imgView = (ImageView) view.findViewById(R.id.img_coin_list);
            vh.txtView = (TextView) view.findViewById(R.id.txt_spinner_coin);

            String name = contactlist.get(position);
            if(contactlistInfo.get(position).getFullName() != null) {
                Glide.with(context).load(BASE_URL +contactlistInfo.get(position).getImageUrl()).into(vh.imgView);
            }
            else {
                Glide.with(context).load(R.drawable.ic_crypto).into(vh.imgView);
            }

            vh.txtView.setText(name);
        }


        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getDropDownView(position, convertView, parent);
    }

    private static class ViewHolder {
        ImageView imgView;
        TextView txtView;
    }

    @Override
    public int getCount() {
        return contactlistInfo.size();
    }

    @Override
    public CoinInfo getItem(int position) {
        return contactlistInfo.get(position);
    }

}