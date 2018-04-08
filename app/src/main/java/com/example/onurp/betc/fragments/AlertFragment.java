package com.example.onurp.betc.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.onurp.betc.AddAlertActivity;
import com.example.onurp.betc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlertFragment extends Fragment implements View.OnClickListener {
    private static int REQUEST_CODE = 12;
    private static String toolbarTitle = "Alerts";
    private Toolbar toolbar;
    private LinearLayout linearLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert, container, false);
        toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        linearLayout = (LinearLayout)toolbar.findViewById(R.id.spinnerCurrency);

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab_add);
        fab.setOnClickListener(this);
        linearLayout.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(toolbarTitle);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), AddAlertActivity.class);
        intent.putExtra("add_alert",true);
        startActivityForResult(intent,REQUEST_CODE);
    }
}
