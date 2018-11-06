package com.peil.smartmoney.model;

public class CostListItem {
    public static final int ITEM_COST = 1;
    public static final int ITEM_COST_SECTION = 2;
    private Integer type = ITEM_COST_SECTION;
    private CostItem costItem;
    private CostListItemSectioner costSectionItem;

    public CostListItem() {
    }

    public CostListItem(Integer type, CostItem costItem) {
        this.type = type;
        this.costItem = costItem;
    }

    public CostListItem(Integer type, CostListItemSectioner costSectionItem) {
        this.type = type;
        this.costSectionItem = costSectionItem;
    }

    public CostListItem(Integer type, CostItem costItem, CostListItemSectioner costSectionItem) {
        this.type = type;
        this.costItem = costItem;
        this.costSectionItem = costSectionItem;
        setTypeItem();
    }

    public CostItem getCostItem() {
        return costItem;
    }

    public void setCostItem(CostItem costItem) {
        this.costItem = costItem;
    }

    public CostListItemSectioner getCostSectionItem() {
        return costSectionItem;
    }

    public void setCostSectionItem(CostListItemSectioner costSectionItem) {
        this.costSectionItem = costSectionItem;
    }

    public Integer getType() {
        return type;
    }

    public void setTypeItem() {
        type = ITEM_COST;
    }

    public void setTypeSection() {
        type = ITEM_COST_SECTION;
    }
}


