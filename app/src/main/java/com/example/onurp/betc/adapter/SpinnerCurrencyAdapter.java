package com.example.onurp.betc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.onurp.betc.R;
import com.example.onurp.betc.model.Currency;
import com.example.onurp.betc.model.coins.CoinInfo;

import java.util.ArrayList;

public class SpinnerCurrencyAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Currency> currencyName;


    public SpinnerCurrencyAdapter(Context context, ArrayList<Currency> currencyName) {
        super(context, R.layout.row_spinner_currency,currencyName);
        this.context = context;
        this.currencyName = currencyName;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder vh;

        if(view == null){
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflator.inflate(R.layout.row_spinner_currency, parent, false);
            vh = new ViewHolder();
            vh.txtName = (TextView) view.findViewById(R.id.txt_spinner_currency_name);
            vh.txtSymbol = (TextView) view.findViewById(R.id.txt_spinner_currency_symbol);



            vh.txtName.setText(currencyName.get(position).getCc());
        }


        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder vh;

        if(convertView == null){
            LayoutInflater inflator = (LayoutInflater)   context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.row_spinner_currency, parent, false);
            vh = new ViewHolder();
            vh.txtName = (TextView) convertView.findViewById(R.id.txt_spinner_currency_name);
            vh.txtSymbol = (TextView) convertView.findViewById(R.id.txt_spinner_currency_symbol);


            vh.txtName.setText(currencyName.get(position).getName());
            vh.txtSymbol.setText(currencyName.get(position).getCc());
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView txtSymbol;
        TextView txtName;
    }


    @Override
    public int getCount() {
        return currencyName.size();
    }

    @Override
    public Currency getItem(int position) {
        return currencyName.get(position);
    }
}
