package com.peil.smartmoney.calculator;

import android.content.Intent;
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
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.peil.smartmoney.R;
import com.peil.smartmoney.adapter.CalculatorListAdapter;
import com.peil.smartmoney.base.BaseFragment;
import com.peil.smartmoney.base.MoneyApplication;
import com.peil.smartmoney.cost.CostAddActivity;
import com.peil.smartmoney.greendao.gen.CostItemDao;
import com.peil.smartmoney.model.CalculatorListItem;
import com.peil.smartmoney.model.CostItem;
import com.peil.smartmoney.model.CostItemAmountType;
import com.peil.smartmoney.util.FloatUtils;
import com.peil.smartmoney.view.BottomBar;
import com.peil.smartmoney.view.BottomTextBarTab;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 统计
 */
public class CalculatorFragment extends BaseFragment {

  // 类别
  public static final String CALCULATOR_COST_CHART_TYPE = "calculator_cost_chart_type";

  // public static final String CALCULATOR_COST_CHART_TYPE="calculator_cost_chart_type";
  List<CalculatorListItem> mListData = new ArrayList<>();
  protected String[] mParties = new String[] {
      "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
      "Party I", "Party J",
      "Party K", "Party L", "Party M", "Party N", "Party O", "Party P", "Party Q", "Party R",
      "Party S", "Party T",
      "Party U", "Party V", "Party W", "Party X", "Party Y", "Party Z"
  };
  public Integer[] mColors;
  private List<CostItemAmountType> mAmountTypes = new ArrayList<>();
  private BottomBar.OnTabSelectedListener mOnTabSelectedListener =
      new BottomBar.OnTabSelectedListener() {
        @Override
        public void onTabSelected(int position, int prePosition) {
          CostItemAmountType item = mAmountTypes.get(position);

          updateData(item);
        }

        @Override
        public void onTabUnselected(int position) {
        }

        @Override
        public void onTabReselected(int position) {
        }
      };
  private OnChartValueSelectedListener mOnChartValueSelectedListener =
      new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {
          if (e == null) {
            return;
          }

          LogUtils.i("VAL SELECTED",
              "Value: "
                  + e.getY()
                  + ", index: "
                  + h.getX()
                  + ", DataSet index: "
                  + h.getDataSetIndex());
        }

