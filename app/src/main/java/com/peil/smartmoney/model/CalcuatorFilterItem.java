package com.peil.smartmoney.model;

import android.text.TextUtils;

import com.peil.smartmoney.util.TimeUtil;

import java.io.Serializable;

public class CalcuatorFilterItem implements Serializable {


    private static final long serialVersionUID = -1021443809984777797L;

    private String mFirstDate;
    private String mEndDate;

    public CalcuatorFilterItem(String firstDate, String endDate) {
        this.mFirstDate = firstDate;
        this.mEndDate = endDate;
    }

    public CalcuatorFilterItem() {

    }

    public String getForamtFirstDate() {
        return getForamtDate(mFirstDate, "yyyy-MM");
    }


    public String getForamtDate(String date, String format) {
        if (!TextUtils.isEmpty(date)) {
            return TimeUtil.millis2Str(Long.valueOf(date), format);
        }
        return "";
    }


    public void setFirstDate(String date) {
        this.mFirstDate = setDate(date);
    }

    public void setFirstDate(long date) {
        this.mFirstDate = String.valueOf(date);
    }

    public void setEndDate(long date) {
        this.mEndDate = String.valueOf(date);
    }

    private String setDate(String date) {
        long temp = TimeUtil.str2Millis(date, "yyyy-MM");
        return String.valueOf(temp);
    }


    public String getFormatEndDate() {
        return getForamtDate(mEndDate, "yyyy-MM");
    }

    public void setEndDate(String date) {
        this.mEndDate = setDate(date);
    }

    public String getFirstDate() {
        return mFirstDate;
    }

    public String getEndDate() {
        return mEndDate;
    }
}
