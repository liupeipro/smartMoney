package com.peil.smartmoney.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.ViewHolder;
import com.peil.smartmoney.R;

/**
 * 调用 Dialog 帮助类
 */
public class PDialogHelper {
    private static final float SCALE_WIDTH = 0.8f;
    private static final float SCALE_HEIGHT = 0.3f;

    private Context mContext;
    private int mContentWidth = 0, mContentHeight = 0;
    private DialogPlus mDialogPlus = null;
    private DialogPlusBuilder mDialogPlusBuilder = null;
    private Builder mBuilder = null;
    private DialogOnClickListener mDialogOnClickListener = null;

    private View mContentView = null;
    private View mHeaderView = null;
    private View mFooterView = null;
    private TextView tv_title, tv_content;
    private Button btn_left, btn_right;

    public interface DialogOnClickListener {
        void onBtnLeftClicked();

        void onBtnRightClicked();
    }

    public static class Builder {

        private String title = "";
        private String content = "";
        private String btnLeftText = "";
        private String btnRightText = "";
        private int mGravity = -1;

        public Builder() {

        }

        public Builder(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public Builder(String title, String content, String btnLeftText, String btnRightText) {
            this.title = title;
            this.content = content;
            this.btnLeftText = btnLeftText;
            this.btnRightText = btnRightText;
        }

        public Builder(String title, String content, String btnLeftText, String btnRightText, int gravity) {
            this.title = title;
            this.content = content;
            this.btnLeftText = btnLeftText;
            this.btnRightText = btnRightText;
            this.mGravity = gravity;
        }

        public String getTitle() {
            return title;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getContent() {
            return content;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public String getBtnLeftText() {
            return btnLeftText;
        }

        public Builder setBtnLeftText(String btnLeftText) {
            this.btnLeftText = btnLeftText;
            return this;
        }

        public String getBtnRightText() {
            return btnRightText;
        }

        public Builder setBtnRightText(String btnRightText) {
            this.btnRightText = btnRightText;
            return this;
        }

        public int getGravity() {
            return mGravity;
        }

        public Builder setGravity(int gravity) {
            this.mGravity = gravity;
            return this;
        }

    }

    private static class NormalViewHolder extends ViewHolder {

        public NormalViewHolder(int viewResourceId) {
            super(viewResourceId);
        }

        public NormalViewHolder(View contentView) {
            super(contentView);
        }
    }

    private View.OnClickListener mFooterOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_left:
                    if (mDialogOnClickListener != null) {
                        mDialogOnClickListener.onBtnLeftClicked();
                    }
                    break;
                case R.id.btn_right:
                    if (mDialogOnClickListener != null) {
                        mDialogOnClickListener.onBtnRightClicked();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public PDialogHelper(Context context) {
        this(context, null);
    }

    public static PDialogHelper newDialog(Context context) {
        return new PDialogHelper(context);
    }

    public PDialogHelper(Context context, Builder builder) {
        mContext = context;
        mBuilder = builder;
        initView();
        initData();
    }


    private void initView() {
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_content_normal, null);
        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.dialog_header_normal, null);
        mFooterView = LayoutInflater.from(mContext).inflate(R.layout.dialog_footer_normal, null);

        mDialogPlusBuilder = DialogPlus.newDialog(mContext);

        tv_title = mHeaderView.findViewById(R.id.tv_title);
        tv_content = mContentView.findViewById(R.id.tv_content);
        btn_left = mFooterView.findViewById(R.id.btn_left);
        btn_right = mFooterView.findViewById(R.id.btn_right);

        btn_left.setOnClickListener(mFooterOnClickListener);
        btn_right.setOnClickListener(mFooterOnClickListener);
    }

    private void initData() {
        mContentWidth = (int) (ScreenUtils.getScreenWidth() * SCALE_WIDTH);
        mContentHeight = (int) (ScreenUtils.getScreenHeight() * SCALE_HEIGHT);
    }

    private void initController() {
        if (mBuilder != null) {
            if (!TextUtils.isEmpty(mBuilder.getTitle())) {
                tv_title.setText(mBuilder.getTitle());
                mHeaderView.setVisibility(View.VISIBLE);
            } else {
                tv_title.setText("");
                mHeaderView.setVisibility(View.GONE);
            }

            if (TextUtils.isEmpty(mBuilder.getBtnLeftText()) && TextUtils.isEmpty(mBuilder.getBtnRightText())) {
                mFooterView.setVisibility(View.GONE);
            } else {
                btn_left.setText(mBuilder.getBtnLeftText());
                btn_right.setText(mBuilder.getBtnRightText());
                mFooterView.setVisibility(View.VISIBLE);
            }

            tv_content.setText(mBuilder.getContent());
            mDialogPlusBuilder.setGravity(mBuilder.mGravity != -1 ? mBuilder.getGravity() : Gravity.CENTER);
        } else {
            mHeaderView.setVisibility(View.VISIBLE);
            mFooterView.setVisibility(View.VISIBLE);

            tv_title.setText("title");
            tv_content.setText("content");
            btn_left.setText("取消");
            btn_right.setText("确定");

            mDialogPlusBuilder.setGravity(Gravity.CENTER);
        }

        mDialogPlusBuilder.setHeader(mHeaderView);
        mDialogPlusBuilder.setFooter(mFooterView);

        mDialogPlusBuilder.setContentHolder(new NormalViewHolder(mContentView));
        mDialogPlusBuilder.setContentBackgroundResource(R.drawable.bg_white_round);
        mDialogPlusBuilder.setContentWidth(mContentWidth);
        mDialogPlusBuilder.setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialogPlusBuilder.setExpanded(true);

        mDialogPlus = mDialogPlusBuilder.create();
    }

    public void show() {
        initController();
        if (mDialogPlus != null) {
            mDialogPlus.show();
        }
    }

    public void dismiss() {
        if (mDialogPlus != null) {
            mDialogPlus.dismiss();
        }
    }

    public PDialogHelper setBuilder(Builder builder) {
        this.mBuilder = builder;
        return this;
    }

    public PDialogHelper setDialogOnClickListener(DialogOnClickListener dialogOnClickListener) {
        this.mDialogOnClickListener = dialogOnClickListener;
        return this;
    }
}
