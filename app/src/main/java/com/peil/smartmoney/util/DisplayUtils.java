package com.peil.smartmoney.util;

import android.content.Context;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

public class DisplayUtils {

    public static int dpToPx(int dpValue) {
        return QMUIDisplayHelper.dpToPx(dpValue);
    }

    public static int dpToPx(Context context, int dpValue) {
        return QMUIDisplayHelper.dp2px(context, dpValue);
    }

}
