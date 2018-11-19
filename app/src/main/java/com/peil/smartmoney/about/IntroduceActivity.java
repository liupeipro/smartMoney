package com.peil.smartmoney.about;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.peil.smartmoney.R;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

public class IntroduceActivity extends AppCompatActivity {

    private QMUITopBarLayout bar_top;
    public TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);

        bar_top = findViewById(R.id.bar_top);
        bar_top.setTitle("功能介绍");
        bar_top.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_content = findViewById(R.id.tv_content);
        tv_content.setText("本APP是一款理财记账APP，能够方便、快捷的操作和记录日常花销。核心功能包括记账及理财。\n" +
                "如何记账？\n" +
                "点击首页记账Tab，点击右上角的添加按钮进入添加页面，在此页面记账参数，如金额，时间，账户，备注，类别，金额类型(收入还是支出)等。\n" +
                "\n" +
                "如何查看记账的记录？\n" +
                "在首页记账Tab会显示所有记账的记录列表，所有的记账信息都在这里显示。\n" +
                "\n" +
                "如何查看一段时间内的记账数据？\n" +
                "在首页的计算器页面会显示最近一个月的记账分类数据，主要是显示类型对应的总金额。可以点击右上角筛选按钮来设置具体的时间段。\n" +
                "\n" +
                "如何查看理财信息？\n" +
                "在首页的理财页面，会显示基本的理财常识。");
    }
}
