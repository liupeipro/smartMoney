package com.peil.smartmoney.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peil.smartmoney.R;
import com.peil.smartmoney.model.CostItemType;
import com.peil.smartmoney.util.ResHelper;

/**
 * 添加记账-类型
 */
public class CostAddTypeAdapter extends AbsSignleSelectedAdapter<CostItemType> {
    public CostAddTypeAdapter(Context c) {
        super(c);
    }

    @Override
    protected void onBindViewHolder(BaseViewHolder viewHolder, int position) {
        CostAddTypeVHolder holder = (CostAddTypeVHolder) viewHolder;
        CostItemType item = getItem(position);
        holder.update(item, hasSelected(position));
    }

    @Override
    protected BaseViewHolder onCreateViewHolder(View view, ViewGroup parent, int viewType) {
        return new CostAddTypeVHolder(view);
    }

    @Override
    protected int getLayoutResId(int position) {
        return R.layout.item_cost_add_type;
    }

    public static class CostAddTypeVHolder extends BaseViewHolder {
        public TextView tv_name;

        public CostAddTypeVHolder(View itemView) {
            super(itemView);
            tv_name = mItemView.findViewById(R.id.tv_name);
        }

        public void update(CostItemType itemType, boolean hasSelected) {
            tv_name.setText(itemType.getCostTypeName());
            tv_name.setTextColor(ResHelper.getAttrColor(mContext, hasSelected ? R.color.blue_normal : R.color.tab_unselect));
        }
    }
}


