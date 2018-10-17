package com.peil.smartmoney.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * 类别
 */
@Entity
public class CostAddItemType {

    @Id(autoincrement = true)
    private Long _id;

    private String costTypeIcon;

    @NotNull
    private String costTypeName;

    public CostAddItemType(String icon, String name) {
        this.costTypeIcon = icon;
        this.costTypeName = name;
    }

    @Generated(hash = 223109892)
    public CostAddItemType(Long _id, String costTypeIcon,
            @NotNull String costTypeName) {
        this._id = _id;
        this.costTypeIcon = costTypeIcon;
        this.costTypeName = costTypeName;
    }

    @Generated(hash = 1542308652)
    public CostAddItemType() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getCostTypeIcon() {
        return this.costTypeIcon;
    }

    public void setCostTypeIcon(String costTypeIcon) {
        this.costTypeIcon = costTypeIcon;
    }

    public String getCostTypeName() {
        return this.costTypeName;
    }

    public void setCostTypeName(String costTypeName) {
        this.costTypeName = costTypeName;
    }

}
