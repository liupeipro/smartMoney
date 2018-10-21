package com.peil.smartmoney.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.peil.smartmoney.R;
import com.peil.smartmoney.adapter.CostAddTypeAdapter;
import com.peil.smartmoney.base.BaseActivity;
import com.peil.smartmoney.base.MoneyApplication;
import com.peil.smartmoney.greendao.gen.CostItemDao;
import com.peil.smartmoney.greendao.gen.CostItemTypeDao;
import com.peil.smartmoney.model.CostItem;
import com.peil.smartmoney.model.CostItemAccount;
import com.peil.smartmoney.model.CostItemAmountType;
import com.peil.smartmoney.model.CostItemType;
import com.peil.smartmoney.util.MoneyConstants;
import com.peil.smartmoney.util.ReceiverUtils;
import com.peil.smartmoney.util.TimeUtil;
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

public class CalculatorFilterActivity extends BaseActivity {
    
    // 变量
    private List<CostItemType> mGridInData = new ArrayList<>();
    private List<CostItemType> mGridOutData = new ArrayList<>();
    private List<CostItemAccount> mAccountListData = new ArrayList<>();
    private List<CostItemAmountType> mAmountTypes = new ArrayList<>();
    private CostItem mCostItem = new CostItem();
    private BottomBar.OnTabSelectedListener mOnTabSelectedListener =
        new BottomBar.OnTabSelectedListener() {
            @Override public void onTabSelected(int position, int prePosition) {
                updateAmountType(position);
            }
            
            @Override public void onTabUnselected(int position) {
            }
            
            @Override public void onTabReselected(int position) {
            }
        };
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_choose_date:
                    
                    //              显示选择时间的View
                    showChooseTimeView();
                    
                    break;
                
                case R.id.btn_choose_account:
                    
                    //              显示选择账户的View
                    showChooseAccountView();
                    
                    break;
                
                case R.id.btn_remark:
                    
                    //              显示备注
                    showInputRemarkView();
                    
