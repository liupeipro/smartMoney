package com.peil.smartmoney.base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import com.peil.smartmoney.greendao.gen.DaoMaster;
import com.peil.smartmoney.greendao.gen.DaoSession;
import com.peil.smartmoney.model.CostItemAccount;
import com.peil.smartmoney.model.CostItemAmountType;
import com.peil.smartmoney.model.CostItemType;
import com.peil.smartmoney.model.FinanceItem;
import me.yokeyword.fragmentation.BuildConfig;
import me.yokeyword.fragmentation.Fragmentation;

public class MoneyApplication extends Application {
    private static DaoSession daoSession;
    
    public MoneyApplication() {
    }
    
    @Override public void onCreate() {
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
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this , "money.db" , null);
        
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
        CostItemAmountType amountTypeIn = new CostItemAmountType("收入");
        CostItemAmountType amountTypeOut = new CostItemAmountType("支出");
        
        if (getDaoInstant().getCostItemAmountTypeDao().loadAll().size() == 0) {
            getDaoInstant().getCostItemAmountTypeDao().insertOrReplace(amountTypeIn);
            getDaoInstant().getCostItemAmountTypeDao().insertOrReplace(amountTypeOut);
        }
        
        // 初始化 记账-类型-支出
        if (getDaoInstant().getCostItemTypeDao().loadAll().size() == 0) {
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "吃喝" , amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "交通" , amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "日用品" , amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "红包" , amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "买菜" , amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "孩子" , amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "网购" , amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "话费" , amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "医疗" , amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "娱乐" , amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "化妆护肤" , amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "水电煤" , amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "汽车" , amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "数码" , amountTypeOut.get_id()));
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "其他" , amountTypeOut.get_id()));
            
            // 初始化 记账-类型-收入
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "工资" , amountTypeIn.get_id()));
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "红包" , amountTypeIn.get_id()));
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "兼职" , amountTypeIn.get_id()));
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "投资" , amountTypeIn.get_id()));
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "奖金" , amountTypeIn.get_id()));
            getDaoInstant().getCostItemTypeDao()
                           .insertOrReplace(new CostItemType("" , "其他" , amountTypeIn.get_id()));
        }
        
        // 初始化 记账-账户
        if (getDaoInstant().getCostItemAccountDao().loadAll().size() == 0) {
            getDaoInstant().getCostItemAccountDao().insertOrReplace(new CostItemAccount("" , "现金"));
            getDaoInstant().getCostItemAccountDao()
                           .insertOrReplace(new CostItemAccount("" , "支付宝"));
            getDaoInstant().getCostItemAccountDao().insertOrReplace(new CostItemAccount("" , "微信"));
        }
        
        addFinanceNormal();
    }
    
    private void addFinanceNormal() {
        if (getDaoInstant().getFinanceItemDao().loadAll().size() == 0) {
            getDaoInstant().getFinanceItemDao()
                           .insertOrReplace(new FinanceItem("最传统渠道：储蓄" ,
                                                            "这是普通家庭采取的传统做法，所占比重最高。储蓄投资安全可靠，能够赚取利息，但其回报率低，还需缴利息税。同时，存款利息无法弥补通货膨胀所带来的资金贬值"));
            getDaoInstant().getFinanceItemDao()
                           .insertOrReplace(new FinanceItem("保障性投资：保险" ,
                                                            "保险作为一种纯消费型风险保障工具，只要通过科学的保险计划，就能充分发挥资金的投资价值，又能为家人提供一份充足的保障。"));
            getDaoInstant().getFinanceItemDao()
                           .insertOrReplace(new FinanceItem("稳健性理财：P2P网贷" ,
                                                            "P2P网贷理财不同于股票、期货等其他金融产品，不需要太过专业的金融知识，而且也无需花费你过多的精力，只要及时掌握行业动态和平台动态，就可以快速上手。此外，相比与目前市场上同类固收理财产品来看，P2P网贷是为数不多的收益较高的产品。不过P2P网贷投资也要注重风险识别，要做到安全投资关键是要找到一家安全合规的网贷平台。"));
            getDaoInstant().getFinanceItemDao()
                           .insertOrReplace(new FinanceItem("合理的投资理财组合" ,
                                                            "投资“一分法”——适合于贫困家庭。选择现金、储蓄和小额固收理财产品作为投资工具。\n"
                                                                + "\n"
                                                                + "投资“二分法”——低收入者。现金、储蓄为主，再适当考虑购入如P2P、银行理财产品等。\n"
                                                                + "\n"
                                                                + "投资“三分法”——适合于收入不高但稳定者。可选择40%的现金及储蓄，40%的房地产，20%的低风险投资产品。\n"
                                                                + "\n"
                                                                + "投资“四分法”——适合于收入较高，但风险意识较弱、缺乏专门知识与业余时间者。其投资组合为：30%的现金、储蓄，35%的房地产，5%的保险，20%的稳健型投资。\n"
                                                                + "\n"
                                                                + "投资“五分法”——适合于财力雄厚者。其投资比例为：现金、储蓄或债券30%，房地产25%，保险5%，稳健型投资基金20%，高风险投资20%。"));
            getDaoInstant().getFinanceItemDao()
                           .insertOrReplace(new FinanceItem("个人理财八大原则" , "量入为出原则——保证基本生活，余钱投资。\n"
                               + "\n"
                               + "经济效益原则——绝对值：利润=收入-成本;相对值：投资收益率=利润/投资额×100%\n"
                               + "\n"
                               + "安全性原则——组合投资，分散风险，不要把全部鸡蛋放在同一个篮子里;也不要把全部篮子挑在一个肩膀上。\n"
                               + "\n"
                               + "变现原则——天有不测风云。\n"
                               + "\n"
                               + "因人制宜原则——环境、个性、偏好、年龄、职业、经历等。\n"
                               + "\n"
                               + "终生理财原则——一个人一生不同时期理财的需求不一样，因此必须考虑阶段性和延续性。\n"
                               + "\n"
                               + "快乐理财原则——投资理财的目的是为了生活得更美好，保持快乐的心情和健康的身体。\n"
                               + "\n"
                               + "提高素质原则——增强理财管理能力、资金运筹能力、风险投资意识，充实经济金融知识"));
            getDaoInstant().getFinanceItemDao()
                           .insertOrReplace(new FinanceItem("股市如何看K线图 K线图怎么看？" ,
                                                            "K线最上方的一条细线称为上影线，中间的一条粗线为实体。下面的一条细线为下影线。当收盘价高于开盘价，也就是股价走势呈上升趋势时，我们称这种情况下的K线为阳线，中部的实体以空白或红色表示。这时，上影线的长度表示最高价和收盘价之间的价差，实体的长短代表收盘价与开盘价之间的价差，下影线的长度则代表开盘价和最低价之间的差距。当收盘价低于开盘价，也就是股价走势呈下降趋势时，我们称这种情况下的K线为阴线。中部的实体为绿色或黑色。\n"));
            getDaoInstant().getFinanceItemDao()
                           .insertOrReplace(new FinanceItem("股票市净率高低有什么影响？市净率的计算方法\n" ,
                                                            "市净率指的是市价与每股净资产之间的比值，如果一支股票市价是10，净资产为5，则它的市净率就是2，如果一支股票同样市价是10，净资产为2的话，那他的市净率就是5.所以。市净率越低，我们承担的风险就越低。理论上：市净率越低，被低估，越有涨的可能\n"
                                                                + "但是，对市净率我们要动态地看，因为会计制度的不同往往使得净资产与境外企业的概念存在着一定的差别。更为重要的是，净资产仅仅是企业静态的资产概念，存在着一定的变数。去年盈利会增加每股净资产，但如果今年亏损就会减少每股净资产。如海南航空在2002年每股净资产是3.26元，到了2003年因为每股亏损1.74元，就变成了1.53元，跌幅超过50%。\t\n"
                                                                + "需要注意的是，市净率是高好还是低，对短线操作影响不大，因为，市净率适合长线投资时候分析。所以，朋友们不要搞混了。\n"
                                                                + "市净率计算公式\n"
                                                                + "市净率的计算方法是：市净率=(P/BV)即：股票市价(P)/每股净资产(book value)\n"
                                                                + "股票市净率在多少范围比较合理\n"
                                                                + "市净率多少合适?市净率=市价/净资产;净资产收益率=每股收益/净资产;市价收益率=每股收益/市价\n"
                                                                + "如果净资产收益为10%(中石化的水平）\n"
                                                                + "当市净率=1时，市价收益率=10%<\n"
                                                                + "当市净率=2时，市价收益率=5%\n"
                                                                + "当市净率=3时，市价收益率=3.3%\n"
                                                                + "因此，当市净率超过2倍时，千万别说有投资价值。当然，市净率低于2倍时，也未必有投资价值。"));
            getDaoInstant().getFinanceItemDao()
                           .insertOrReplace(new FinanceItem("选股票原则是什么?" ,
                                                            "适合定投的股票，一定是具备长线投资价值的股票。例如估值适中的高成长股，或者是低估值的蓝筹股。高成长股就意味着高估值，一般就算是熊市，估值也不会很低，如果要等到低估值再去买，怕是买不到。蓝筹股企业已经成熟，估值就不会给的很高，所以要等到绝对低估值才能买入。定投不是趋势投资，对买入的具体点位要求不高，不需要等到上涨趋势确认，只需要相对低估值就可以了。\n"));
            getDaoInstant().getFinanceItemDao()
                           .insertOrReplace(new FinanceItem("每股收益计算公式 股票EPS是什么意思？" ,
                                                            "EPS是指每股收益。每股收益又称每股税后利润、每股盈余，是分析每股价值的一个基础性指标。传统的每股收益指标计算公式为：每股收益=期末净利润期末总股本。\n"
                                                                + "每股收益突出了分摊到每一份股票上的盈利数额，是股票市场上按市盈率定价的基础。如果一家公司的净利润很大，但每股盈利却很小，表明它的业绩被过分稀释，每股价格通常不高\n"
                                                                + "市盈率公式：市盈率=P/EPS。（P：股票价格;EPS：每股收益）"));
            getDaoInstant().getFinanceItemDao()
                           .insertOrReplace(new FinanceItem("K线图中的三条线代表什么？" ,
                                                            "K线图中的三条线是移动平均线，也就是通常说的“均线”。其中，交易软件中默认的黄色为30日均线，白色为10日均线，紫色为60日均线\n"
                                                                + "一般股票分析软件不同，其颜色也会不同，没有统一的规定，你可以根据你所使用的行情软件来分辨。具体是在K线图上，看日K线图上方与工具条下方之间的一排日K线数字，如 MA5、MA10、MA30所对应的颜色，分别代表 5日、10日、30日、60日、120日、250日均线。\n"));
            getDaoInstant().getFinanceItemDao()
                           .insertOrReplace(new FinanceItem("波浪理论是什么? 2018股票波浪理论攻略" ,
                                                            "波浪理论又称艾略特波段理论。是由美国证券分析家拉尔夫.纳尔逊.艾略特利用道琼斯工业指数平均作为研究工具，发现不断变化的股价结构性形态的呈现自然和谐。\n"
                                                                + "波浪理论的基本观点：\n"
                                                                + "1.股价指数的上升和下跌将是交替进行的。推动浪和调整浪是价格波动的两种最基本的方式\n"
                                                                + "2.推动浪由5个上升浪组成，即五浪上升模式模式。在市场中价格以一种特定的五浪形态，其中1、3、5浪是上升浪，2浪和4浪则是对1、3浪的逆向调整。\n"
                                                                + "3.调整浪由A、B、C三浪组成，即三浪调整模式。五浪上升运行完毕后将有A、B、C三浪对五浪上升进行调整，其中A浪和C浪是下跌浪。B浪是反弹浪\n"
                                                                + "4.一个完整的循环由五个上升浪和三个调整浪组成，即所谓的八浪循环。\n"
                                                                + "5.第一浪有两种表现形式，一种属于构筑底部，另一种则为上升形态;第二浪有时调整幅度较大，跌幅惊人;第三浪通常最具爆发力，是运行时间及幅度最长的一个浪;第四浪经常以较为复杂的形态出现，以三角形调整形态的情况居多。如二浪是简单浪，则四浪以复杂浪居多;如二浪是复杂浪，则四浪以简单浪居多。四浪不应低于第一浪的顶。第五浪是上升中的最后一浪，力度大小不一。\n"
                                                                + "6.A浪对五浪上升进行调整，下跌力度大小不一;B浪是修复A浪下跌的反弹浪，升势较不稳定，C浪下跌的时间长、幅度大，最具杀伤力。 可以买本波浪理论去学习一下，平时多学习，多去模拟操作，总结经验\n"));
        }
    }
    
    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}


