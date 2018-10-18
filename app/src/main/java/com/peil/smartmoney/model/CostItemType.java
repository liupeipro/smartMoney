package com.peil.smartmoney.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import com.peil.smartmoney.greendao.gen.DaoSession;
import com.peil.smartmoney.greendao.gen.CostItemAmountTypeDao;
import com.peil.smartmoney.greendao.gen.CostItemTypeDao;

/**
 * 类别
 */
@Entity
public class CostItemType {

    @Id(autoincrement = true)
    private Long _id;

    private String costTypeIcon;

    @NotNull
    private String costTypeName;

    private Long amountTypeId;

    @ToOne(joinProperty = "amountTypeId")
    @NotNull
    private CostItemAmountType amountType;

    public CostItemType(String costTypeIcon, String costTypeName, CostItemAmountType amountType) {
        this.costTypeIcon = costTypeIcon;
        this.costTypeName = costTypeName;
        this.amountType = amountType;
    }

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1707162959)
    private transient CostItemTypeDao myDao;

    @Generated(hash = 67926559)
    public CostItemType(Long _id, String costTypeIcon, @NotNull String costTypeName,
            Long amountTypeId) {
        this._id = _id;
        this.costTypeIcon = costTypeIcon;
        this.costTypeName = costTypeName;
        this.amountTypeId = amountTypeId;
    }

    @Generated(hash = 956239630)
    public CostItemType() {
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

    @Generated(hash = 789450003)
    private transient Long amountType__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 880249269)
    public CostItemAmountType getAmountType() {
        Long __key = this.amountTypeId;
        if (amountType__resolvedKey == null || !amountType__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CostItemAmountTypeDao targetDao = daoSession.getCostItemAmountTypeDao();
            CostItemAmountType amountTypeNew = targetDao.load(__key);
            synchronized (this) {
                amountType = amountTypeNew;
                amountType__resolvedKey = __key;
            }
        }
        return amountType;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 461477587)
    public void setAmountType(CostItemAmountType amountType) {
        synchronized (this) {
            this.amountType = amountType;
            amountTypeId = amountType == null ? null : amountType.get_id();
            amountType__resolvedKey = amountTypeId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 518208986)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCostItemTypeDao() : null;
    }

    public Long getAmountTypeId() {
        return this.amountTypeId;
    }

    public void setAmountTypeId(Long amountTypeId) {
        this.amountTypeId = amountTypeId;
    }
}
