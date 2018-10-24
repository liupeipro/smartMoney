package com.peil.smartmoney.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.peil.smartmoney.R;
import com.peil.smartmoney.model.CalculCostType;

/**
 * 添加记账列表
 */
public class CalculatorListAdapter extends AbsMultiTypeAdapter<CalculCostType> {
    
    public CalculatorListAdapter(Context c) {
        super(c);
    }
    
    private void bindCostSectionView(ItemCalculatorVholder holder , CalculCostType item) {
        holder.update(item);
    }
    
    @Override protected void onBindViewHolder(BaseViewHolder viewHolder , int position) {
        CalculCostType item = getItem(position);
        
        if (viewHolder instanceof ItemCalculatorVholder) {
            bindCostSectionView((ItemCalculatorVholder)viewHolder , item);
        }
    }
    
    @Override
    protected BaseViewHolder onCreateViewHolder(View view , ViewGroup parent , int viewType) {
        return new ItemCalculatorVholder(view);
    }
    
    @Override protected int getLayoutResId(int position) {
        return R.layout.item_calculator_list;
    }
    
    @Override public int getViewTypeCount() {
        return 2;
    }
    
    public static class ItemCalculatorVholder extends BaseViewHolder {
        public TextView tv_type_name, tv_scale, tv_total_amount, tv_num;
        
        public ItemCalculatorVholder(View itemView) {
            super(itemView);
            tv_type_name = mItemView.findViewById(R.id.tv_type_name);
            tv_scale = mItemView.findViewById(R.id.tv_scale);
            tv_total_amount = mItemView.findViewById(R.id.tv_total_amount);
            tv_num = mItemView.findViewById(R.id.tv_num);
        }
        
        public void update(CalculCostType item) {
            
            tv_type_name.setText(item.getCostType().getCostTypeName());
            String totalAmount = item.getTotalAmountStr();
            if (Double.valueOf(totalAmount) > 0f) {
                totalAmount = "+" + totalAmount + "元";
                tv_total_amount.setTextColor(mContext.getColor(R.color.text_plus));
            } else if (Double.valueOf(totalAmount) < 0f) {
                totalAmount = "" + totalAmount + "元";
                tv_total_amount.setTextColor(mContext.getColor(R.color.text_sub));
            }
            tv_total_amount.setText(totalAmount);
            tv_num.setText(item.getNumStr() + " 笔");
            tv_scale.setText(item.getScaleStr() + " %");
        }
    }
}


