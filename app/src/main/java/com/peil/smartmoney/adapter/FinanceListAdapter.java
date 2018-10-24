package com.peil.smartmoney.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.peil.smartmoney.R;
import com.peil.smartmoney.model.FinanceItem;

/**
 * 添加记账列表
 */
public class FinanceListAdapter extends AbsMultiTypeAdapter<FinanceItem> {
    
    public FinanceListAdapter(Context c) {
        super(c);
    }
    
    private void bindCostSectionView(ItemFinanceVholder holder , FinanceItem item) {
        holder.update(item);
    }
    
    @Override protected void onBindViewHolder(BaseViewHolder viewHolder , int position) {
        FinanceItem item = getItem(position);
        
        if (viewHolder instanceof ItemFinanceVholder) {
            bindCostSectionView((ItemFinanceVholder)viewHolder , item);
        }
    }
    
    @Override
    protected BaseViewHolder onCreateViewHolder(View view , ViewGroup parent , int viewType) {
        return new ItemFinanceVholder(view);
    }
    
    @Override protected int getLayoutResId(int position) {
        return R.layout.item_finance_list;
    }
    
    @Override public int getViewTypeCount() {
        return 2;
    }
    
    public static class ItemFinanceVholder extends BaseViewHolder {
        public TextView tv_title, tv_content;
        
        public ItemFinanceVholder(View itemView) {
            super(itemView);
            tv_title = mItemView.findViewById(R.id.tv_title);
            tv_content = mItemView.findViewById(R.id.tv_content);
        }
        
        public void update(FinanceItem item) {
            tv_title.setText(item.getTitle());
            tv_content.setText(item.getContent());
        }
    }
}


