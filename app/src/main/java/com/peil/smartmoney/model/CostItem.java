package com.peil.smartmoney.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.peil.smartmoney.greendao.gen.DaoSession;
import com.peil.smartmoney.greendao.gen.CostAddItemAccountDao;
import com.peil.smartmoney.greendao.gen.CostAddItemTypeDao;
import com.peil.smartmoney.greendao.gen.CostItemDao;

/**
 * Bean 对象注释的解释
 *
 * @Entity：告诉GreenDao该对象为实体，只有被@Entity注释的Bean类才能被dao类操作
 * @Id：对象的Id，使用Long类型作为EntityId，否则会报错。(autoincrement = true)表示主键会自增，如果false就会使用旧值
 * @Property：可以自定义字段名，注意外键不能使用该属性
 * @NotNull：属性不能为空
 * @Transient：使用该注释的属性不会被存入数据库的字段中
 * @Unique：该属性值必须在数据库中是唯一值
 * @Generated：编译后自动生成的构造函数、方法等的注释，提示构造函数、方法等不能被修改
 */
@Entity
public class CostItem {

    @Id(autoincrement = true)
    private Long _id;

//    外键
    private Long costTypeId;
    private Long costAccountId;
    /**
     * 金额类别：吃喝、交通。。。
     */
    @ToOne(joinProperty = "costTypeId")
    CostAddItemType costType;
    /**
     * 账户
     */
    @ToOne(joinProperty = "costAccountId")
    CostAddItemAccount costAccount;
    /**
     * 金额
     */
    @NotNull
    String costAmount;
    /**
     * 记录日期
     */
    @NotNull
    String costDate;

    /**
     * 金额类型：收入 支出
     */
    @NotNull
    String costAmountType;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 335225705)
    private transient CostItemDao myDao;

    @Generated(hash = 154207525)
    public CostItem(Long _id, Long costTypeId, Long costAccountId,
            @NotNull String costAmount, @NotNull String costDate,
            @NotNull String costAmountType) {
        this._id = _id;
        this.costTypeId = costTypeId;
        this.costAccountId = costAccountId;
        this.costAmount = costAmount;
        this.costDate = costDate;
        this.costAmountType = costAmountType;
    }

    @Generated(hash = 716453317)
    public CostItem() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Long getCostTypeId() {
        return this.costTypeId;
    }

    public void setCostTypeId(Long costTypeId) {
        this.costTypeId = costTypeId;
    }

    public Long getCostAccountId() {
        return this.costAccountId;
    }

    public void setCostAccountId(Long costAccountId) {
        this.costAccountId = costAccountId;
    }

    public String getCostAmount() {
        return this.costAmount;
    }

    public void setCostAmount(String costAmount) {
        this.costAmount = costAmount;
    }

    public String getCostDate() {
        return this.costDate;
    }

    public void setCostDate(String costDate) {
        this.costDate = costDate;
    }

    public String getCostAmountType() {
        return this.costAmountType;
    }

    public void setCostAmountType(String costAmountType) {
        this.costAmountType = costAmountType;
    }

    @Generated(hash = 1431160105)
    private transient Long costType__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1516743311)
    public CostAddItemType getCostType() {
        Long __key = this.costTypeId;
        if (costType__resolvedKey == null || !costType__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CostAddItemTypeDao targetDao = daoSession.getCostAddItemTypeDao();
            CostAddItemType costTypeNew = targetDao.load(__key);
            synchronized (this) {
                costType = costTypeNew;
                costType__resolvedKey = __key;
            }
        }
        return costType;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1415231366)
    public void setCostType(CostAddItemType costType) {
        synchronized (this) {
            this.costType = costType;
            costTypeId = costType == null ? null : costType.get_id();
            costType__resolvedKey = costTypeId;
        }
    }

    @Generated(hash = 2136258578)
    private transient Long costAccount__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 748337763)
    public CostAddItemAccount getCostAccount() {
        Long __key = this.costAccountId;
        if (costAccount__resolvedKey == null
                || !costAccount__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CostAddItemAccountDao targetDao = daoSession.getCostAddItemAccountDao();
            CostAddItemAccount costAccountNew = targetDao.load(__key);
            synchronized (this) {
                costAccount = costAccountNew;
                costAccount__resolvedKey = __key;
            }
        }
        return costAccount;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 960382833)
    public void setCostAccount(CostAddItemAccount costAccount) {
        synchronized (this) {
            this.costAccount = costAccount;
            costAccountId = costAccount == null ? null : costAccount.get_id();
            costAccount__resolvedKey = costAccountId;
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
    @Generated(hash = 1401460910)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCostItemDao() : null;
    }


}
