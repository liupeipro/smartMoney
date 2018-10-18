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

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.peil.smartmoney.R;
import com.peil.smartmoney.adapter.CoastAddTypeAdapter;
import com.peil.smartmoney.base.MoneyApplication;
import com.peil.smartmoney.greendao.gen.CostItemTypeDao;
import com.peil.smartmoney.model.CostItem;
import com.peil.smartmoney.model.CostItemAccount;
import com.peil.smartmoney.model.CostItemAmountType;
import com.peil.smartmoney.model.CostItemType;
import com.peil.smartmoney.util.GsonUtils;
import com.peil.smartmoney.util.TimeUtil;
import com.peil.smartmoney.view.BottomBar;
import com.peil.smartmoney.view.BottomTextBarTab;
import com.peil.smartmoney.view.InputAmountView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.greenrobot.greendao.Property;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;

public class CostAddActivity extends Activity {

    //
    List<CostItemType> mGridData;
    List<CostItemAccount> mAccountListData;

    QMUITopBarLayout bar_top;
    TextView tv_remark;
    InputAmountView view_amount;
    DatePicker mPickerDate;
    private BottomBar mBottomBar;
    GridView grid_type;
    QMUIRoundButton btn_choose_date, btn_choose_account;

    private List<CostItemAmountType> mAmountTypes;

    private List<CostItemType> mTempGridData;
    private CoastAddTypeAdapter mGridAdapter;

    private CostItem mCostItem;

    private String chooseDate = "", remark = "", account = "", costType = "", costAmountType = "";


    private BottomBar.OnTabSelectedListener mOnTabSelectedListener = new BottomBar.OnTabSelectedListener() {
        @Override
        public void onTabSelected(int position, int prePosition) {
            updateAmountType(prePosition);
        }

        @Override
        public void onTabUnselected(int position) {

        }

        @Override
        public void onTabReselected(int position) {

        }
    };

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
                    showChooseAccountView();
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

    private InputAmountView.OnAmountClickListener mOnAmountClickListener = new InputAmountView.OnAmountClickListener() {

        @Override
        public void onAmountDone(float amount) {
            mCostItem.setCostAmount(String.valueOf(amount));
            onSubmit(mCostItem);
        }
    };

    private void onSubmit(CostItem item) {

        //检查
//        1- 金额
        if (item.getCostAmount().isEmpty()) {
            ToastUtils.showShort("请输入金额");
            return;
        }

//        2- 类别
        if (item.getCostType() == null) {
            ToastUtils.showShort("请选择类别");
            return;
        }

//        3- 账户
        if (item.getCostAccount() == null) {
            ToastUtils.showShort("请选择账户");
            return;
        }


//        4- 日期
        if (item.getCostDate() == null) {
            ToastUtils.showShort("请选择日期");
            return;
        }


        mCostItem.setCreateTime(Long.valueOf(System.currentTimeMillis()));
        mCostItem.setLastModifyTime(Long.valueOf(System.currentTimeMillis()));

        long temp = MoneyApplication.getDaoInstant().getCostItemDao().insert(mCostItem);
        LogUtils.d("提交数据库：现在记账数据 "+temp);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_add);

        mAmountTypes = new ArrayList<>();
        mGridData = new ArrayList<CostItemType>();
        mAccountListData = new ArrayList<CostItemAccount>();
        mTempGridData = new ArrayList<CostItemType>();
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
        mBottomBar.setOnTabSelectedListener(mOnTabSelectedListener);

        view_amount = findViewById(R.id.view_amount);
        view_amount.setOnAmountClickListener(mOnAmountClickListener);
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


        mCostItem = new CostItem();
        mAmountTypes = MoneyApplication.getDaoInstant().getCostItemAmountTypeDao().loadAll();
        if (!mAmountTypes.isEmpty()) {
            for (CostItemAmountType item : mAmountTypes) {
                mBottomBar
                        .addItem(new BottomTextBarTab(this, item.getName()));
            }
        }
        updateAmountType(0);

        mAccountListData = MoneyApplication.getDaoInstant().getCostItemAccountDao().loadAll();

        grid_type.setAdapter(mGridAdapter);
        grid_type.setOnItemClickListener(mGridItemClickListener);
        mGridAdapter.addAll(mGridData);
        mPickerDate = new DatePicker(this);

        //设置默认选择项
        mGridAdapter.setSelectedState(0);
        mGridAdapter.notifyDataSetChanged();
        updateChooseDate(TimeUtil.millis2Str(System.currentTimeMillis(), "yyyy-MM-dd"));
        updateChooseAccount(0);

    }

    private void updateAmountType(int position) {
        CostItemAmountType item = mAmountTypes.get(position);
        mCostItem.setCostAmountType(item);

        mGridData = MoneyApplication.getDaoInstant().getCostItemTypeDao().queryBuilder().where(CostItemTypeDao.Properties.AmountTypeId.eq(mCostItem.getCostAmountTypeId())).list();
    }

    private void updateChooseDate(String date) {
//        更新日期
        if (!btn_choose_date.getText().equals(date)) {
            mCostItem.setTempCostDate(date);
            btn_choose_date.setText(mCostItem.getTempCostDate());
        }
    }

    /**
     * 更新账户信息
     *
     * @param position
     */
    private void updateChooseAccount(int position) {
        CostItemAccount item = mAccountListData.get(position);
        mCostItem.setCostAccount(item);
        btn_choose_account.setText(mCostItem.getCostAccount().getCostAccountame());
        Toast.makeText(CostAddActivity.this, "Item " + (position) + GsonUtils.toJson(item), Toast.LENGTH_SHORT).show();
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

    private void showChooseAccountView() {
        QMUIBottomSheet.BottomListSheetBuilder builder = new QMUIBottomSheet.BottomListSheetBuilder(this, true);

        for (CostItemAccount item : mAccountListData) {
            builder.addItem(item.getCostAccountame());
        }

        builder.setOnSheetItemClickListener(mAccountItemClickListener);
        builder.build().show();
    }

    private QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener mAccountItemClickListener = new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
        @Override
        public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
            dialog.dismiss();
            updateChooseAccount(position);
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
