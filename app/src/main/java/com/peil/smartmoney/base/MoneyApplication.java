package com.peil.smartmoney.base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.peil.smartmoney.greendao.gen.DaoMaster;
import com.peil.smartmoney.greendao.gen.DaoSession;

import me.yokeyword.fragmentation.BuildConfig;
import me.yokeyword.fragmentation.Fragmentation;


public class MoneyApplication extends Application {
    private static DaoSession daoSession;

    public MoneyApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Fragmentation.builder()
                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG).install();

        //配置数据库
        setupDatabase();

    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库app.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "money.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}