package com.peil.smartmoney.cost;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.dialogplus.DialogPlus;
import com.peil.smartmoney.R;
import com.peil.smartmoney.base.BaseActivity;
import com.peil.smartmoney.base.MoneyApplication;
import com.peil.smartmoney.greendao.gen.CostItemDao;
import com.peil.smartmoney.model.CostItem;
import com.peil.smartmoney.util.MoneyConstants;
import com.peil.smartmoney.util.PDialogHelper;
import com.peil.smartmoney.util.ReceiverUtils;
import com.peil.smartmoney.util.ResHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class CostCheckActivity extends BaseActivity {
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_del:
                    showDelDialog();

                    break;

                case R.id.topbar_right_change_button:
                    Intent intent = new Intent(CostCheckActivity.this, CostAddActivity.class);

                    intent.putExtra(MoneyConstants.INTENT_COST_EDIT_ITEM_ID, mItem.get_id());
                    startActivity(intent);
                    finish();

                    break;
            }
        }
    };
    private CostItem mItem;
    private QMUITopBarLayout bar_top;
    private TextView tv_amount;
    private QMUIRoundButton btn_del;
    private QMUIGroupListView mGroupListView;
    private QMUICommonListItemView itemAccount, itemCreateTime, itemLastModifyTime, itemRemark;

    private void doDeleteRecord() {
        MoneyApplication.getDaoInstant().getCostItemDao().delete(mItem);
        ToastUtils.showShort("删除成功");
        sendBroadcast(new Intent(ReceiverUtils.RECEIVER_COST_LIST_UPDATE));
        onBackPressed();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_cost_check;
    }

    @Override
    protected void initView() {
        bar_top = findViewById(R.id.bar_top);
        tv_amount = findViewById(R.id.tv_amount);
        btn_del = findViewById(R.id.btn_del);
        mGroupListView = findViewById(R.id.groupListView);

        //      账户
        itemAccount =
                mGroupListView.createItemView(null, "账户", "", QMUICommonListItemView.HORIZONTAL,
                        QMUICommonListItemView.ACCESSORY_TYPE_NONE);

        //      创建时间
        itemCreateTime =
                mGroupListView.createItemView(null, "创建时间", "", QMUICommonListItemView.HORIZONTAL,
                        QMUICommonListItemView.ACCESSORY_TYPE_NONE);

        //      最后修改时间
        itemLastModifyTime =
                mGroupListView.createItemView(null, "最后修改时间", "", QMUICommonListItemView.HORIZONTAL,
                        QMUICommonListItemView.ACCESSORY_TYPE_NONE);

        //      备注
        itemRemark =
                mGroupListView.createItemView(null, "备注", "", QMUICommonListItemView.HORIZONTAL,
                        QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        initDialogDelteView();
    }

    @Override
    protected void initData() {
        Long intentItemId = getIntent().getLongExtra(MoneyConstants.INTENT_COST_EDIT_ITEM_ID, -1);

        if (intentItemId != -1) {
            mItem = MoneyApplication.getDaoInstant()
                    .getCostItemDao()
                    .queryBuilder()
                    .where(CostItemDao.Properties._id.eq(intentItemId))
                    .unique();
        }
    }

    @Override
    protected void initController() {
        bar_top.setTitle("查看记账");
        bar_top.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        bar_top.addRightImageButton(R.mipmap.ic_edit_white, R.id.topbar_right_change_button)
                .setOnClickListener(mOnClickListener);
        btn_del.setText("删除");
        btn_del.setOnClickListener(mOnClickListener);
        update(mItem);
    }

    private DialogPlus mDialogDelete;
    private com.orhanobut.dialogplus.OnClickListener mDeleteClickListener = new com.orhanobut.dialogplus.OnClickListener() {
        @Override
        public void onClick(DialogPlus dialog, View view) {
            switch (view.getId()) {
                case R.id.btn_left:
                    ToastUtils.showShort("取消");
                    mDialogDelete.dismiss();
                    break;
                case R.id.btn_right:
                    ToastUtils.showShort("确认");
                    doDeleteRecord();
                    break;
                default:
                    break;
            }
        }
    };

    private PDialogHelper mPDialogHelper = null;

    /**
     * 初始化删除对话框
     */
    private void initDialogDelteView() {
        mPDialogHelper = PDialogHelper.newDialog(new PDialogHelper.Builder(this)
                .setBtnLeftText("取消")
                .setBtnRightText("确认")
                .setTitle("确认要删除吗？")
                .setContent("辛苦记的帐就找不回来啦！")
                .setDialogOnClickListener(
                        new PDialogHelper.Builder.DialogOnClickListener() {
                            @Override
                            public void onBtnLeftClicked() {
                                ToastUtils.showShort("取消");
                                mPDialogHelper.dismiss();
                            }

                            @Override
                            public void onBtnRightClicked() {
                                mPDialogHelper.dismiss();

                                ToastUtils.showShort("确认");
                                doDeleteRecord();
                            }
                        }));
    }


    private void showDelDialog() {
        mPDialogHelper.show();
    }

    private void update(CostItem item) {
        if (item != null) {
            String amount = "";

            if (item.getCostAmountType().getName().equals("收入")) {
                amount = "￥+" + item.getCostAmount() + " 元";
                tv_amount.setTextColor(ResHelper.getColor(this, R.color.text_plus));
            } else {
                amount = "￥-" + item.getCostAmount() + " 元";
                tv_amount.setTextColor(ResHelper.getColor(this, R.color.text_sub));
            }

            tv_amount.setText(amount);
            itemAccount.setDetailText(item.getCostAccount().getCostAccountame());
            itemCreateTime.setDetailText(item.getTempCreateTime());
            itemLastModifyTime.setDetailText(item.getTempLastModifyTime());
            itemRemark.setDetailText(item.getRemark());
        }

        QMUIGroupListView.newSection(this)
                .setTitle("")
                .setDescription("")
                .addItemView(itemAccount, mOnClickListener)
                .addItemView(itemCreateTime, mOnClickListener)
                .addItemView(itemLastModifyTime, mOnClickListener)
                .addItemView(itemRemark, mOnClickListener)
                .addTo(mGroupListView);
    }
}


