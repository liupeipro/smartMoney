package com.peil.smartmoney.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peil.smartmoney.R;
import com.peil.smartmoney.model.CostItemType;

/**
 * 添加记账-类型
 */
public class CoastAddTypeAdapter extends AbsSelectedAdaspter<CostItemType> {

    public static class CostAddTypeVHolder extends BaseViewHolder {
        public TextView tv_name;

        public CostAddTypeVHolder(View itemView) {
            super(itemView);
            tv_name = mItemView.findViewById(R.id.tv_name);
        }

        public void update(CostItemType itemType, boolean hasSelected) {
            tv_name.setText(itemType.getCostTypeName());

            mItemView.setBackgroundColor(mContext.getColor(hasSelected ? R.color.qmui_config_color_white : R.color.qmui_config_color_red));
        }

    }

    public CoastAddTypeAdapter(Context c) {
        super(c);
    }

    @Override
    protected int getLayoutResId(int position) {
        return R.layout.item_cost_add_type;
    }

    @Override
    protected BaseViewHolder onCreateViewHolder(View view, ViewGroup parent, int viewType) {
        return new CostAddTypeVHolder(view);
    }

    @Override
    protected void onBindViewHolder(BaseViewHolder viewHolder, int position) {
        CostAddTypeVHolder holder = (CostAddTypeVHolder) viewHolder;
        CostItemType item = getItem(position);
        holder.tv_name.setText(item.getCostTypeName());
    }

}
