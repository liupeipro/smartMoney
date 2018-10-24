package com.peil.smartmoney.calculator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.peil.smartmoney.R;
import com.peil.smartmoney.adapter.CalculatorListAdapter;
import com.peil.smartmoney.base.BaseFragment;
import com.peil.smartmoney.base.MoneyApplication;
import com.peil.smartmoney.greendao.gen.CostItemDao;
import com.peil.smartmoney.model.CalculCostType;
import com.peil.smartmoney.model.CalculatorListItem;
import com.peil.smartmoney.model.CostItem;
import com.peil.smartmoney.model.CostItemAmountType;
import com.peil.smartmoney.util.FloatUtils;
import com.peil.smartmoney.util.GsonUtils;
import com.peil.smartmoney.util.ReceiverUtils;
import com.peil.smartmoney.util.TimeUtil;
import com.peil.smartmoney.view.BottomBar;
import com.peil.smartmoney.view.BottomTextBarTab;
import com.peil.smartmoney.view.RefreshLayout;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 统计
 */
public class CalculatorFragment extends BaseFragment {
    private BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context , Intent intent) {
            if (intent.getAction().equals(ReceiverUtils.RECEIVER_COST_LIST_UPDATE)) {
                updateDataAll();
            }
        }
    };
    private RefreshLayout pull_to_refresh;
    CostItemAmountType mAmountType = null;
    Long mStartTime, mEndTime;
    List<CalculatorListItem> mListData = new ArrayList<>();
    private List<CostItemAmountType> mAmountTypes = new ArrayList<>();
    private BottomBar.OnTabSelectedListener mOnTabSelectedListener =
        new BottomBar.OnTabSelectedListener() {
            @Override public void onTabSelected(int position , int prePosition) {
                updateAmountTypeData(position);
            }
            
            @Override public void onTabUnselected(int position) {
            }
            
            @Override public void onTabReselected(int position) {
            }
        };
    
    private void updateAmountTypeData(int position) {
        mAmountType = mAmountTypes.get(position);
        updateDataAll();
    }
    
    private void updateDataAll() {
        tv_time.setText(
            TimeUtil.millis2Str(mStartTime , "yyyy年MM月") + " - " + TimeUtil.millis2Str(mEndTime ,
                                                                                       "yyyy年MM月"));
        updateData(mAmountType , mStartTime , mEndTime);
    }
    
    private OnChartValueSelectedListener mOnChartValueSelectedListener =
        new OnChartValueSelectedListener() {
            @Override public void onValueSelected(Entry e , Highlight h) {
                if (e == null) {
                    return;
                }
                
                LogUtils.i("VAL SELECTED" , "Value: "
                    + e.getY()
                    + ", index: "
                    + h.getX()
                    + ", DataSet index: "
                    + h.getDataSetIndex());
            }
            
            @Override public void onNothingSelected() {
                LogUtils.i("PieChart" , "#nothing selected");
            }
        };
    private QMUITopBarLayout bar_top;
    private BottomBar mBottomBar;
    private TextView tv_time;
    private ListView listview;
    private CalculatorListAdapter mListAdapter;
    
    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        
        s.setSpan(new RelativeSizeSpan(1.7f) , 0 , 14 , 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL) , 14 , s.length() - 15 , 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY) , 14 , s.length() - 15 , 0);
        s.setSpan(new RelativeSizeSpan(.8f) , 14 , s.length() - 15 , 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC) , s.length() - 14 , s.length() , 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()) , s.length() - 14 ,
                  s.length() , 0);
        
        return s;
    }
    
    private void initView(View view) {
        bar_top = view.findViewById(R.id.bar_top);
        bar_top.setTitle("统计");
        bar_top.addRightTextButton("筛选" , R.id.topbar_right_change_button)
               .setOnClickListener(new View.OnClickListener() {
                   @Override public void onClick(View v) {
                       startActivity(new Intent(_mActivity.getApplicationContext() ,
                                                CalculatorFilterActivity.class));
                   }
               });
        tv_time = view.findViewById(R.id.tv_time);
        listview = view.findViewById(R.id.listview);
        listview.setAdapter(mListAdapter);
        mBottomBar = (BottomBar)view.findViewById(R.id.bottomBar);
        pull_to_refresh = view.findViewById(R.id.pull_to_refresh);
        pull_to_refresh.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override public void onMoveTarget(int offset) {
            }
            
            @Override public void onMoveRefreshView(int offset) {
            }
            
            @Override public void onRefresh() {
                updateDataAll();
            }
        });
        
        // data 初始化
        mAmountTypes = MoneyApplication.getDaoInstant().getCostItemAmountTypeDao().loadAll();
        
        if (!mAmountTypes.isEmpty()) {
            for (CostItemAmountType item : mAmountTypes) {
                BottomTextBarTab tempTab = new BottomTextBarTab(_mActivity , item.getName());
                
                mBottomBar.addItem(tempTab);
            }
        }
        
        mBottomBar.setOnTabSelectedListener(mOnTabSelectedListener);
        
        firstInitData();
    }
    
    private void firstInitData() {
        //指定初始数据
        mStartTime = TimeUtil.getMonthAgoForNow();
        mEndTime = TimeUtils.getNowMills();
        updateAmountTypeData(0);
    }
    
    public static CalculatorFragment newInstance() {
        Bundle args = new Bundle();
        CalculatorFragment fragment = new CalculatorFragment();
        
        fragment.setArguments(args);
        
        return fragment;
    }
    
    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new CalculatorListAdapter(_mActivity);
        registerLocalReceiver(mUpdateReceiver ,
                              new IntentFilter(ReceiverUtils.RECEIVER_COST_LIST_UPDATE));
    }
    
    @Override public void onDestroy() {
        super.onDestroy();
        unRegistLocalReceiver(mUpdateReceiver);
    }
    
    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater , @Nullable ViewGroup container ,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_calculator , container , false);
        
        initView(view);
        return view;
    }
    
    private List<CalculCostType> parseData(List<CostItem> datas) {
        HashMap<Long,List<CostItem>> groups = new HashMap<>();
        
        //      根据类别分组
        for (CostItem costItem : datas) {
            Long tempCostItemTypeId = costItem.getCostTypeId();
            
            if (groups.containsKey(tempCostItemTypeId)) {
                List<CostItem> tItems = groups.get(tempCostItemTypeId);
                tItems.add(costItem);
            } else {
                List<CostItem> tItems = new ArrayList<>();
                tItems.add(costItem);
                groups.put(tempCostItemTypeId , tItems);
            }
        }
        
        //      map转list 分组
        List<CalculCostType> tempGroups = new ArrayList<>();
        Double totalAmount = 0d;
        Set<Map.Entry<Long,List<CostItem>>> entrySet = groups.entrySet();
        
        for (Map.Entry<Long,List<CostItem>> entrys : entrySet) {
            List<CostItem> items = entrys.getValue();
            
            CalculCostType ti = new CalculCostType();
            ti.setCostType(items.get(0).getCostType());
            ti.setCostItems(items);
            ti.updateAmount();
            totalAmount = FloatUtils.add(totalAmount , ti.getTotalAmount());
            tempGroups.add(ti);
        }
        
        //      指定比例
        for (CalculCostType groupItem : tempGroups) {
            groupItem.setScale(FloatUtils.div(groupItem.getTotalAmount() , totalAmount , 4) * 100);
        }
        
        Collections.sort(tempGroups);
        return tempGroups;
    }
    
    
    private void updateData(CostItemAmountType itemType , Long startTime , long endTime) {
        // 获取数据库中数据
        List<CostItem> tempData = getDbData(itemType.get_id() , startTime , endTime);
        //数据进行分类
        List<CalculCostType> result = parseData(tempData);
        
        pull_to_refresh.finishRefresh();
        LogUtils.d(GsonUtils.toJson(result));
        mListAdapter.addAll(result);
    }
    
    /**
     * 获取数据库中关于记账列表数据
     */
    private List<CostItem> getDbData(Long amountTypeId , Long startTime , Long endTime) {
        List<CostItem> result = new ArrayList<>();
        result = MoneyApplication.getDaoInstant()
                                 .getCostItemDao()
                                 .queryBuilder()
                                 .where(CostItemDao.Properties.CostDate.ge(startTime) ,
                                        CostItemDao.Properties.CostDate.le(endTime) ,
                                        CostItemDao.Properties.CostAmountTypeId.eq(amountTypeId))
                                 .orderDesc(CostItemDao.Properties.CostDate)
                                 .list();
        return result;
    }
}
