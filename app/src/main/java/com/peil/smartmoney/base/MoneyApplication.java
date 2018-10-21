package com.peil.smartmoney.base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import com.peil.smartmoney.greendao.gen.DaoMaster;
import com.peil.smartmoney.greendao.gen.DaoSession;
import com.peil.smartmoney.model.CostItemAccount;
import com.peil.smartmoney.model.CostItemAmountType;
import com.peil.smartmoney.model.CostItemType;
import me.yokeyword.fragmentation.BuildConfig;
import me.yokeyword.fragmentation.Fragmentation;

public class MoneyApplication extends Application {
    private static DaoSession daoSession;

    public MoneyApplication() {}

    @Override
    public void onCreate() {
        super.onCreate();
        Fragmentation.builder()

        // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
        .stackViewMode(Fragmentation.BUBBLE).debug(BuildConfig.DEBUG).install();

        // 配置数据库
        setupDatabase();

        // 插入默认的数据
        setupDbNormal();
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {

        // 创建数据库app.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "money.db", null);

        // 获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();

        // 获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);

        // 获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    /**
     * @return
     */
    private void setupDbNormal() {

        // 初始化 记账-金额类型
        CostItemAmountType amountTypeIn  = new CostItemAmountType("收入");
        CostItemAmountType amountTypeOut = new CostItemAmountType("支出");

        if (getDaoInstant().getCostItemAmountTypeDao().loadAll().size() == 0) {
            getDaoInstant().getCostItemAmountTypeDao().insertOrReplace(amountTypeIn);
            getDaoInstant().getCostItemAmountTypeDao().insertOrReplace(amountTypeOut);
        }

        // 初始化 记账-类型-支出
        if (getDaoInstant().getCostItemTypeDao().loadAll().size() == 0) {
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "吃喝", amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "交通", amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "日用品", amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "红包", amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "买菜", amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "孩子", amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "网购", amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "话费", amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "医疗", amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "娱乐", amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "化妆护肤", amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "水电煤", amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "汽车", amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "数码", amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "其他", amountTypeOut.get_id()));

            // 初始化 记账-类型-收入
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "工资", amountTypeIn.get_id()));
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "红包", amountTypeIn.get_id()));
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "兼职", amountTypeIn.get_id()));
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "投资", amountTypeIn.get_id()));
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "奖金", amountTypeIn.get_id()));
            getDaoInstant().getCostItemTypeDao().insertOrReplace(new CostItemType("", "其他", amountTypeIn.get_id()));
        }

        // 初始化 记账-账户
        if (getDaoInstant().getCostItemAccountDao().loadAll().size() == 0) {
            getDaoInstant().getCostItemAccountDao().insertOrReplace(new CostItemAccount("", "现金"));
            getDaoInstant().getCostItemAccountDao().insertOrReplace(new CostItemAccount("", "支付宝"));
            getDaoInstant().getCostItemAccountDao().insertOrReplace(new CostItemAccount("", "微信"));
        }
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
