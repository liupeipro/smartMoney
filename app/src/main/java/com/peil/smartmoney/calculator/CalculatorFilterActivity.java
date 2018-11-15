package com.peil.smartmoney.calculator;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.peil.smartmoney.R;
import com.peil.smartmoney.base.BaseActivity;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

public class CalculatorFilterActivity extends BaseActivity {

    private QMUITopBarLayout bar_top;
    private QMUICommonListItemView item_choose_date_start, item_choose_date_end;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_calculator_filter;
    }

    @Override
    protected void initView() {
        bar_top = findViewById(R.id.bar_top);
        item_choose_date_start = findViewById(R.id.item_choose_date_start);
        item_choose_date_end = findViewById(R.id.item_choose_date_end);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initController() {

        bar_top.setTitle("筛选");
        bar_top.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
        bar_top.addRightTextButton("完成", R.id.topbar_right_change_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("filter_date_start", "");
                        intent.putExtra("filter_date_end", "");
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                });

        item_choose_date_start.setText("起始日期");
        item_choose_date_start.setDetailText("2018-09");
        item_choose_date_end.setDetailText("2018-10");
        item_choose_date_end.setText("截止日期");
    }
}