                    break;
            }
        }
    };
    private AdapterView.OnItemClickListener mGridItemClickListener =
        new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mGridAdapter.hasSelected(position)) {
                    mGridAdapter.clearSelectedArray();
                    mGridAdapter.setSelectedState(position);
                    mGridAdapter.notifyDataSetChanged();
                    updateAmountGrid();
                }
            }
        };
    private InputAmountView.OnAmountClickListener mOnAmountClickListener =
        new InputAmountView.OnAmountClickListener() {
            @Override public void onAmountZero() {
                ToastUtils.showShort("请输入金额");
            }
            
            @Override public void onAmountDone(float amount) {
                mCostItem.setCostAmount(String.valueOf(amount));
                onSubmit(mCostItem);
            }
        };
    private QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener
        mAccountItemClickListener =
        new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
            @Override
            public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                dialog.dismiss();
                updateChooseAccount(position);
            }
        };
    
    // view
    private QMUITopBarLayout bar_top;
    private InputAmountView view_amount;
    private DatePicker mPickerDate;
    private BottomBar mBottomBar;
    private GridView grid_type;
    private QMUIRoundButton btn_choose_date, btn_choose_account, btn_remark;
    
    // Adapter
    private CostAddTypeAdapter mGridAdapter;
    
    @Override public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
    }
    
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_add);
        
        //      变量初始化
        mGridAdapter = new CostAddTypeAdapter(this);
        mPickerDate = new DatePicker(this);
        
        // view 初始化
        bar_top = findViewById(R.id.bar_top);
        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        view_amount = findViewById(R.id.view_amount);
        grid_type = findViewById(R.id.grid_type);
        btn_choose_date = findViewById(R.id.btn_choose_date);
        btn_choose_account = findViewById(R.id.btn_choose_account);
        btn_remark = findViewById(R.id.btn_remark);
        
        //      title
        bar_top.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onBackPressed();
            }
        });
        
        // listener
        mBottomBar.setOnTabSelectedListener(mOnTabSelectedListener);
        view_amount.setOnAmountClickListener(mOnAmountClickListener);
        btn_choose_date.setText("选择日期");
        btn_choose_date.setOnClickListener(mOnClickListener);
        btn_choose_account.setText("选择账户");
        btn_choose_account.setOnClickListener(mOnClickListener);
        btn_remark.setText("备注");
        btn_remark.setOnClickListener(mOnClickListener);
        
        // data 初始化
        mAmountTypes = MoneyApplication.getDaoInstant().getCostItemAmountTypeDao().loadAll();
        
        for (CostItemAmountType item : mAmountTypes) {
            if (item.getName().equals("收入")) {
                mGridInData.clear();
                mGridInData = MoneyApplication.getDaoInstant()
                                              .getCostItemTypeDao()
                                              .queryBuilder()
                                              .where(CostItemTypeDao.Properties.AmountTypeId.eq(
                                                  item.get_id()))
                                              .list();
            } else if (item.getName().equals("支出")) {
                mGridOutData.clear();
                mGridOutData = MoneyApplication.getDaoInstant()
                                               .getCostItemTypeDao()
                                               .queryBuilder()
                                               .where(CostItemTypeDao.Properties.AmountTypeId.eq(
                                                   item.get_id()))
                                               .list();
            }
        }
        
        mAccountListData = MoneyApplication.getDaoInstant().getCostItemAccountDao().loadAll();
        
        if (!mAmountTypes.isEmpty()) {
            for (CostItemAmountType item : mAmountTypes) {
                BottomTextBarTab tempTab = new BottomTextBarTab(this, item.getName());
                
                mBottomBar.addItem(tempTab);
            }
        }
        
        grid_type.setAdapter(mGridAdapter);
        grid_type.setOnItemClickListener(mGridItemClickListener);
        
        Long intentItemId = getIntent().getLongExtra(MoneyConstants.INTENT_COST_EDIT_ITEM_ID, -1);
        
        if (intentItemId != -1) {
            bar_top.setTitle("修改记账");
            mCostItem = MoneyApplication.getDaoInstant()
                                        .getCostItemDao()
                                        .queryBuilder()
                                        .where(CostItemDao.Properties._id.eq(intentItemId))
                                        .unique();
            updateAmountType(mAmountTypes.indexOf(mCostItem.getCostAmountType()));
            
            int gridIndex =
                getAmountTypeList(mCostItem.getCostAmountType()).indexOf(mCostItem.getCostType());
            
            // 设置默认选择项
            mGridAdapter.setSelectedState(gridIndex);
            mGridAdapter.notifyDataSetChanged();
            
            //          updateAmountGrid();
            updateChooseDate(mCostItem.getTempCostDate());
            updateChooseAccount(mAccountListData.indexOf(mCostItem.getCostAccount()));
            view_amount.setAmount(mCostItem.getCostAmount());
        } else {
            bar_top.setTitle("添加记账");
            updateAmountType(0);
            
            // 设置默认选择项
            mGridAdapter.setSelectedState(0);
            mGridAdapter.notifyDataSetChanged();
            updateAmountGrid();
            updateChooseDate(TimeUtil.millis2Str(System.currentTimeMillis(), "yyyy-MM-dd"));
            updateChooseAccount(0);
        }
    }
    
    private void onSubmit(CostItem item) {
        
        // 检查
        //      1- 金额
        if (item.getCostAmount().isEmpty()) {
            ToastUtils.showShort("请输入金额");
            
            return;
        }
        
        //      2- 类别
        if (item.getCostType() == null) {
            ToastUtils.showShort("请选择类别");
            
            return;
        }
        
        //      3- 账户
        if (item.getCostAccount() == null) {
            ToastUtils.showShort("请选择账户");
            
            return;
        }
        
        //      4- 日期
        if (item.getCostDate() == null) {
            ToastUtils.showShort("请选择日期");
            
            return;
        }
        
        if (mCostItem.getCreateTime() == null) {
            mCostItem.setCreateTime(Long.valueOf(System.currentTimeMillis()));
        }
        
        mCostItem.setLastModifyTime(Long.valueOf(System.currentTimeMillis()));
        
        long temp = MoneyApplication.getDaoInstant().getCostItemDao().insertOrReplace(mCostItem);
        
        LogUtils.d("提交数据库：现在记账数据 " + temp);
        
        if (temp != -1) {
            ToastUtils.showShort("记账成功");
            sendBroadcast(new Intent(ReceiverUtils.RECEIVER_COST_LIST_UPDATE));
            onBackPressed();
        }
    }
    
    private void showChooseAccountView() {
        QMUIBottomSheet.BottomListSheetBuilder builder =
            new QMUIBottomSheet.BottomListSheetBuilder(this, true);
        
        for (CostItemAccount item : mAccountListData) {
            builder.addItem(item.getCostAccountame());
        }
        
        builder.setOnSheetItemClickListener(mAccountItemClickListener);
        builder.build().show();
    }
    
    private void showChooseTimeView() {
        mPickerDate.setCanceledOnTouchOutside(true);
        mPickerDate.setUseWeight(true);
        mPickerDate.setTopPadding(ConvertUtils.toPx(this, 10));
        mPickerDate.setRangeEnd(2111, 1, 11);
        mPickerDate.setRangeStart(2016, 1, 1);
        
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTimeInMillis(System.currentTimeMillis());
        
        int tempYear = calendar.get(Calendar.YEAR);
        int tempMonth = calendar.get(Calendar.MONTH);
        int tempDay = calendar.get(Calendar.DAY_OF_MONTH);
        
        LogUtils.i("cost add datepicker ",
                   " year = " + tempYear + " month = " + tempMonth + "day = " + tempDay);
        mPickerDate.setSelectedItem(tempYear, tempMonth, tempDay);
        mPickerDate.setResetWhileWheel(false);
        mPickerDate.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override public void onDatePicked(String year, String month, String day) {
                updateChooseDate(year + "-" + month + "-" + day);
            }
        });
        mPickerDate.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override public void onYearWheeled(int index, String year) {
                
                //              mPickerDate.setTitleText(year + "-" + mPickerDate.getSelectedMonth() + "-" + mPickerDate.getSelectedDay());
            }
            
            @Override public void onMonthWheeled(int index, String month) {
                
                //              mPickerDate.setTitleText(mPickerDate.getSelectedYear() + "-" + month + "-" + mPickerDate.getSelectedDay());
            }
            
            @Override public void onDayWheeled(int index, String day) {
                
                //              mPickerDate.setTitleText(mPickerDate.getSelectedYear() + "-" + mPickerDate.getSelectedMonth() + "-" + day);
            }
        });
        mPickerDate.show();
    }
    
    private void showInputRemarkView() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(this);
        
        builder.setTitle("备注")
               .setPlaceholder("请输入备注...")
               .setInputType(InputType.TYPE_CLASS_TEXT)
               .setDefaultText(
                   (btn_remark.getText().toString().length() > 0) ? btn_remark.getText().toString()
                       : "")
               .addAction("取消", new QMUIDialogAction.ActionListener() {
                   @Override public void onClick(QMUIDialog dialog, int index) {
                       dialog.dismiss();
                   }
               })
               .addAction("确定", new QMUIDialogAction.ActionListener() {
                   @Override public void onClick(QMUIDialog dialog, int index) {
                       CharSequence text = builder.getEditText().getText();
                
                       if ((text != null) && (text.length() > 0)) {
                           updateRemark(text.toString());
                           dialog.dismiss();
                       } else {
                           Toast.makeText(CalculatorFilterActivity.this, "请填入昵称",
                                          Toast.LENGTH_SHORT).show();
                       }
                   }
               })
               .create(R.style.qmui_dialog_wrap)
               .show();
    }
    
    private void updateAmountGrid() {
        CostItemType item = mGridAdapter.getSelectedItem(0);
        
        if (item != null) {
            mCostItem.setCostTypeId(item.get_id());
            mCostItem.setCostType(item);
        }
    }
    
    private void updateAmountType(int position) {
        CostItemAmountType item = mAmountTypes.get(position);
        
        mCostItem.setCostAmountTypeId(item.get_id());
        mCostItem.setCostAmountType(item);
        mGridAdapter.addAll(getAmountTypeList(item));
        updateAmountGrid();
        mGridAdapter.notifyDataSetChanged();
    }
    
    /**
     * 更新账户信息
     */
    private void updateChooseAccount(int position) {
        CostItemAccount item = mAccountListData.get(position);
        
        mCostItem.setCostAccount(item);
        btn_choose_account.setText(mCostItem.getCostAccount().getCostAccountame());
    }
    
    private void updateChooseDate(String date) {
        LogUtils.i("updateChooseDate ", date);
        
        //      更新日期
        if (!btn_choose_date.getText().equals(date)) {
            mCostItem.setTempCostDate(date);
            btn_choose_date.setText(mCostItem.getTempCostDate());
        }
    }
    
    private void updateRemark(String text) {
        if (!btn_remark.getText().equals(text)) {
            mCostItem.setRemark(text);
            btn_remark.setText(mCostItem.getRemark());
        }
    }
    
    private List<CostItemType> getAmountTypeList(CostItemAmountType type) {
        if (type.getName().equals("收入")) {
            return mGridInData;
        } else if (type.getName().equals("支出")) {
            return mGridOutData;
        }
        
        return new ArrayList<CostItemType>();
    }
}

//~ Formatted by Jindent --- http://www.jindent.com
