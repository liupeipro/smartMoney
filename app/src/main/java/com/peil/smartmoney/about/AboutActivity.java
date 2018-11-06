package com.peil.smartmoney.about;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.peil.smartmoney.R;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

public class AboutActivity extends AppCompatActivity {

    private QMUITopBarLayout bar_top;

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
    }
}
