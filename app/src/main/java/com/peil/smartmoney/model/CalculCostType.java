package com.peil.smartmoney.model;

import android.support.annotation.NonNull;

import com.peil.smartmoney.util.FloatUtils;

import java.text.DecimalFormat;
import java.util.List;

public class CalculCostType implements Comparable<CalculCostType> {

    CostItemType costType;
    Double totalAmount = 0d;
    Double scale = 0d;
    Integer num = 0;
    List<CostItem> costItems;

    public CalculCostType() {
    }

    public void updateAmount() {
        if (costItems != null && !costItems.isEmpty()) {
            for (CostItem item : costItems) {
                totalAmount = FloatUtils.add(totalAmount, Double.valueOf(item.getCostAmount()));
            }

            setNum(costItems.size());
        }
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public String getTotalAmountStr() {
        return String.valueOf(totalAmount);
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getScale() {
        return scale;
    }

    public String getScaleStr() {
        //return String.valueOf(scale);
        return new DecimalFormat("0.00").format(scale);
    }

    public String getNumStr() {
        return String.valueOf(num);
    }

    public void setScale(Double scale) {
        //使用0.00不足位补0，#.##仅保留有效位
        this.scale = scale;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List<CostItem> getCostItems() {
        return costItems;
    }

    public void setCostItems(List<CostItem> costItems) {
        this.costItems = costItems;
    }

    public CostItemType getCostType() {
        return costType;
    }

    public void setCostType(CostItemType costType) {
        this.costType = costType;
    }

    @Override
    public int compareTo(@NonNull CalculCostType o) {
        //按钮比例大小排序，大的放前面
        if (this.getScale() > o.getScale()) {
            return -1;
        }
        return 1;
    }
}
