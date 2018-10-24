package com.peil.smartmoney.cost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DatePicker.OnWheelListener;
import cn.qqtheme.framework.picker.DatePicker.OnYearMonthDayPickListener;
import cn.qqtheme.framework.util.ConvertUtils;
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
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

//
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
    private QMUIRoundButton btn_choose_date;
    private QMUIRoundButton btn_choose_account;
    private QMUIRoundButton btn_remark;
    private CostAddTypeAdapter mGridAdapter;
    
    private OnTabSelectedListener mOnTabSelectedListener = new OnTabSelectedListener() {
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
                case R.id.btn_choose_account:
                    showChooseAccountView();
                    break;
                case R.id.btn_choose_date:
                    showChooseTimeView();
                    break;
                case R.id.btn_remark:
                    showInputRemarkView();
            }
        }
    };
    
    private OnItemClickListener mGridItemClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (!mGridAdapter.hasSelected(position)) {
                mGridAdapter.clearSelectedArray();
                mGridAdapter.setSelectedState(position);
                mGridAdapter.notifyDataSetChanged();
                updateAmountGrid();
            }
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
    
    public CostAddActivity() {
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
            if (this.mCostItem.getCreateTime() == null) {
                this.mCostItem.setCreateTime(System.currentTimeMillis());
            }
            
            this.mCostItem.setLastModifyTime(System.currentTimeMillis());
            long temp =
                MoneyApplication.getDaoInstant().getCostItemDao().insertOrReplace(this.mCostItem);
            LogUtils.d(new Object[] { "提交数据库：现在记账数据 " + temp });
            if (temp != -1L) {
                ToastUtils.showShort("记账成功");
                this.sendBroadcast(new Intent("receiver_costlist_update"));
                this.onBackPressed();
            }
        }
    }
    
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
    }
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_cost_add);
        
        this.mGridAdapter = new CostAddTypeAdapter(this);
        this.mPickerDate = new DatePicker(this);
        this.bar_top = (QMUITopBarLayout) this.findViewById(R.id.bar_top);
        this.mBottomBar = (BottomBar) this.findViewById(R.id.bottomBar);
        this.view_amount = (InputAmountView) this.findViewById(R.id.view_amount);
        this.grid_type = (GridView) this.findViewById(R.id.grid_type);
        this.btn_choose_date = (QMUIRoundButton) this.findViewById(R.id.btn_choose_date);
        this.btn_choose_account = (QMUIRoundButton) this.findViewById(R.id.btn_choose_account);
        this.btn_remark = (QMUIRoundButton) this.findViewById(R.id.btn_remark);
        this.bar_top.addLeftBackImageButton().setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.mBottomBar.setOnTabSelectedListener(this.mOnTabSelectedListener);
        this.view_amount.setOnAmountClickListener(this.mOnAmountClickListener);
        this.btn_choose_date.setText("选择日期");
        this.btn_choose_date.setOnClickListener(this.mOnClickListener);
        this.btn_choose_account.setText("选择账户");
        this.btn_choose_account.setOnClickListener(this.mOnClickListener);
        this.btn_remark.setText("备注");
        this.btn_remark.setOnClickListener(this.mOnClickListener);
        this.mAmountTypes = MoneyApplication.getDaoInstant().getCostItemAmountTypeDao().loadAll();
        Iterator var2 = this.mAmountTypes.iterator();
        
        CostItemAmountType item;
        while (var2.hasNext()) {
            item = (CostItemAmountType) var2.next();
            if (item.getName().equals("收入")) {
                this.mGridInData.clear();
                this.mGridInData = MoneyApplication.getDaoInstant()
                                                   .getCostItemTypeDao()
                                                   .queryBuilder()
                                                   .where(CostItemTypeDao.Properties.AmountTypeId.eq(item.get_id()))
                                                   .list();
            } else if (item.getName().equals("支出")) {
                this.mGridOutData.clear();
                this.mGridOutData = MoneyApplication.getDaoInstant()
                                                    .getCostItemTypeDao()
                                                    .queryBuilder()
                                                    .where(
                                                        CostItemTypeDao.Properties.AmountTypeId.eq(item.get_id()))
                                                    .list();
            }
        }
        
        this.mAccountListData = MoneyApplication.getDaoInstant().getCostItemAccountDao().loadAll();
        if (!this.mAmountTypes.isEmpty()) {
            var2 = this.mAmountTypes.iterator();
            
            while (var2.hasNext()) {
                item = (CostItemAmountType) var2.next();
                BottomTextBarTab tempTab = new BottomTextBarTab(this, item.getName());
                this.mBottomBar.addItem(tempTab);
            }
        }
        
        this.grid_type.setAdapter(this.mGridAdapter);
        this.grid_type.setOnItemClickListener(this.mGridItemClickListener);
        Long intentItemId = this.getIntent().getLongExtra("intent_cost_edit_item_id", -1L);
        if (intentItemId != -1L) {
            this.bar_top.setTitle("修改记账");
            this.mCostItem = (CostItem) MoneyApplication.getDaoInstant()
                                                        .getCostItemDao()
                                                        .queryBuilder()
                                                        .where(
                                                            com.peil.smartmoney.greendao.gen.CostItemDao.Properties._id
                                                                .eq(intentItemId))
                                                        .unique();
            this.updateAmountType(this.mAmountTypes.indexOf(this.mCostItem.getCostAmountType()));
            int gridIndex = this.getAmountTypeList(this.mCostItem.getCostAmountType())
                                .indexOf(this.mCostItem.getCostType());
            this.mGridAdapter.setSelectedState(gridIndex);
            this.mGridAdapter.notifyDataSetChanged();
            this.updateChooseDate(this.mCostItem.getTempCostDate());
            this.updateChooseAccount(
                this.mAccountListData.indexOf(this.mCostItem.getCostAccount()));
            this.view_amount.setAmount(this.mCostItem.getCostAmount());
        } else {
            this.bar_top.setTitle("添加记账");
            this.updateAmountType(0);
            this.mGridAdapter.setSelectedState(0);
            this.mGridAdapter.notifyDataSetChanged();
            this.updateAmountGrid();
            this.updateChooseDate(TimeUtil.millis2Str(System.currentTimeMillis(), "yyyy-MM-dd"));
            this.updateChooseAccount(0);
        }
    }
    
    private void updateAmountGrid() {
        CostItemType item = (CostItemType) this.mGridAdapter.getSelectedItem(0);
        if (item != null) {
            this.mCostItem.setCostTypeId(item.get_id());
            this.mCostItem.setCostType(item);
        }
    }
    
    private List<CostItemType> getAmountTypeList(CostItemAmountType type) {
        if (type.getName().equals("收入")) {
            return this.mGridInData;
        } else {
            return type.getName().equals("支出") ? this.mGridOutData : new ArrayList();
        }
    }
    
    private void updateAmountType(int position) {
        CostItemAmountType item = (CostItemAmountType) this.mAmountTypes.get(position);
        this.mCostItem.setCostAmountTypeId(item.get_id());
        this.mCostItem.setCostAmountType(item);
        this.mGridAdapter.addAll(this.getAmountTypeList(item));
        this.updateAmountGrid();
        this.mGridAdapter.notifyDataSetChanged();
    }
    
    private void updateChooseDate(String date) {
        LogUtils.i(new Object[] { "updateChooseDate ", date });
        if (!this.btn_choose_date.getText().equals(date)) {
            this.mCostItem.setTempCostDate(date);
            this.btn_choose_date.setText(this.mCostItem.getTempCostDate());
        }
    }
    
    private void updateRemark(String text) {
        if (!this.btn_remark.getText().equals(text)) {
            this.mCostItem.setRemark(text);
            this.btn_remark.setText(this.mCostItem.getRemark());
        }
    }
    
    private void updateChooseAccount(int position) {
        CostItemAccount item = (CostItemAccount) this.mAccountListData.get(position);
        this.mCostItem.setCostAccount(item);
        this.btn_choose_account.setText(this.mCostItem.getCostAccount().getCostAccountame());
    }
    
    private void showChooseTimeView() {
        this.mPickerDate.setCanceledOnTouchOutside(true);
        this.mPickerDate.setUseWeight(true);
        this.mPickerDate.setTopPadding(ConvertUtils.toPx(this, 10.0F));
        this.mPickerDate.setRangeEnd(2111, 1, 11);
        this.mPickerDate.setRangeStart(2016, 1, 1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int tempYear = calendar.get(Calendar.YEAR);
        int tempMonth = calendar.get(Calendar.MONTH);
        int tempDay = calendar.get(Calendar.DAY_OF_MONTH);
        LogUtils.i(new Object[] {
            "cost add datepicker ",
            " year = " + tempYear + " month = " + tempMonth + "day = " + tempDay
        });
        this.mPickerDate.setSelectedItem(tempYear, tempMonth, tempDay);
        this.mPickerDate.setResetWhileWheel(false);
        this.mPickerDate.setOnDatePickListener(new OnYearMonthDayPickListener() {
            public void onDatePicked(String year, String month, String day) {
                updateChooseDate(year + "-" + month + "-" + day);
            }
        });
        this.mPickerDate.setOnWheelListener(new OnWheelListener() {
            public void onYearWheeled(int index, String year) {
            }
            
            public void onMonthWheeled(int index, String month) {
            }
            
            public void onDayWheeled(int index, String day) {
            }
        });
        this.mPickerDate.show();
    }
    
    private void showChooseAccountView() {
        BottomListSheetBuilder builder = new BottomListSheetBuilder(this, true);
        Iterator var2 = this.mAccountListData.iterator();
        
        while (var2.hasNext()) {
            CostItemAccount item = (CostItemAccount) var2.next();
            builder.addItem(item.getCostAccountame());
        }
        
        builder.setOnSheetItemClickListener(this.mAccountItemClickListener);
        builder.build().show();
    }
    
    private void showInputRemarkView() {
        
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(this);
        
        builder.setTitle("备注")
               .setPlaceholder("请输入备注...")
               .addAction("取消", new QMUIDialogAction.ActionListener() {
                   @Override public void onClick(QMUIDialog dialog, int index) {
                       dialog.dismiss();
                   }
               })
               .addAction("确定", new QMUIDialogAction.ActionListener() {
                   @Override public void onClick(QMUIDialog dialog, int index) {
                       CharSequence text = builder.getEditText().getText();
                       updateRemark(text.toString());
                       dialog.dismiss();
                   }
               })
               .create(R.style.qmui_dialog_wrap)
               .show();
         
    }
}
