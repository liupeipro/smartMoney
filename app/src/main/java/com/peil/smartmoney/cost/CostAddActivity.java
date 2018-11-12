package com.peil.smartmoney.cost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.peil.smartmoney.R;
import com.peil.smartmoney.adapter.CostAddTypeAdapter;
import com.peil.smartmoney.base.BaseActivity;
import com.peil.smartmoney.base.MoneyApplication;
import com.peil.smartmoney.greendao.gen.CostItemTypeDao;
import com.peil.smartmoney.model.CostItem;
import com.peil.smartmoney.model.CostItemAccount;
import com.peil.smartmoney.model.CostItemAmountType;
import com.peil.smartmoney.model.CostItemType;
import com.peil.smartmoney.util.TimeUtil;
import com.peil.smartmoney.view.BottomBar;
import com.peil.smartmoney.view.BottomBar.OnTabSelectedListener;
import com.peil.smartmoney.view.BottomTextBarTab;
import com.peil.smartmoney.view.InputAmountView;
import com.peil.smartmoney.view.InputAmountView.OnAmountClickListener;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet.BottomListSheetBuilder;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DatePicker.OnWheelListener;
import cn.qqtheme.framework.picker.DatePicker.OnYearMonthDayPickListener;
import cn.qqtheme.framework.util.ConvertUtils;

/**
 * 添加记账页面
 */
public class CostAddActivity extends BaseActivity {
    //
    private List<CostItemType> mGridInData = new ArrayList();
    private List<CostItemType> mGridOutData = new ArrayList();
    private List<CostItemAccount> mAccountListData = new ArrayList();
    private List<CostItemAmountType> mAmountTypes = new ArrayList();
    private CostItem mCostItem = new CostItem();
    private QMUITopBarLayout bar_top;
    private InputAmountView view_amount;
    private DatePicker mPickerDate;
    private BottomBar mBottomBar;
    private GridView grid_type;
    private CostAddTypeAdapter mGridAdapter;
    private EditText et_remark;
    QMUICommonListItemView item_choose_date, item_choose_account;

    private OnTabSelectedListener mOnTabSelectedListener = new OnTabSelectedListener() {
        @Override
        public void onTabSelected(int position, int prePosition) {
            updateAmountType(position);
        }

        public void onTabUnselected(int position) {
        }

        public void onTabReselected(int position) {
        }
    };

