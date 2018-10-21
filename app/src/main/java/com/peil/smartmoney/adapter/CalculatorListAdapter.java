package com.peil.smartmoney.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.peil.smartmoney.R;
import com.peil.smartmoney.model.CalculatorListItem;
import com.peil.smartmoney.model.CostItemAmountType;

/**
 * 添加记账列表
 */
public class CalculatorListAdapter extends AbsMultiTypeAdapter<CalculatorListItem> {
    private CostItemAmountType contItemType = null;

    public CalculatorListAdapter(Context c) {
        super(c);
    }

    private void bindCostSectionView(ItemCalculatorVholder holder, CalculatorListItem item) {

//      holder.update(item);
    }

    @Override
    protected void onBindViewHolder(BaseViewHolder viewHolder, int position) {
        CalculatorListItem item = getItem(position);

        if (viewHolder instanceof ItemCalculatorVholder) {
            bindCostSectionView((ItemCalculatorVholder) viewHolder, item);
        }
    }

    @Override
    protected BaseViewHolder onCreateViewHolder(View view, ViewGroup parent, int viewType) {
        return new ItemCalculatorVholder(view);
    }

    public CostItemAmountType getContItemAmountType() {
        return contItemType;
    }

    public void setContItemAmountType(CostItemAmountType contItemType) {
        this.contItemType = contItemType;
    }

    @Override
    protected int getLayoutResId(int position) {
        return R.layout.item_calculator_list;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public static class ItemCalculatorVholder extends BaseViewHolder {
        public TextView tv_type, tv_scale, tv_total_amount;

        public ItemCalculatorVholder(View itemView) {
            super(itemView);
            tv_type         = mItemView.findViewById(R.id.tv_type);
            tv_scale        = mItemView.findViewById(R.id.tv_scale);
            tv_total_amount = mItemView.findViewById(R.id.tv_total_amount);
        }

        public void update(CalculatorListItem item) {

//          tv_type.setText(item.getDate());
//          String totalAmount = item.getTotalAmountStr();
//          if (Double.valueOf(totalAmount) > 0f) {
//              totalAmount = "+" + totalAmount + "元";
//              tv_total_amount.setTextColor(mContext.getColor(R.color.text_plus));
//          } else if (Double.valueOf(totalAmount) < 0f) {
//              totalAmount = "" + totalAmount + "元";
//              tv_total_amount.setTextColor(mContext.getColor(R.color.text_sub));
//          }
//          tv_total_amount.setText(item.getTotalAmount());
//          tv_scale.setText(item.getScale());
//          tv_total_amount.setText(item.getItemType().getCostTypeName());
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
