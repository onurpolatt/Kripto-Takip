package com.example.onurp.betc.graphs;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.TextView;

import com.example.onurp.betc.R;
import com.example.onurp.betc.api.GraphDataAPIClient;
import com.example.onurp.betc.api.GraphDataAPInterface;
import com.example.onurp.betc.api.MarketAPInterface;
import com.example.onurp.betc.api.MarketsAPIClient;
import com.example.onurp.betc.formatters.GraphMarkerView;
import com.example.onurp.betc.formatters.HourAxisValueFormatter;
import com.example.onurp.betc.formatters.YAxisValueFormatter;
import com.example.onurp.betc.model.FavCoins;
import com.example.onurp.betc.model.exchanges.MarketCoinInfo;
import com.example.onurp.betc.model.graphs.Graphs;
import com.example.onurp.betc.model.markets.Markets;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.FormatFlagsConversionMismatchException;
import java.util.HashSet;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GraphActivity extends AppCompatActivity implements OnSeekBarChangeListener,
        OnChartValueSelectedListener,View.OnClickListener{

    public interface MyCallback {
        void onCallback(Boolean value);
    }

    private static Integer TYPE_MINUTE = 1;
    private static Integer TYPE_HOUR = 2;
    private static Integer TYPE_DAY = 3;

    private LinearLayout linearLayout;
    private String marketName;
    private String coinSymbol;
    private String currency;
    private String currencySymbol;
    private String coinImageUrl;
    private String coinFullName;
    private ArrayList<Long> xAxis;
    private ArrayList<Float> yValues;
    private ArrayList<String> date;
    private GraphDataAPInterface apiInterface;

    private static String dailyLimitOneDay = "144";
    private static String dailyAggreOneDay ="10";

    private static String dailyLimitOneHour = "12";
    private static String dailyAggreOneHour ="5";

    private static String dailyLimitOneWeek = "168";
    private static String dailyAggreOneWeek ="1";

    private static String dailyLimitOneMonth = "720";
    private static String dailyAggreOneMonth ="1";

    private static String dailyLimitOneYear = "365";
    private static String dailyAggreOneYear ="1";

    private SimpleDateFormat sdf;
    private SimpleDateFormat sdfOnlyHM;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private String concatCurrency;
    private  Boolean returnValue,isFavExist;

    @BindView(R.id.linechart)LineChart lineChart;
    @BindView(R.id.toolbar_graph)Toolbar toolbar;
    @BindView(R.id.progressBar_graphs)ProgressBar progressBar;
    @BindView(R.id.timepicker)LinearLayout linearLayoutTimePickers;
    @BindView(R.id.onehour)TextView onehour;
    @BindView(R.id.oneday)TextView oneday;
    @BindView(R.id.oneweek)TextView oneweek;
    @BindView(R.id.onemonth)TextView onemonth;
    @BindView(R.id.oneyear)TextView oneyear;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        linearLayout = (LinearLayout)toolbar.findViewById(R.id.spinnerCurrency);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        marketName = getIntent().getExtras().getString("marketName");
        coinSymbol = getIntent().getExtras().getString("coinSymbol");
        currency = getIntent().getExtras().getString("currency");
        coinFullName = getIntent().getExtras().getString("coinFullName");
        currencySymbol = getIntent().getExtras().getString("currencySymbol");
        coinImageUrl = getIntent().getExtras().getString("coinImageUrl");

        concatCurrency = coinSymbol+"-"+currency+"-"+marketName;
        initView();
    }

    public void initView(){
        lineChart.setOnChartValueSelectedListener(this);
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragDecelerationFrictionCoef(0.9f);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);
        lineChart.setHighlightPerDragEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setBackgroundColor(Color.WHITE);

        linearLayoutTimePickers.setOnClickListener(this);
        oneday.setBackgroundResource(R.drawable.custom_time_period_picker);
        onemonth.setOnClickListener(this);
        oneyear.setOnClickListener(this);
        oneday.setOnClickListener(this);
        onehour.setOnClickListener(this);
        oneweek.setOnClickListener(this);

        loadExchangeJSON(coinSymbol,dailyLimitOneDay,dailyAggreOneDay,1);
        linearLayout.setVisibility(View.GONE);
        getSupportActionBar().setTitle(marketName+"("+coinSymbol+"-"+currency+")");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_graph_item, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {

        isCoinFav(new MyCallback() {
            @Override
            public void onCallback(Boolean value) {
                if(value){
                    Log.d("TAG","FİREBASE -FAVORİTE");
                    menu.findItem(R.id.favorite).setVisible(true);
                    menu.findItem(R.id.remove_favorite).setVisible(false);
                }
                else{
                    Log.d("TAG","FİREBASE -NO FAVORİTE");
                    menu.findItem(R.id.favorite).setVisible(false);
                    menu.findItem(R.id.remove_favorite).setVisible(true);
                }
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.favorite:
                removeFromFirebase();
                invalidateOptionsMenu();
                return true;
            case R.id.remove_favorite:
                addToFirebase();
                invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void isCoinFav(final MyCallback callback){
        returnValue = null;

        Query queryRef = databaseReference.child("favorites").child(user.getUid()).orderByChild("concatInfo").equalTo(concatCurrency);
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    returnValue = true;
                    callback.onCallback(returnValue);
                }
                else{
                    returnValue = false;
                    callback.onCallback(returnValue);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }


    public void addToFirebase(){
        FavCoins mCoinInfo = new FavCoins(coinSymbol,currency,marketName,concatCurrency,coinFullName,coinImageUrl);

       databaseReference.child("favorites").child(user.getUid()).push().setValue(mCoinInfo);
    }

    public void removeFromFirebase(){

        databaseReference.child("favorites").child(user.getUid()).orderByChild("concatInfo").equalTo(concatCurrency).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child: dataSnapshot.getChildren()) {
                                child.getRef().setValue(null);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.onehour:
                clearChartAndLists();
                loadExchangeJSON(coinSymbol,dailyLimitOneHour,dailyAggreOneHour,1);
                onehour.setBackgroundResource(R.drawable.custom_time_period_picker);
                break;
            case R.id.oneday:
                clearChartAndLists();
                loadExchangeJSON(coinSymbol,dailyLimitOneDay,dailyAggreOneDay,1);
                oneday.setBackgroundResource(R.drawable.custom_time_period_picker);
                break;
            case R.id.oneweek:
                clearChartAndLists();
                loadExchangeJSON(coinSymbol,dailyLimitOneWeek,dailyAggreOneWeek,2);
                oneweek.setBackgroundResource(R.drawable.custom_time_period_picker);
                break;
            case R.id.onemonth:
                clearChartAndLists();
                loadExchangeJSON(coinSymbol,dailyLimitOneMonth,dailyAggreOneMonth,3);
                onemonth.setBackgroundResource(R.drawable.custom_time_period_picker);
                break;
            case R.id.oneyear:
                clearChartAndLists();
                loadExchangeJSON(coinSymbol,dailyLimitOneMonth,dailyAggreOneMonth,4);
                oneyear.setBackgroundResource(R.drawable.custom_time_period_picker);
                break;
        }

        for (int i = 0; i < linearLayoutTimePickers.getChildCount(); i++) {
            if(linearLayoutTimePickers.getChildAt(i).getId() != view.getId()){
                if((linearLayoutTimePickers.getChildAt(i).getId() != R.id.view_first) && (linearLayoutTimePickers.getChildAt(i).getId() != R.id.view_last)){
                    linearLayoutTimePickers.getChildAt(i).setBackgroundResource(0);
                }
            }
        }
    }

    public void clearChartAndLists(){
        date.clear();
        xAxis.clear();
        yValues.clear();
        lineChart.invalidate();
        lineChart.clear();
    }

    public void loadExchangeJSON(String cSymbol,String limit,String aggre,Integer type){
        progressBar.setVisibility(View.VISIBLE);
        apiInterface = GraphDataAPIClient.getClient().create(GraphDataAPInterface.class);
        Call<Graphs> call = null;
        final Integer typeInner = type;
        switch (type){
            case 1:
                call  = apiInterface.getAllGraphDataByMinute(cSymbol,currency,limit,aggre,marketName);
                break;
            case 2:
                call  = apiInterface.getAllGraphDataByHour(cSymbol,currency,limit,aggre,marketName);
                break;
            case 3:
                call  = apiInterface.getAllGraphDataByHour(cSymbol,currency,limit,aggre,marketName);
                break;
            case 4:
                call  = apiInterface.getAllGraphDataByDay(cSymbol,currency,limit,aggre,marketName);
                break;
        }

        call.enqueue(new Callback<Graphs>() {
            @Override
            public void onResponse(Call<Graphs> call, Response<Graphs> response) {
                Graphs graphs = response.body();
                yValues = new ArrayList<Float>();
                xAxis = new ArrayList<Long>();
                date = new ArrayList<String>();
                sdfOnlyHM = new SimpleDateFormat("HH:mm");

                for(int i = 0;i<graphs.getData().length;i++){
                    date.add(getDate(Integer.parseInt(graphs.getData()[i].getTime())));
                    xAxis.add(Long.parseLong(graphs.getData()[i].getTime()));
                    yValues.add(Float.parseFloat(graphs.getData()[i].getClose()));
                }

                prepareChart(Collections.max(yValues),Collections.min(yValues),Collections.max(xAxis),Collections.min(xAxis),typeInner);
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<Graphs> call, Throwable t) {

            }
        });
    }
    public void prepareChart(float max,float min,long maxTime,long minTime,Integer typeInner){
        setData(yValues.size(),yValues);
        lineChart.animateX(2500);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setLabelCount(6,true);
        xAxis.setValueFormatter(new HourAxisValueFormatter(minTime,typeInner));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setXOffset(10f);
        xAxis.setLabelRotationAngle(45);
        xAxis.setAvoidFirstLastClipping(true);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setAxisMaximum(max+0.00001f);
        leftAxis.setAxisMinimum(min-0.00001f);
        leftAxis.setLabelCount(6,true);
        leftAxis.setValueFormatter(new YAxisValueFormatter(currencySymbol));
        leftAxis.setDrawLimitLinesBehindData(true);
        leftAxis.removeAllLimitLines();
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);

        lineChart.getLegend().setEnabled(false);

    }
    private void setData(int count, ArrayList<Float> dataList) {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        GraphMarkerView mv = new GraphMarkerView(getApplicationContext(), R.layout.custom_marker_view,currencySymbol);
        for (int i = 0; i < count; i++) {
            yVals1.add(new Entry(xAxis.get(i).floatValue(), dataList.get(i)));
        }

        LineDataSet set1;
        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(yVals1, "DataSet 1");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.WHITE);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setDrawValues(false);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);

            LineData data = new LineData(set1);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            lineChart.setHighlightPerTapEnabled(true);
            mv.setChartView(lineChart);
            lineChart.setMarker(mv);
            lineChart.setMarker(mv);
            lineChart.setExtraOffsets(10, 50, 20, 50);
            lineChart.setData(data);
        }
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();

        sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        sdf.setTimeZone(tz);
        String localTime = sdf.format(new Date(time * 1000));
        return localTime;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
