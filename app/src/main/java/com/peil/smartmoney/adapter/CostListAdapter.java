package com.peil.smartmoney.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peil.smartmoney.R;
import com.peil.smartmoney.model.CostItem;
import com.peil.smartmoney.model.CostListItem;
import com.peil.smartmoney.model.CostListItemSectioner;
import com.peil.smartmoney.util.ResHelper;

import de.halfbit.pinnedsection.PinnedSectionListView;

/**
 * 添加记账列表
 */
public class CostListAdapter extends AbsMultiTypeAdapter<CostListItem>
        implements PinnedSectionListView.PinnedSectionListAdapter {
    public CostListAdapter(Context c) {
        super(c);
    }

    private void bindCostSectionView(ItemCostSectionVholder holder, CostListItemSectioner item) {
        holder.update(item);
    }

    private void bindCostView(ItemCostVholder holder, CostItem item) {
        holder.update(item);
    }

    @Override
    protected void onBindViewHolder(BaseViewHolder viewHolder, int position) {
        CostListItem item = getItem(position);

        if (viewHolder instanceof ItemCostSectionVholder) {
            bindCostSectionView((ItemCostSectionVholder) viewHolder, item.getCostSectionItem());
        } else if (viewHolder instanceof ItemCostVholder) {
            bindCostView((ItemCostVholder) viewHolder, item.getCostItem());
        }
    }

    @Override
    protected BaseViewHolder onCreateViewHolder(View view, ViewGroup parent, int viewType) {
        if (viewType == CostListItem.ITEM_COST_SECTION) {
            return new ItemCostSectionVholder(view);
        }

        return new ItemCostVholder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == CostListItem.ITEM_COST_SECTION;
    }

    @Override
    protected int getLayoutResId(int position) {
        if (getItemViewType(position) == CostListItem.ITEM_COST_SECTION) {
            return R.layout.item_cost_section;
        }

        return R.layout.item_cost;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    public static class ItemCostSectionVholder extends BaseViewHolder {
        public TextView tv_date, tv_total_amount;

        public ItemCostSectionVholder(View itemView) {
            super(itemView);
            tv_date = mItemView.findViewById(R.id.tv_date);
            tv_total_amount = mItemView.findViewById(R.id.tv_total_amount);
        }

        public void update(CostListItemSectioner item) {
            tv_date.setText(item.getDate());

            String totalAmount = item.getTotalAmountStr();

            if (Double.valueOf(totalAmount) > 0f) {
                totalAmount = "总计：+" + totalAmount + "元";
                tv_total_amount.setTextColor(ResHelper.getColor(mContext,R.color.text_plus));
            } else if (Double.valueOf(totalAmount) < 0f) {
                totalAmount = "总计：" + totalAmount + "元";
                tv_total_amount.setTextColor(ResHelper.getColor(mContext,R.color.text_sub));
            }

            tv_total_amount.setText(totalAmount);
        }
    }

    public static class ItemCostVholder extends BaseViewHolder {
        public TextView tv_type, tv_amount, tv_time, tv_account, tv_remark;

        public ItemCostVholder(View itemView) {
            super(itemView);
            tv_type = mItemView.findViewById(R.id.tv_type);
            tv_amount = mItemView.findViewById(R.id.tv_amount);
            tv_time = mItemView.findViewById(R.id.tv_time);
            tv_account = mItemView.findViewById(R.id.tv_account);
            tv_remark = mItemView.findViewById(R.id.tv_remark);
        }

        public void update(CostItem item) {
            tv_type.setText(item.getCostType().getCostTypeName());

            String amount = "";

            if (item.getCostAmountType().getName().equals("收入")) {
                amount = "+" + item.getCostAmount() + "元";
                tv_amount.setTextColor(ResHelper.getColor(mContext,R.color.black_666666));
            } else {
                amount = "-" + item.getCostAmount() + "元";
                tv_amount.setTextColor(ResHelper.getColor(mContext,R.color.black_666666));
            }

            tv_amount.setText(amount);
            tv_time.setText(item.getTempCostDate());
            tv_account.setText(item.getCostAccount().getCostAccountame());
            tv_remark.setText(item.getRemark());
        }
    }
}


