package com.example.onurp.betc.formatters;


import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HourAxisValueFormatter implements IAxisValueFormatter {

    private long referenceTimestamp;
    private DateFormat mDataFormat;
    private Date mDate;
    private Integer type;
    public HourAxisValueFormatter(long referenceTimestamp,Integer type) {
        this.referenceTimestamp = referenceTimestamp;
        this.mDataFormat = new SimpleDateFormat("HH:mm");
        this.mDate = new Date();
        this.type = type;
    }


    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        String returnValue= null;
        long convertedTimestamp = (long) value;
        long originalTimestamp = convertedTimestamp;

        switch (type){
            case 1:
                returnValue = getHour(originalTimestamp);
                break;
            case 2:
                returnValue = getDay(originalTimestamp);
                break;
            case 3:
                returnValue = getDate(originalTimestamp);
                break;
            case 4:
                returnValue = getDate(originalTimestamp);
                break;
        }


        return returnValue;
    }

    private String getHour(long timestamp) {
        try {
            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();
            mDate.setTime(timestamp * 1000);
            mDataFormat.setTimeZone(tz);
            return mDataFormat.format(mDate);
        } catch (Exception ex) {
            return "xx";
        }
    }

    private String getDay(long timestamp){
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();
            mDate.setTime(timestamp * 1000);
            mDataFormat.setTimeZone(tz);
            DateFormat dayFormate=new SimpleDateFormat("EEEE");
            return dayFormate.format(mDate);
        } catch (Exception ex) {
            return "xx";
        }
    }

    private String getDate(long timestamp){
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();
            mDate.setTime(timestamp * 1000);
            mDataFormat.setTimeZone(tz);
            return dateFormat.format(mDate);
        } catch (Exception ex) {
            return "xx";
        }
    }
}
