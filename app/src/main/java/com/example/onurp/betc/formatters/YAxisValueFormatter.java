package com.example.onurp.betc.formatters;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class YAxisValueFormatter implements IAxisValueFormatter {
    String currencySymbol;

    public YAxisValueFormatter(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return currencySymbol+" "+value;
    }
}
