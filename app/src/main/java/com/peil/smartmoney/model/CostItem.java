package com.peil.smartmoney.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import com.peil.smartmoney.greendao.gen.DaoSession;
import com.peil.smartmoney.greendao.gen.CostItemAccountDao;
import com.peil.smartmoney.greendao.gen.CostItemTypeDao;
import com.peil.smartmoney.greendao.gen.CostItemDao;
import com.peil.smartmoney.util.TimeUtil;
import com.peil.smartmoney.greendao.gen.CostItemAmountTypeDao;

/**
 * Bean 对象注释的解释
 *
 * @Entity：告诉GreenDao该对象为实体，只有被@Entity注释的Bean类才能被dao类操作
 * @Id：对象的Id，使用Long类型作为EntityId，否则会报错。(autoincrement = true)表示主键会自增，如果false就会使用旧值
 * @Property：可以自定义字段名，注意外键不能使用该属性
 * @NotNull：属性不能为空
 * @Transient：使用该注释的属性不会被存入数据库的字段中
 * @Unique：该属性值必须在数据库中是唯一值
 * @Generated：编译后自动生成的构造函数、方法等的注释，提示构造函数、方法等不能被修改 -----
 * <p>
 * <p>
 * A、
 * @Entity用于描述实体类名，其中active表示update/delete/refresh 方法是否自动生成, 默认为false.
 * createInDb表示是否在数据库中创建表，默认为true，如果为false，将不创建该表.
 * generateConstructors表示是否自动生成构造方法(一个有参构造，一个无参构造).
 * indexes表示制定查询数据返回的默认排序规则.(@Index中的value制定排序的数据表中的列明加上排序规则即(ASC/DESC)，
 * name表示......，unique表示是否唯一即SQL中的去重复
 * 如果按照多个字段来排序可以这样(比如(indexes={@Index(value="ID ASC"),@Index(value="AGE DESC")}或者
 * indexes={@Index(value="ID ASC AGE DESC")})))
 * nameInDb表示该实体对应的数据表的名称,默认为实体名的拼音全大写
 * generateGettersSetters表示是否自动生成get/set方法，默认为true
 * B、@Id表示该字段是主键,autoincrement表示是否自增，默认为false.
 * C、@Property用于描述字段，nameInDb表示该字段在数据表中对应的列名，默认是实体字段名称.
 * D、@NotNull表示该字段不为null.
 * E、@Transient 表示在创建数据表时候忽略这个字段，也就是在创建表的时候不会创建这个字段.
 * F、@ToOne表示一对一的关系，也就是多条这个实体只对应一条标识的实体joinProperty标识这个实体表中的哪个字段和标识的实体表的主键关联.
 * G、@ToMany标识一对多的关系，也就是一条该实体数据通过指定列名和标识的数据实体的指定列名对应关系(@referencedJoinProperty表示当前标识的实体对应的数据表的
 * 主键,@joinProperties表示当前表和标识的实体对应的数据表的属性对应关系)
 * H、@Convert定义当前标识的实体和数据表中字段之间装换规则.converter表示转换器.columnType表示对应的数据表列名在表中的数据类型,
 */
@Entity
public class CostItem {

    @Id(autoincrement = true)
    private Long _id;

    //    外键
    private Long costTypeId;
    private Long costAccountId;
    private Long costAmountTypeId;
    /**
     * 金额类别：吃喝、交通。。。
     */
    @ToOne(joinProperty = "costTypeId")
    CostItemType costType = null;

    /**
     * 账户
     */
    @ToOne(joinProperty = "costAccountId")
    CostItemAccount costAccount = null;

    /**
     * 金额
     */
    @NotNull
    String costAmount = "";

    /**
     * 记录日期
     */
    @NotNull
    Long costDate = null;

    /**
     * 金额类型：收入 支出
     */
    @NotNull
    @ToOne(joinProperty = "costAmountTypeId")
    CostItemAmountType costAmountType = null;

    /**
     * 创建时间
     */
    @NotNull
    Long createTime = null;

