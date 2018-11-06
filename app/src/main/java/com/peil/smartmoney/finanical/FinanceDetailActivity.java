package com.peil.smartmoney.finanical;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.peil.smartmoney.R;
import com.peil.smartmoney.base.MoneyApplication;
import com.peil.smartmoney.model.FinanceItem;
import com.peil.smartmoney.util.MoneyConstants;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

public class FinanceDetailActivity extends AppCompatActivity {
    private QMUITopBarLayout bar_top;
    public TextView tv_title, tv_content;
    private FinanceItem mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_detail);

        bar_top = findViewById(R.id.bar_top);
        bar_top.setTitle("详情");
        bar_top.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);

        Long intentId = getIntent().getLongExtra(MoneyConstants.INTENT_FINANCE_ID, -1);
        if (intentId != -1) {
            mItem = MoneyApplication.getDaoInstant().getFinanceItemDao().load(intentId);
        }

        updateView(mItem);
    }

    private void updateView(FinanceItem item) {
        tv_title.setText(item.getTitle());
        tv_content.setText(item.getContent());
    }
}
