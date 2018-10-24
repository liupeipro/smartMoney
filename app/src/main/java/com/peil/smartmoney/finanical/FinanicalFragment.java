package com.peil.smartmoney.finanical;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.peil.smartmoney.R;
import com.peil.smartmoney.adapter.FinanceListAdapter;
import com.peil.smartmoney.base.BaseFragment;
import com.peil.smartmoney.base.MoneyApplication;
import com.peil.smartmoney.model.FinanceItem;
import com.peil.smartmoney.util.MoneyConstants;
import com.peil.smartmoney.view.RefreshLayout;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import java.util.List;

public class FinanicalFragment extends BaseFragment {
    private QMUITopBarLayout bar_top;
    private RefreshLayout pull_to_refresh;
    ListView mListView;
    FinanceListAdapter mListAdapter;
    
    public static FinanicalFragment newInstance() {
        Bundle args = new Bundle();
        FinanicalFragment fragment = new FinanicalFragment();
        
        fragment.setArguments(args);
        
        return fragment;
    }
    
    private AdapterView.OnItemClickListener mOnItemClickener =
        new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent , View view , int position , long id) {
                FinanceItem listItem = (FinanceItem)parent.getItemAtPosition(position);
                if (listItem != null) {
                    Intent intent = new Intent(_mActivity , FinanceDetailActivity.class);
                    intent.putExtra(MoneyConstants.INTENT_FINANCE_ID , listItem.getId());
                    startActivity(intent);
                }
            }
        };
    
    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater , @Nullable ViewGroup container ,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_finanical , container , false);
        
        initView(view);
        
        return view;
    }
    
    private void initView(View view) {
        bar_top = view.findViewById(R.id.bar_top);
        bar_top.setTitle("理财");
        
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
        
        mListView = view.findViewById(R.id.listview);
        mListView.setOnItemClickListener(mOnItemClickener);
        mListAdapter = new FinanceListAdapter(_mActivity);
        mListView.setAdapter(mListAdapter);
        pull_to_refresh.doRefresh();
    }
    
    private void updateDataAll() {
        List<FinanceItem> items = MoneyApplication.getDaoInstant().getFinanceItemDao().loadAll();
        mListAdapter.addAll(items);
        pull_to_refresh.finishRefresh();
    }
}


