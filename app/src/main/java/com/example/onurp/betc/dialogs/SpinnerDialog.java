package com.example.onurp.betc.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;


import android.support.v4.app.Fragment;
import android.view.ViewGroup;

import com.example.onurp.betc.ItemDivider;
import com.example.onurp.betc.MarketDetailActivity;
import com.example.onurp.betc.R;
import com.example.onurp.betc.adapter.SpinnerAdapter;
import com.example.onurp.betc.listener.GetStringCurrencyBack;
import com.example.onurp.betc.listener.RecyclerItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by onurp on 21.03.2018.
 */

public class SpinnerDialog extends DialogFragment {
    private RecyclerView mRecyclerView;
    private SpinnerAdapter spinnerAdapter;
    private boolean isModal = false;
    private  ArrayList<String> mCurrencyList;
    private GetStringCurrencyBack mListener;

    public static SpinnerDialog newInstance(ArrayList<String> currencyList)
    {

        SpinnerDialog fragment = new SpinnerDialog();
        Bundle args = new Bundle();
        args.putStringArrayList("currency", currencyList);
        fragment.isModal = true;
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        mCurrencyList = getArguments().getStringArrayList("currency");
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.row_spinner,null);
        mRecyclerView = new RecyclerView(getActivity());
        spinnerAdapter = new SpinnerAdapter(getActivity(),mCurrencyList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(spinnerAdapter);


        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("CLICK", "POSITION: " + mCurrencyList.get(position));
                if(getTargetFragment() != null){
                    sendBackResult(mCurrencyList.get(position));
                }
                else{
                    mListener.onComplete(mCurrencyList.get(position));
                    dismiss();
                }

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setTitle("Select Currency")
                .setView(mRecyclerView)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog)
    {
        super.onCancel(dialog);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        dialog.cancel();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (GetStringCurrencyBack) activity;
        }
        catch (final ClassCastException e) {
            //throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }



    public void sendBackResult(String result) {
        Fragment targetFragment = getTargetFragment();
        if (targetFragment != null) {

            Bundle bundle = new Bundle();
            bundle.putString("currency",result);

            Intent intent = new Intent().putExtras(bundle);

            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

            dismiss();
        } else {


        }
    }
}