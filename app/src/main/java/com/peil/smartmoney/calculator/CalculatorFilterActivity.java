package com.peil.smartmoney.calculator;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.peil.smartmoney.R;
import com.peil.smartmoney.base.BaseActivity;
import com.peil.smartmoney.model.CalcuatorFilterItem;
import com.peil.smartmoney.util.MoneyConstants;
import com.peil.smartmoney.util.TimeUtil;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import java.util.Calendar;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.util.ConvertUtils;

public class CalculatorFilterActivity extends BaseActivity {

    private QMUITopBarLayout bar_top;
    private QMUICommonListItemView item_choose_date_start, item_choose_date_end;
    private DatePicker mPickerDate;
    private CalcuatorFilterItem mResultObject;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_calculator_filter;
    }

    @Override
    protected void initView() {
        bar_top = findViewById(R.id.bar_top);
        item_choose_date_start = findViewById(R.id.item_choose_date_start);
        item_choose_date_end = findViewById(R.id.item_choose_date_end);
        mPickerDate = new DatePicker(this, DateTimePicker.YEAR_MONTH);

        mPickerDate.setCanceledOnTouchOutside(true);
        mPickerDate.setUseWeight(true);
        mPickerDate.setTopPadding(ConvertUtils.toPx(this, 10.0F));
        mPickerDate.setRangeEnd(2111, 1, 11);
        mPickerDate.setRangeStart(2016, 1, 1);
        mPickerDate.setResetWhileWheel(false);


    }

    @Override
    protected void initData() {

        mResultObject = (CalcuatorFilterItem) getIntent().getSerializableExtra(MoneyConstants.INTENT_CALCULATOR_FILTER_ITEM);
        if (mResultObject == null) {
            mResultObject = new CalcuatorFilterItem();
        }
    }

    @Override
    protected void initController() {
        bar_top.setTitle("筛选");
        bar_top.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
        bar_top.addRightTextButton("完成", R.id.topbar_right_change_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra(MoneyConstants.INTENT_CALCULATOR_FILTER_ITEM, mResultObject);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                });

        item_choose_date_start.setText("起始日期");
        item_choose_date_end.setText("截止日期");

        item_choose_date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseTimeView("1");
            }
        });
        item_choose_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseTimeView("2");

            }
        });

        updateDetailText();
    }

    private void updateDetailText() {
        item_choose_date_start.setDetailText(mResultObject.getForamtFirstDate());
        item_choose_date_end.setDetailText(mResultObject.getFormatEndDate());

    }


    private void showChooseTimeView(final String type) {
        int tempYear = 0, tempMonth = 0;
        if (!TextUtils.isEmpty(mResultObject.getForamtFirstDate()) || !TextUtils.isEmpty(mResultObject.getFormatEndDate())) {
            if (type.equals("1")) {
                String ty = mResultObject.getForamtDate(mResultObject.getFirstDate(), "yyyy");
                if (!TextUtils.isEmpty(ty)) {
                    tempYear = Integer.valueOf(ty);
                }

                String tm = mResultObject.getForamtDate(mResultObject.getFirstDate(), "MM");
                if (!TextUtils.isEmpty(tm)) {
                    tempMonth = Integer.valueOf(tm);
                }


            } else {
                String ty = mResultObject.getForamtDate(mResultObject.getEndDate(), "yyyy");
                if (!TextUtils.isEmpty(ty)) {
                    tempYear = Integer.valueOf(ty);
                }

                String tm = mResultObject.getForamtDate(mResultObject.getEndDate(), "MM");
                if (!TextUtils.isEmpty(tm)) {
                    tempMonth = Integer.valueOf(tm);
                }

            }
        }

        if (tempYear == 0 && tempMonth == 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            tempYear = calendar.get(Calendar.YEAR);
            tempMonth = calendar.get(Calendar.MONTH) + 1;
        }

        mPickerDate.setSelectedItem(tempYear, tempMonth);
        mPickerDate.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
            @Override
            public void onDatePicked(String year, String month) {
                updateChooseDate(type, year + "-" + month);
            }
        });
        mPickerDate.show();
    }

    private void updateChooseDate(String type, String date) {
        //起始时间一定要早 截止时间
        if (checkChooseDate(type, date)) {
            if (type.equals("1")) {
                mResultObject.setFirstDate(date);
            } else if (type.equals("2")) {
                mResultObject.setEndDate(date);
            }

            updateDetailText();
        } else {
            ToastUtils.showShort("输入有误，请重新输入");
//            ToastUtils.showShort("截止日期要大于起始日期");
        }
    }

    private boolean checkChooseDate(String type, String date) {
        //起始时间一定要早 截止时间
        long tempStart = 0;
        long tempEnd = 0;
        if (type.equals("1")) {
            tempStart = TimeUtil.str2Millis(date, "yyyy-MM");
            if (!TextUtils.isEmpty(mResultObject.getEndDate())) {
                tempEnd = Long.valueOf(mResultObject.getEndDate());
            }
        } else if (type.equals("2")) {
            if (!TextUtils.isEmpty(mResultObject.getFirstDate())) {
                tempStart = Long.valueOf(mResultObject.getFirstDate());
            }
            tempEnd = TimeUtil.str2Millis(date, "yyyy-MM");
        }

        if (tempStart > 0 && tempEnd > 0) {
            if (tempEnd <= tempStart) {
                return false;
            }

            long tempNow = TimeUtils.getNowMills();
            if (tempStart > tempNow || tempEnd > tempNow) {
                return false;
            }
        }
        return true;
    }

}


