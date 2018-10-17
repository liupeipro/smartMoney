package com.peil.smartmoney.cost;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.peil.smartmoney.R;
import com.peil.smartmoney.adapter.CoastAddTypeAdapter;
import com.peil.smartmoney.model.CostAddItemAccount;
import com.peil.smartmoney.model.CostAddItemType;
import com.peil.smartmoney.view.BottomBar;
import com.peil.smartmoney.view.BottomTextBarTab;
import com.peil.smartmoney.view.InputAmountView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;

public class CostAddActivity extends Activity {

//
    List<CostAddItemType> mGridData;
    List<CostAddItemAccount> mAccountListData;

    QMUITopBarLayout bar_top;
    TextView tv_remark;
    InputAmountView view_amount;
    DatePicker mPickerDate;
    private BottomBar mBottomBar;
    GridView grid_type;
    QMUIRoundButton btn_choose_date, btn_choose_account;

    private List<CostAddItemType> mTempGridData;
    private CoastAddTypeAdapter mGridAdapter;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_choose_date:

//                显示选择时间的View
                    showChooseTimeView();
                    break;
                case R.id.btn_choose_account:
//                显示选择账户的View
                    showChooseTypeView();
                    break;
                case R.id.tv_remark:
//                    显示备注
                    showInputRemarkView();
                    break;


            }
        }
    };

    private AdapterView.OnItemClickListener mGridItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Toast.makeText(CostAddActivity.this, mGridData.get(position).getCostTypeName(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_add);

        mGridData = new ArrayList<CostAddItemType>();
        mAccountListData = new ArrayList<CostAddItemAccount>();
        mTempGridData = new ArrayList<CostAddItemType>();
        mGridAdapter = new CoastAddTypeAdapter(this);

        bar_top = findViewById(R.id.bar_top);
        bar_top.setTitle("添加记账");
        bar_top.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        mBottomBar
                .addItem(new BottomTextBarTab(this, getString(R.string.cost_add_item_in)))
                .addItem(new BottomTextBarTab(this, getString(R.string.cost_add_item_out)));

        view_amount = findViewById(R.id.view_amount);
        grid_type = findViewById(R.id.grid_type);
        btn_choose_date = findViewById(R.id.btn_choose_date);
        btn_choose_date.setText("选择日期");
        btn_choose_date.setOnClickListener(mOnClickListener);

        btn_choose_account = findViewById(R.id.btn_choose_account);
        btn_choose_account.setText("选择账户");
        btn_choose_account.setOnClickListener(mOnClickListener);

        tv_remark = findViewById(R.id.tv_remark);
        tv_remark.setText("备注");
        tv_remark.setOnClickListener(mOnClickListener);


        mGridData.add(new CostAddItemType("", "吃喝"));
        mGridData.add(new CostAddItemType("", "交通"));
        mGridData.add(new CostAddItemType("", "日用品"));
        mGridData.add(new CostAddItemType("", "红包"));
        mGridData.add(new CostAddItemType("", "买菜"));
        mGridData.add(new CostAddItemType("", "孩子"));
        mGridData.add(new CostAddItemType("", "网购"));
        mGridData.add(new CostAddItemType("", "话费"));
        mGridData.add(new CostAddItemType("", "医疗"));
        mGridData.add(new CostAddItemType("", "娱乐"));
        mGridData.add(new CostAddItemType("", "化妆护肤"));
        mGridData.add(new CostAddItemType("", "水电煤"));
        mGridData.add(new CostAddItemType("", "汽车"));
        mGridData.add(new CostAddItemType("", "数码"));
        mGridData.add(new CostAddItemType("", "其他"));
        mGridData.add(new CostAddItemType("", "+"));

        mAccountListData.add(new CostAddItemAccount("", "现金"));
        mAccountListData.add(new CostAddItemAccount("", "支付宝"));
        mAccountListData.add(new CostAddItemAccount("", "微信"));

        grid_type.setAdapter(mGridAdapter);
        grid_type.setOnItemClickListener(mGridItemClickListener);
        mGridAdapter.addAll(mGridData);
        mPickerDate = new DatePicker(this);
    }


    private void showInputAmountView() {

    }

    private void showChooseTimeView() {

        mPickerDate.setCanceledOnTouchOutside(true);
        mPickerDate.setUseWeight(true);
        mPickerDate.setTopPadding(ConvertUtils.toPx(this, 10));
        mPickerDate.setRangeEnd(2111, 1, 11);
        mPickerDate.setRangeStart(2016, 8, 29);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int tempYear = calendar.get(Calendar.YEAR);
        int tempMonth = calendar.get(Calendar.MONTH);
        int tempDay = calendar.get(Calendar.DAY_OF_MONTH);

        Log.i("cost add datepicker ", " year = " + tempYear + " month = " + tempMonth + "day = " + tempDay);

        mPickerDate.setSelectedItem(tempYear, tempMonth, tempDay);
        mPickerDate.setResetWhileWheel(false);
        mPickerDate.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                Toast.makeText(CostAddActivity.this, year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();

            }
        });
        mPickerDate.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                mPickerDate.setTitleText(year + "-" + mPickerDate.getSelectedMonth() + "-" + mPickerDate.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                mPickerDate.setTitleText(mPickerDate.getSelectedYear() + "-" + month + "-" + mPickerDate.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                mPickerDate.setTitleText(mPickerDate.getSelectedYear() + "-" + mPickerDate.getSelectedMonth() + "-" + day);
            }
        });
        mPickerDate.show();
    }

    private void showChooseTypeView() {
        QMUIBottomSheet.BottomListSheetBuilder builder = new QMUIBottomSheet.BottomListSheetBuilder(this, true);

        for (CostAddItemAccount item : mAccountListData) {
            builder.addItem(item.getCostAccountame());
        }

        builder.setOnSheetItemClickListener(mTypeItemClickListener);
        builder.build().show();
    }

    private QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener mTypeItemClickListener = new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
        @Override
        public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
            dialog.dismiss();
            Toast.makeText(CostAddActivity.this, "Item " + (position + 1), Toast.LENGTH_SHORT).show();
        }
    };

    private void showInputRemarkView() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(this);
        builder.setTitle("标题")
                .setPlaceholder("在此输入您的昵称")
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        if (text != null && text.length() > 0) {
                            Toast.makeText(CostAddActivity.this, "您的昵称: " + text, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(CostAddActivity.this, "请填入昵称", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .create(R.style.qmui_dialog_wrap).show();
    }
}