    /**
     * 最后修改时间
     */
    @NotNull
    Long lastModifyTime = null;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 335225705)
    private transient CostItemDao myDao;


    @Generated(hash = 531894926)
    public CostItem(Long _id, Long costTypeId, Long costAccountId, Long costAmountTypeId,
                    @NotNull String costAmount, @NotNull Long costDate, @NotNull Long createTime,
                    @NotNull Long lastModifyTime) {
        this._id = _id;
        this.costTypeId = costTypeId;
        this.costAccountId = costAccountId;
        this.costAmountTypeId = costAmountTypeId;
        this.costAmount = costAmount;
        this.costDate = costDate;
        this.createTime = createTime;
        this.lastModifyTime = lastModifyTime;
    }

    @Generated(hash = 716453317)
    public CostItem() {
    }

    @Generated(hash = 1431160105)
    private transient Long costType__resolvedKey;

    @Generated(hash = 2136258578)
    private transient Long costAccount__resolvedKey;

    @Generated(hash = 1257445021)
    private transient Long costAmountType__resolvedKey;


    /**
     * @param date
     */
    public void setTempCostDate(String date) {
        setCostDate(TimeUtil.str2Millis(date, "yyyy-MM-dd"));
    }

    public String getTempCostDate() {
        String result = "";
        result = TimeUtil.millis2Str(getCostDate(), "yyyy-MM-dd");
        return result;
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

    public Long getCostAmountTypeId() {
        return this.costAmountTypeId;
    }

    public void setCostAmountTypeId(Long costAmountTypeId) {
        this.costAmountTypeId = costAmountTypeId;
    }

    public String getCostAmount() {
        return this.costAmount;
    }

    public void setCostAmount(String costAmount) {
        this.costAmount = costAmount;
    }

    public Long getCostDate() {
        return this.costDate;
    }

    public void setCostDate(Long costDate) {
        this.costDate = costDate;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getLastModifyTime() {
        return this.lastModifyTime;
    }

    public void setLastModifyTime(Long lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 2028115332)
    public CostItemType getCostType() {
        Long __key = this.costTypeId;
        if (costType__resolvedKey == null || !costType__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CostItemTypeDao targetDao = daoSession.getCostItemTypeDao();
            CostItemType costTypeNew = targetDao.load(__key);
            synchronized (this) {
                costType = costTypeNew;
                costType__resolvedKey = __key;
            }
        }
        return costType;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 701205377)
    public void setCostType(CostItemType costType) {
        synchronized (this) {
            this.costType = costType;
            costTypeId = costType == null ? null : costType.get_id();
            costType__resolvedKey = costTypeId;
        }
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 665416236)
    public CostItemAccount getCostAccount() {
        Long __key = this.costAccountId;
        if (costAccount__resolvedKey == null || !costAccount__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CostItemAccountDao targetDao = daoSession.getCostItemAccountDao();
            CostItemAccount costAccountNew = targetDao.load(__key);
            synchronized (this) {
                costAccount = costAccountNew;
                costAccount__resolvedKey = __key;
            }
        }
        return costAccount;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 648860675)
    public void setCostAccount(CostItemAccount costAccount) {
        synchronized (this) {
            this.costAccount = costAccount;
            costAccountId = costAccount == null ? null : costAccount.get_id();
            costAccount__resolvedKey = costAccountId;
        }
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 1336909163)
    public CostItemAmountType getCostAmountType() {
        Long __key = this.costAmountTypeId;
        if (costAmountType__resolvedKey == null
                || !costAmountType__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CostItemAmountTypeDao targetDao = daoSession.getCostItemAmountTypeDao();
            CostItemAmountType costAmountTypeNew = targetDao.load(__key);
            synchronized (this) {
                costAmountType = costAmountTypeNew;
                costAmountType__resolvedKey = __key;
            }
        }
        return costAmountType;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 448877316)
    public void setCostAmountType(CostItemAmountType costAmountType) {
        synchronized (this) {
            this.costAmountType = costAmountType;
            costAmountTypeId = costAmountType == null ? null : costAmountType.get_id();
            costAmountType__resolvedKey = costAmountTypeId;
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

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1401460910)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCostItemDao() : null;
    }


}