    private OnClickListener mOnClickListener = new OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_choose_account:
                    showChooseAccountView();
                    break;
                case R.id.item_choose_date:
                    showChooseTimeView();
                    break;
                case R.id.topbar_right_change_button:
                    float temp = view_amount.getInputAmount();
                    if (temp > 0) {
                        mOnAmountClickListener.onAmountDone(temp);
                    } else {
                        mOnAmountClickListener.onAmountZero();
                    }
                    break;
            }
        }
    };

    AdapterView.OnItemClickListener mGridItemClickener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LogUtils.d("mGridItemClickener position = " + position);
            if (!mGridAdapter.hasSelected(position)) {
                mGridAdapter.resetSelectedPosition();
                mGridAdapter.setSelectedPosition(position);
                mGridAdapter.notifyDataSetChanged();
                updateAmountGrid();
            }
        }
    };

    private AdapterView.OnItemSelectedListener mGridItemSelectedListener =
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    LogUtils.d("mGridItemSelectedListener position = " + position);
                    if (!mGridAdapter.hasSelected(position)) {
                        mGridAdapter.resetSelectedPosition();
                        mGridAdapter.setSelectedPosition(position);
                        mGridAdapter.notifyDataSetChanged();
                        updateAmountGrid();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            };

    private OnAmountClickListener mOnAmountClickListener = new OnAmountClickListener() {
        public void onAmountZero() {
            ToastUtils.showShort("请输入金额");
        }

        public void onAmountDone(float amount) {
            mCostItem.setCostAmount(String.valueOf(amount));
            onSubmit(mCostItem);
        }
    };

    private OnSheetItemClickListener mAccountItemClickListener = new OnSheetItemClickListener() {
        public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
            dialog.dismiss();
            updateChooseAccount(position);
        }
    };


    private void doSubmit() {

    }

    private void onSubmit(CostItem item) {
        if (item.getCostAmount().isEmpty()) {
            ToastUtils.showShort("请输入金额");
        } else if (item.getCostType() == null) {
            ToastUtils.showShort("请选择类别");
        } else if (item.getCostAccount() == null) {
            ToastUtils.showShort("请选择账户");
        } else if (item.getCostDate() == null) {
            ToastUtils.showShort("请选择日期");
        } else {
            if (mCostItem.getCreateTime() == null) {
                mCostItem.setCreateTime(System.currentTimeMillis());
            }

            mCostItem.setLastModifyTime(System.currentTimeMillis());
            long temp =
                    MoneyApplication.getDaoInstant().getCostItemDao().insertOrReplace(mCostItem);
            LogUtils.d(new Object[]{"提交数据库：现在记账数据 " + temp});
            if (temp != -1L) {
                ToastUtils.showShort("记账成功");
                sendBroadcast(new Intent("receiver_costlist_update"));
                onBackPressed();
            }
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
    }

    private BottomBar getTitileCenterView() {
        int height = getResources().getDimensionPixelSize(R.dimen.height_32);
        BottomBar bottomBar = new BottomBar(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, height);
        bottomBar.setLayoutParams(params);
        bottomBar.setBackgroundResource(R.drawable.bg_add_cost_type);
        bottomBar.setOnTabSelectedListener(mOnTabSelectedListener);
        return bottomBar;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_add);

        mGridAdapter = new CostAddTypeAdapter(this);
        mPickerDate = new DatePicker(this);
        bar_top = (QMUITopBarLayout) findViewById(R.id.bar_top);
        view_amount = (InputAmountView) findViewById(R.id.view_amount);
        grid_type = (GridView) findViewById(R.id.grid_type);
        et_remark = (EditText) findViewById(R.id.et_remark);
        item_choose_date = (QMUICommonListItemView) findViewById(R.id.item_choose_date);
        item_choose_account = (QMUICommonListItemView) findViewById(R.id.item_choose_account);


        bar_top.addLeftBackImageButton().setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mBottomBar = getTitileCenterView();
        bar_top.setCenterView(mBottomBar);
        bar_top.addRightImageButton(R.mipmap.ic_done_white, R.id.topbar_right_change_button).setOnClickListener(mOnClickListener);

        mBottomBar.setOnTabSelectedListener(mOnTabSelectedListener);
        view_amount.setOnAmountClickListener(mOnAmountClickListener);
        item_choose_date.setOnClickListener(mOnClickListener);
        item_choose_date.setOnClickListener(mOnClickListener);

        item_choose_date.setText("日期");
        item_choose_account.setText("账户");


        mAmountTypes = MoneyApplication.getDaoInstant().getCostItemAmountTypeDao().loadAll();

        for (CostItemAmountType item : mAmountTypes) {
            List<CostItemType> temp = MoneyApplication.getDaoInstant()
                    .getCostItemTypeDao()
                    .queryBuilder()
                    .where(
                            CostItemTypeDao.Properties.AmountTypeId.eq(
                                    item.get_id()))
                    .list();
            if (item.getName().equals("收入")) {
                mGridInData.clear();
                mGridInData = temp;
            } else if (item.getName().equals("支出")) {
                mGridOutData.clear();
                mGridOutData = temp;
            }
        }

        mAccountListData = MoneyApplication.getDaoInstant().getCostItemAccountDao().loadAll();

        if (!mAmountTypes.isEmpty()) {
            for (CostItemAmountType type : mAmountTypes) {
                BottomTextBarTab tempTab = new BottomTextBarTab(this, type.getName());
                mBottomBar.addItem(tempTab);
            }
        }

        grid_type.setAdapter(mGridAdapter);
        grid_type.setOnItemClickListener(mGridItemClickener);
        grid_type.setOnItemSelectedListener(mGridItemSelectedListener);

        Long intentItemId = getIntent().getLongExtra("intent_cost_edit_item_id", -1L);
        if (intentItemId != -1L) {
//            bar_top.setTitle("修改记账");
            mCostItem = (CostItem) MoneyApplication.getDaoInstant()
                    .getCostItemDao()
                    .queryBuilder()
                    .where(
                            com.peil.smartmoney.greendao.gen.CostItemDao.Properties._id
                                    .eq(intentItemId))
                    .unique();
            updateAmountType(mAmountTypes.indexOf(mCostItem.getCostAmountType()));
            int gridIndex =
                    getAmountTypeList(mCostItem.getCostAmountType()).indexOf(mCostItem.getCostType());
            mGridAdapter.setSelectedPosition(gridIndex);
            mGridAdapter.notifyDataSetChanged();
            updateChooseDate(mCostItem.getTempCostDate());
            updateChooseAccount(mAccountListData.indexOf(mCostItem.getCostAccount()));
            view_amount.setAmount(mCostItem.getCostAmount());
        } else {
//            bar_top.setTitle("添加记账");
            updateAmountType(0);
            mGridAdapter.setSelectedPosition(0);
            mGridAdapter.notifyDataSetChanged();
            updateAmountGrid();
            updateChooseDate(TimeUtil.millis2Str(System.currentTimeMillis(), "yyyy-MM-dd"));
            updateChooseAccount(0);
        }
    }

    private void updateAmountGrid() {
        CostItemType item = mGridAdapter.getSelectedItem();
        if (item != null) {
            mCostItem.setCostTypeId(item.get_id());
            mCostItem.setCostType(item);
        }
    }

    private List<CostItemType> getAmountTypeList(CostItemAmountType type) {
        if (type.getName().equals("收入")) {
            return mGridInData;
        } else {
            return type.getName().equals("支出") ? mGridOutData : new ArrayList();
        }
    }

    private void updateAmountType(int position) {
        CostItemAmountType item = (CostItemAmountType) mAmountTypes.get(position);
        mCostItem.setCostAmountTypeId(item.get_id());
        mCostItem.setCostAmountType(item);
        mGridAdapter.addAll(getAmountTypeList(item));
        updateAmountGrid();
        mGridAdapter.notifyDataSetChanged();
    }

    private void updateChooseDate(String date) {
        LogUtils.i(new Object[]{"updateChooseDate ", date});
        if (!item_choose_date.getDetailText().toString().equals(date)) {
            mCostItem.setTempCostDate(date);
            item_choose_date.setDetailText(mCostItem.getTempCostDate());
        }
    }


    private void updateChooseAccount(int position) {
        CostItemAccount item = (CostItemAccount) mAccountListData.get(position);
        mCostItem.setCostAccount(item);
        item_choose_account.setDetailText(mCostItem.getCostAccount().getCostAccountame());
    }

    private void showChooseTimeView() {
        mPickerDate.setCanceledOnTouchOutside(true);
        mPickerDate.setUseWeight(true);
        mPickerDate.setTopPadding(ConvertUtils.toPx(this, 10.0F));
        mPickerDate.setRangeEnd(2111, 1, 11);
        mPickerDate.setRangeStart(2016, 1, 1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int tempYear = calendar.get(Calendar.YEAR);
        int tempMonth = calendar.get(Calendar.MONTH);
        int tempDay = calendar.get(Calendar.DAY_OF_MONTH);
        LogUtils.i(new Object[]{
                "cost add datepicker ",
                " year = " + tempYear + " month = " + tempMonth + "day = " + tempDay
        });
        mPickerDate.setSelectedItem(tempYear, tempMonth, tempDay);
        mPickerDate.setResetWhileWheel(false);
        mPickerDate.setOnDatePickListener(new OnYearMonthDayPickListener() {
            public void onDatePicked(String year, String month, String day) {
                updateChooseDate(year + "-" + month + "-" + day);
            }
        });
        mPickerDate.setOnWheelListener(new OnWheelListener() {
            public void onYearWheeled(int index, String year) {
            }

            public void onMonthWheeled(int index, String month) {
            }

            public void onDayWheeled(int index, String day) {
            }
        });
        mPickerDate.show();
    }

    private void showChooseAccountView() {
        BottomListSheetBuilder builder = new BottomListSheetBuilder(this, true);
        Iterator var2 = mAccountListData.iterator();

        while (var2.hasNext()) {
            CostItemAccount item = (CostItemAccount) var2.next();
            builder.addItem(item.getCostAccountame());
        }

        builder.setOnSheetItemClickListener(mAccountItemClickListener);
        builder.build().show();
    }

}
