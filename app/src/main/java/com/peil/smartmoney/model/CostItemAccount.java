package com.peil.smartmoney.model;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 账户
 */
@Entity public class CostItemAccount implements Serializable {
    private static final long serialVersionUID = -7576337145879831388L;
    @Id(autoincrement = true) private Long _id;
    public String costAccountIcon;
    @NotNull @Unique public String costAccountame;
    
    @Generated(hash = 719143901) public CostItemAccount() {
    }
    
    public CostItemAccount(String icon, String name) {
        this.costAccountIcon = icon;
        this.costAccountame = name;
    }
    
    @Generated(hash = 925208979)
    public CostItemAccount(Long _id, String costAccountIcon, @NotNull String costAccountame) {
        this._id = _id;
        this.costAccountIcon = costAccountIcon;
        this.costAccountame = costAccountame;
    }
    
    public Long get_id() {
        return this._id;
    }
    
    public void set_id(Long _id) {
        this._id = _id;
    }
    
    public String getCostAccountIcon() {
        return this.costAccountIcon;
    }
    
    public void setCostAccountIcon(String costAccountIcon) {
        this.costAccountIcon = costAccountIcon;
    }
    
    public String getCostAccountame() {
        return this.costAccountame;
    }
    
    public void setCostAccountame(String costAccountame) {
        this.costAccountame = costAccountame;
    }
}