        @Override
        public void onNothingSelected() {
          LogUtils.i("PieChart", "#nothing selected");
        }
      };
  private QMUITopBarLayout bar_top;
  private BottomBar mBottomBar;
  TextView tv_time;
  PieChart chart_pie;
  ListView listview;
  CalculatorListAdapter mListAdapter;

  private void firstInitData() {
    updateData(mAmountTypes.get(0));
  }

  private SpannableString generateCenterSpannableText() {
    SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");

    s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
    s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
    s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
    s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
    s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
    s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);

    return s;
  }

  private void initView(View view) {
    bar_top = view.findViewById(R.id.bar_top);
    bar_top.setTitle("统计");
    bar_top.addRightTextButton("筛选", R.id.topbar_right_change_button)
        .setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            startActivity(
                new Intent(_mActivity.getApplicationContext(), CostAddActivity.class));
          }
        });
    tv_time = view.findViewById(R.id.tv_time);
    tv_time.setText("2018月10日 ----- 2018月11日");
    listview = view.findViewById(R.id.listview);
    listview.setAdapter(mListAdapter);
    mBottomBar = (BottomBar) view.findViewById(R.id.bottomBar);

    // data 初始化
    mAmountTypes = MoneyApplication.getDaoInstant().getCostItemAmountTypeDao().loadAll();

    if (!mAmountTypes.isEmpty()) {
      for (CostItemAmountType item : mAmountTypes) {
        BottomTextBarTab tempTab = new BottomTextBarTab(_mActivity, item.getName());

        mBottomBar.addItem(tempTab);
      }
    }

    mBottomBar.setOnTabSelectedListener(mOnTabSelectedListener);
    firstInitData();
  }

  public static CalculatorFragment newInstance() {
    Bundle args = new Bundle();
    CalculatorFragment fragment = new CalculatorFragment();

    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mListAdapter = new CalculatorListAdapter(_mActivity);
    mColors = new Integer[] {
        getContext().getColor(R.color.chart_1), getContext().getColor(R.color.chart_2),
        getContext().getColor(R.color.chart_3), getContext().getColor(R.color.chart_4),
        getContext().getColor(R.color.chart_5), getContext().getColor(R.color.chart_6),
        getContext().getColor(R.color.chart_7), getContext().getColor(R.color.chart_8),
        getContext().getColor(R.color.chart_9), getContext().getColor(R.color.chart_10),
        getContext().getColor(R.color.chart_11), getContext().getColor(R.color.chart_12),
        getContext().getColor(R.color.chart_13), getContext().getColor(R.color.chart_14),
        getContext().getColor(R.color.chart_15), getContext().getColor(R.color.chart_16),
        getContext().getColor(R.color.chart_17)
    };
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.frag_calculator, container, false);

    initView(view);

    return view;
  }

  private List<CalculatorListItem> parseData(List<CostItem> datas, float range) {
    HashMap<Long, List<CostItem>> groups = new HashMap<>();

    //      根据类别分组
    for (CostItem costItem : datas) {
      Long tempCostItemTypeId = costItem.getCostTypeId();

      if (groups.containsKey(tempCostItemTypeId)) {
        List<CostItem> tItems = groups.get(tempCostItemTypeId);

        tItems.add(costItem);
      } else {
        List<CostItem> tItems = new ArrayList<>();

        tItems.add(costItem);
        groups.put(tempCostItemTypeId, tItems);
      }
    }

    //      map转list 分组
    List<CalculatorListItem> tempGroups = new ArrayList<>();
    Double totalAmount = 0d;
    Set<Map.Entry<Long, List<CostItem>>> entrySet = groups.entrySet();

    for (Map.Entry<Long, List<CostItem>> entrys : entrySet) {
      CalculatorListItem ti = new CalculatorListItem();

      ti.setTypeId(entrys.getKey());
      ti.setData(entrys.getValue());
      ti.updateTotalAmount();
      totalAmount = FloatUtils.add(totalAmount, ti.getTotalAmount());
      tempGroups.add(ti);
    }

    //      指定比例
    for (CalculatorListItem groupItem : tempGroups) {
      groupItem.setScaleIn(String.valueOf(FloatUtils.div(groupItem.getTotalAmount(), totalAmount)));
    }

    return tempGroups;
  }

  private void updateData(CostItemAmountType itemType) {

    // 获取数据库中数据
    List<CostItem> tempData = getDbData(CALCULATOR_COST_CHART_TYPE, itemType.get_id());
    List<CalculatorListItem> data = parseData(tempData, 100);

    mListAdapter.setContItemAmountType(itemType);

    // update pieChart
    List<PieEntry> entries = new ArrayList<PieEntry>();
    List<Integer> colors = new ArrayList<Integer>();
    int count = data.size();

    for (int i = 0; i < count; i++) {
      CalculatorListItem item = data.get(i);
      entries.add(
          new PieEntry((Float.valueOf(String.valueOf(item.getTotalAmount()))), item.getScaleIn()));
      colors.add(mColors[i]);
    }

    listview.addHeaderView(getListHeader(entries, colors));
    mListAdapter.addAll(data);
  }

  private void setData(List<PieEntry> entries, List<Integer> colors) {
    PieDataSet dataSet = new PieDataSet(entries, "Election Results");

    dataSet.setDrawIcons(false);
    dataSet.setSliceSpace(3f);
    dataSet.setIconsOffset(new MPPointF(0, 40));
    dataSet.setSelectionShift(5f);
    dataSet.setColors(colors);

    // dataSet.setSelectionShift(0f);
    PieData data = new PieData(dataSet);

    data.setValueFormatter(new PercentFormatter());
    data.setValueTextSize(11f);
    data.setValueTextColor(Color.WHITE);

    //      data.setValueTypeface(mTfLight);
    chart_pie.setData(data);

    // undo all highlights
    chart_pie.highlightValues(null);
    chart_pie.invalidate();
  }

  private List<CostItem> getDbData(String dataType, Long amountTypeId) {
    List<CostItem> result = new ArrayList<>();

    if (dataType.equals(CALCULATOR_COST_CHART_TYPE)) {
      result = MoneyApplication.getDaoInstant()
          .getCostItemDao()
          .queryBuilder()
          .where(CostItemDao.Properties.CostAmountTypeId.eq(amountTypeId))
          .orderDesc(CostItemDao.Properties.CostDate)
          .list();
    }

    return result;
  }

  private View getListHeader(List<PieEntry> entries, List<Integer> colors) {
    View view = LayoutInflater.from(_mActivity.getApplicationContext())
        .inflate(R.layout.item_calculator_list_header, null);

    chart_pie = view.findViewById(R.id.chart_pie);
    chart_pie = view.findViewById(R.id.chart_pie);
    chart_pie.setUsePercentValues(true);
    chart_pie.getDescription().setEnabled(false);
    chart_pie.setExtraOffsets(5, 10, 5, 5);
    chart_pie.setDragDecelerationFrictionCoef(0.95f);

    //      chart_pie.setCenterTextTypeface(mTfLight);
    chart_pie.setCenterText(generateCenterSpannableText());
    chart_pie.setDrawHoleEnabled(true);
    chart_pie.setHoleColor(Color.WHITE);
    chart_pie.setTransparentCircleColor(Color.WHITE);
    chart_pie.setTransparentCircleAlpha(110);
    chart_pie.setHoleRadius(58f);
    chart_pie.setTransparentCircleRadius(61f);
    chart_pie.setDrawCenterText(true);
    chart_pie.setRotationAngle(0);

    // enable rotation of the chart by touch
    chart_pie.setRotationEnabled(true);
    chart_pie.setHighlightPerTapEnabled(true);

    // chart_pie.setUnit(" €");
    // chart_pie.setDrawUnitsInChart(true);
    // add a selection listener
    chart_pie.setOnChartValueSelectedListener(mOnChartValueSelectedListener);
    setData(entries, colors);
    chart_pie.animateY(1400, Easing.EasingOption.EaseInOutQuad);

    // chart_pie.spin(2000, 0, 360);
    Legend l = chart_pie.getLegend();

    l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
    l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
    l.setOrientation(Legend.LegendOrientation.VERTICAL);
    l.setDrawInside(false);
    l.setXEntrySpace(7f);
    l.setYEntrySpace(0f);
    l.setYOffset(0f);

    // entry label styling
    chart_pie.setEntryLabelColor(Color.WHITE);

    //      chart_pie.setEntryLabelTypeface(mTfRegular);
    chart_pie.setEntryLabelTextSize(12f);

    return view;
  }
}

//~ Formatted by Jindent --- http://www.jindent.com
