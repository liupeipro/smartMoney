package com.peil.smartmoney.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnClickListener;
import com.peil.smartmoney.R;

/**
 * 调用 Dialog 帮助类
 */
public class PDialogHelper {
    private static final float SCALE_WIDTH = 0.8f;
    private static final float SCALE_HEIGHT = 0.25f;

    private int mContentWidth = 0, mContentHeight = 0;
    private DialogPlus mDialogPlus = null;
    private DialogPlusBuilder mDialogPlusBuilder = null;
    private Builder mBuilder = null;
    private NormalViewHolder mContentViewHolder;

    private View mContentView = null;
    private View mHeaderView = null;
    private View mFooterView = null;
    private TextView tv_title, tv_content;
    private Button btn_left, btn_right;

    public static class Builder {

        public interface DialogOnClickListener {
            void onBtnLeftClicked();

            void onBtnRightClicked();
        }

        private Context mContext;
        private String title = "";
        private String content = "";
        private String btnLeftText = "";
        private String btnRightText = "";
        private int mGravity = -1;
        private DialogOnClickListener mDialogOnClickListener = null;

        public Builder(Context context) {
            mContext = context;
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

        public Context getContext() {
            return mContext;
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

        public DialogOnClickListener getDialogOnClickListener() {
            return mDialogOnClickListener;
        }

        public Builder setDialogOnClickListener(DialogOnClickListener listener) {
            this.mDialogOnClickListener = listener;
            return this;
        }
    }

    private static class NormalViewHolder implements Holder {

        private static final int INVALID = -1;

        private int backgroundResource;

        private ViewGroup headerContainer;
        private View headerView;

        private ViewGroup footerContainer;
        private View footerView;

        private View.OnKeyListener keyListener;

        private View contentView;
        private int viewResourceId = INVALID;

        public NormalViewHolder(int viewResourceId) {
            this.viewResourceId = viewResourceId;
        }

        public NormalViewHolder(View contentView) {
            this.contentView = contentView;
        }

        @Override
        public void addHeader(View view) {
            if (view == null) {
                return;
            }
            headerContainer.addView(view);
            headerView = view;
        }

        @Override
        public void addFooter(View view) {
            if (view == null) {
                return;
            }
            footerContainer.addView(view);
            footerView = view;
        }

        @Override
        public void setBackgroundResource(int colorResource) {
            this.backgroundResource = colorResource;
        }

        @Override
        public View getView(LayoutInflater inflater, ViewGroup parent) {
            View view = inflater.inflate(R.layout.dialog_container_normal, parent, false);
            View outMostView = view.findViewById(com.orhanobut.dialogplus.R.id.dialogplus_outmost_container);
            outMostView.setBackgroundResource(backgroundResource);
            ViewGroup contentContainer = (ViewGroup) view.findViewById(com.orhanobut.dialogplus.R.id.dialogplus_view_container);
            contentContainer.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyListener == null) {
                        throw new NullPointerException("keyListener should not be null");
                    }
                    return keyListener.onKey(v, keyCode, event);
                }
            });
            addContent(inflater, parent, contentContainer);
            headerContainer = (ViewGroup) view.findViewById(com.orhanobut.dialogplus.R.id.dialogplus_header_container);
            footerContainer = (ViewGroup) view.findViewById(com.orhanobut.dialogplus.R.id.dialogplus_footer_container);
            return view;
        }

        private void addContent(LayoutInflater inflater, ViewGroup parent, ViewGroup container) {
            if (viewResourceId != INVALID) {
                contentView = inflater.inflate(viewResourceId, parent, false);
            } else {
                ViewGroup parentView = (ViewGroup) contentView.getParent();
                if (parentView != null) {
                    parentView.removeView(contentView);
                }
            }

            container.addView(contentView);
        }

        @Override
        public void setOnKeyListener(View.OnKeyListener keyListener) {
            this.keyListener = keyListener;
        }

        @Override
        public View getInflatedView() {
            return contentView;
        }

        @Override
        public View getHeader() {
            return headerView;
        }

        @Override
        public View getFooter() {
            return footerView;
        }
    }


    public static PDialogHelper newDialog(Builder builder) {
        return new PDialogHelper(builder);
    }

    public PDialogHelper(Builder builder) {
        mBuilder = builder;
        initView();
        initData();
        initController();
    }


    private void initView() {
        mContentView = LayoutInflater.from(mBuilder.getContext()).inflate(R.layout.dialog_content_normal, null);
        mHeaderView = LayoutInflater.from(mBuilder.getContext()).inflate(R.layout.dialog_header_normal, null);
        mFooterView = LayoutInflater.from(mBuilder.getContext()).inflate(R.layout.dialog_footer_normal, null);

        tv_title = mHeaderView.findViewById(R.id.tv_title);
        tv_content = mContentView.findViewById(R.id.tv_content);
        btn_left = mFooterView.findViewById(R.id.btn_left);
        btn_right = mFooterView.findViewById(R.id.btn_right);

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        mContentViewHolder = new NormalViewHolder(mContentView);

        mDialogPlusBuilder = DialogPlus.newDialog(mBuilder.getContext())
                .setContentBackgroundResource(R.drawable.bg_white_round)
                .setExpanded(true)
                .setHeader(mHeaderView)
                .setContentHolder(mContentViewHolder)
                .setFooter(mFooterView).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        switch (view.getId()) {
                            case R.id.btn_left:
                                if (mBuilder.getDialogOnClickListener() != null) {
                                    mBuilder.getDialogOnClickListener().onBtnLeftClicked();
                                }
                                break;
                            case R.id.btn_right:
                                if (mBuilder.getDialogOnClickListener() != null) {
                                    mBuilder.getDialogOnClickListener().onBtnRightClicked();
                                }
                                break;
                            default:
                        }
                    }
                });

    }

    private void initData() {
        mContentWidth = (int) (ScreenUtils.getScreenWidth() * SCALE_WIDTH);
        mContentHeight = (int) (ScreenUtils.getScreenHeight() * SCALE_HEIGHT);
    }

    private void initController() {
        mDialogPlusBuilder.setContentWidth(mContentWidth)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        if (mBuilder != null) {
            if (!TextUtils.isEmpty(mBuilder.getTitle())) {
                tv_title.setText(mBuilder.getTitle());
                mHeaderView.setVisibility(View.VISIBLE);
            } else {
                tv_title.setText("");
                mHeaderView.setVisibility(View.GONE);
            }

            if (TextUtils.isEmpty(mBuilder.getBtnLeftText())
                    && TextUtils.isEmpty(mBuilder.getBtnRightText())) {
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

        mDialogPlus = mDialogPlusBuilder.create();
    }

    public void show() {
        if (mDialogPlus != null) {
            mDialogPlus.show();
        }
    }

    public void dismiss() {
        if (mDialogPlus != null) {
            mDialogPlus.dismiss();
        }
    }

}
