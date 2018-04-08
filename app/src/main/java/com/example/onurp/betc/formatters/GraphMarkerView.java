package com.example.onurp.betc.formatters;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.TextView;

import com.example.onurp.betc.R;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GraphMarkerView extends MarkerView implements IMarker {

    private TextView tvContent;
    private DateFormat mDataFormat;
    private Date mDate;
    private String currencySymbol;

    public GraphMarkerView (Context context, int layoutResource,String currencySymbol) {
        super(context, layoutResource);
        tvContent = (TextView) findViewById(R.id.tvContent);
        this.mDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        this.mDate = new Date();
        this.currencySymbol = currencySymbol;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        long currentTimestamp = (int)e.getX();

        tvContent.setText(currencySymbol+" "+e.getY() + " at " + getTimedate(currentTimestamp));
        super.refreshContent(e,highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if(mOffset == null) {
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }

        return mOffset;
    }


    private String getTimedate(long timestamp){

        try{
            mDate.setTime(timestamp*1000);
            return mDataFormat.format(mDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }
}