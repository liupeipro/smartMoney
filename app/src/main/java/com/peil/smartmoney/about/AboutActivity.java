package com.peil.smartmoney.about;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.peil.smartmoney.R;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

public class AboutActivity extends AppCompatActivity {

    private QMUITopBarLayout bar_top;
    private QMUIGroupListView mGroupListView;
    TextView tv_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        bar_top = findViewById(R.id.bar_top);
        bar_top.setTitle("关于");
        bar_top.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mGroupListView = findViewById(R.id.groupListView);
        tv_version = findViewById(R.id.tv_version);
        tv_version.setText("版本号: v" + AppUtils.getAppVersionName());

        QMUICommonListItemView itemAccount =
                mGroupListView.createItemView(null, "版本更新", "", QMUICommonListItemView.HORIZONTAL,
                        QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);



        QMUIGroupListView.newSection(this)
                .setTitle("")
                .setDescription("")
                .addItemView(itemAccount, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showShort("已是最新版本");
                    }
                })
                .addTo(mGroupListView);
    }
}
