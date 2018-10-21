package com.peil.smartmoney.cost;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.blankj.utilcode.util.LogUtils;
import com.peil.smartmoney.R;
import com.peil.smartmoney.adapter.CostListAdapter;
import com.peil.smartmoney.base.BaseFragment;
import com.peil.smartmoney.base.MoneyApplication;
import com.peil.smartmoney.calculator.CalculatorFilterActivity;
import com.peil.smartmoney.greendao.gen.CostItemDao;
import com.peil.smartmoney.model.CostItem;
import com.peil.smartmoney.model.CostListItem;
import com.peil.smartmoney.model.CostListItemSectioner;
import com.peil.smartmoney.util.GsonUtils;
import com.peil.smartmoney.util.MoneyConstants;
import com.peil.smartmoney.util.ReceiverUtils;
import com.peil.smartmoney.view.RefreshLayout;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import de.halfbit.pinnedsection.PinnedSectionListView;
import java.util.ArrayList;
import java.util.List;

public class CostFragment extends BaseFragment {
    BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ReceiverUtils.RECEIVER_COST_LIST_UPDATE)) {
                pull_to_refresh.doRefresh();
            }
        }
    };
    private QMUITopBarLayout bar_top;
    RefreshLayout            pull_to_refresh;
    PinnedSectionListView    listview;
    CostListAdapter          mListAdapter;
    List<CostItem>           mListData;

    private void gotoCheck(CostItem item) {
        Intent intent = new Intent(_mActivity, CostCheckActivity.class);

        intent.putExtra(MoneyConstants.INTENT_COST_EDIT_ITEM_ID, item.get_id());
        startActivity(intent);
    }

    private void initView(View view) {
        bar_top = view.findViewById(R.id.bar_top);
        bar_top.setTitle("记账");
        bar_top.addRightImageButton(R.mipmap.plus, R.id.topbar_right_change_button)
               .setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           startActivity(
                                               new Intent(
                                                   _mActivity.getApplicationContext(), CalculatorFilterActivity.class));
                                       }
                                   });
        pull_to_refresh = view.findViewById(R.id.pull_to_refresh);
        pull_to_refresh.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
                                              @Override
                                              public void onMoveTarget(int offset) {}
                                              @Override
                                              public void onMoveRefreshView(int offset) {}
                                              @Override
                                              public void onRefresh() {
                                                  loadAllList();
                                              }
                                          });
        listview = view.findViewById(R.id.listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                                    long id) {
                                                CostListItem listItem =
                                                    (CostListItem) parent.getItemAtPosition(position);

                                                if (listItem != null) {
                                                    gotoCheck(listItem.getCostItem());
                                                }
                                            }
                                        });
        listview.setAdapter(mListAdapter);
        pull_to_refresh.doRefresh();
    }

    private void loadAllList() {

        // 获取数据库中数据
        List<CostItem> tempData = MoneyApplication.getDaoInstant()
                                                  .getCostItemDao()
                                                  .queryBuilder()
                                                  .orderDesc(CostItemDao.Properties.CostDate)
                                                  .list();

        mListAdapter.addAll(parseCostItem(tempData));
        pull_to_refresh.finishRefresh();
        listview.scrollTo(0, 0);
    }

    public static CostFragment newInstance() {
        Bundle       args     = new Bundle();
        CostFragment fragment = new CostFragment();

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new CostListAdapter(_mActivity);
        mListData    = new ArrayList<>();
        registerLocalReceiver(mUpdateReceiver, new IntentFilter(ReceiverUtils.RECEIVER_COST_LIST_UPDATE));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cost, container, false);

        initView(view);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegistLocalReceiver(mUpdateReceiver);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private List<CostListItem> parseCostItem(List<CostItem> items) {
        List<CostListItem> result = new ArrayList<>();
        int                count  = items.size();

        if (count > 1) {
            String                tempLastDate     = "";
            List<CostItem>        tempSectionItems = null;
            CostListItemSectioner tempSection      = null;

            for (int k = 0; k < count; k++) {
                CostItem item = items.get(k);
                String   date = item.getTempCostDate();

//              item.updateLinkEntity();
                if (!tempLastDate.equals(date)) {

                    //
                    if (tempSection != null) {
                        result.add(new CostListItem(CostListItem.ITEM_COST_SECTION, tempSection));
                        tempSection = null;
                    }

                    if (tempSectionItems != null) {
                        for (CostItem i : tempSectionItems) {
                            result.add(new CostListItem(CostListItem.ITEM_COST, i));
                        }

                        tempSectionItems = null;
                    }

                    tempLastDate     = date;
                    tempSection      = new CostListItemSectioner();
                    tempSectionItems = new ArrayList<>();
                    tempSection.setDate(tempLastDate);
                    tempSection.addTotalAmount(item.getCostAmount(), item.getCostAmountType());
                    tempSectionItems.add(item);
                } else {
                    if (tempSection != null) {
                        tempSection.addTotalAmount(item.getCostAmount(), item.getCostAmountType());
                    }

                    if (tempSectionItems != null) {
                        tempSectionItems.add(item);
                    }
                }

                if (k == count - 1) {
                    if (tempSection != null) {
                        result.add(new CostListItem(CostListItem.ITEM_COST_SECTION, tempSection));
                        tempSection = null;
                    }

                    if (tempSectionItems != null) {
                        for (CostItem i : tempSectionItems) {
                            result.add(new CostListItem(CostListItem.ITEM_COST, i));
                        }

                        tempSectionItems = null;
                    }
                }
            }
        } else {
            if (count > 0) {
                CostItem              item        = items.get(0);
                CostListItemSectioner tempSection = new CostListItemSectioner();

                tempSection.setDate(item.getTempCostDate());
                tempSection.addTotalAmount(item.getCostAmount(), item.getCostAmountType());
                result.add(new CostListItem(CostListItem.ITEM_COST_SECTION, tempSection));
                result.add(new CostListItem(CostListItem.ITEM_COST, item));
            }
        }

        LogUtils.d(GsonUtils.toJson(items));

        return result;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
