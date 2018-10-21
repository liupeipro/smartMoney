package com.peil.smartmoney.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.peil.smartmoney.R;

/**
 * Created by YoKeyword on 16/6/3.
 */
public class InputAmountView extends RelativeLayout {
    private static final String AMOUNT_DEFAULT = "0";
    private String tempAmount = "";
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_0:
                    onAmountClicked("0");
                    
                    break;
                
                case R.id.btn_1:
                    onAmountClicked("1");
                    
                    break;
                
                case R.id.btn_2:
                    onAmountClicked("2");
                    
                    break;
                
                case R.id.btn_3:
                    onAmountClicked("3");
                    
                    break;
                
                case R.id.btn_4:
                    onAmountClicked("4");
                    
                    break;
                
                case R.id.btn_5:
                    onAmountClicked("5");
                    
                    break;
                
                case R.id.btn_6:
                    onAmountClicked("6");
                    
                    break;
                
                case R.id.btn_7:
                    onAmountClicked("7");
                    
                    break;
                
                case R.id.btn_8:
                    onAmountClicked("8");
                    
                    break;
                
                case R.id.btn_9:
                    onAmountClicked("9");
                    
                    break;
                
                case R.id.btn_dot:
                    onAmountDot();
                    
                    break;
                
                case R.id.btn_del:
                    onAmountDelete();
                    
                    break;
                
                case R.id.btn_ok:
                    onAmountDone();
                    
                    break;
            }
        }
    };
    private OnAmountClickListener mOnAmountClickListener;
    Button btn_1;
    Button btn_0, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_dot, btn_del, btn_ok;
    TextView tv_amount;
    
    public InputAmountView(Context context) {
        this(context, null);
    }
    
    public InputAmountView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public InputAmountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    
    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_inputamount_main, this);
        tv_amount = findViewById(R.id.tv_amount);
        btn_0 = findViewById(R.id.btn_0);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_dot = findViewById(R.id.btn_dot);
        btn_del = findViewById(R.id.btn_del);
        btn_ok = findViewById(R.id.btn_ok);
        btn_0.setOnClickListener(mOnClickListener);
        btn_1.setOnClickListener(mOnClickListener);
        btn_2.setOnClickListener(mOnClickListener);
        btn_3.setOnClickListener(mOnClickListener);
        btn_4.setOnClickListener(mOnClickListener);
        btn_5.setOnClickListener(mOnClickListener);
        btn_6.setOnClickListener(mOnClickListener);
        btn_7.setOnClickListener(mOnClickListener);
        btn_8.setOnClickListener(mOnClickListener);
        btn_9.setOnClickListener(mOnClickListener);
        btn_dot.setOnClickListener(mOnClickListener);
        btn_del.setOnClickListener(mOnClickListener);
        btn_ok.setOnClickListener(mOnClickListener);
        tempAmount = AMOUNT_DEFAULT;
        updateAmount(tempAmount);
    }
    
    private void onAmountClicked(String amount) {
        
        // 只有一位，已经显示0，再次选0是无效的
        if (!tempAmount.isEmpty()
            && (tempAmount.length() == 1)
            && amount.equals("0")
            && tempAmount.equals("0")) {
            return;
        }
        
        // 保留小数点后2位
        int lastDotIndex = tempAmount.indexOf(".");
        
        if (lastDotIndex == -1) {
        } else {
            if ((tempAmount.length() > 2) && (tempAmount.length() - lastDotIndex > 2)) {
                return;
            }
        }
        
        if (tempAmount.equals("0")) {
            tempAmount = "";
        }
        
        tempAmount += amount;
        updateAmount(tempAmount);
    }
    
    private void onAmountDelete() {
        if (!tempAmount.isEmpty()) {
            if (tempAmount.length() == 1) {
                tempAmount = "0";
            } else {
                tempAmount = tempAmount.substring(0, tempAmount.length() - 1);
            }
            
            updateAmount(tempAmount);
        }
    }
    
    private void onAmountDone() {
        if (mOnAmountClickListener != null) {
            float temp = getInputAmount();
            
            if (temp > 0) {
                mOnAmountClickListener.onAmountDone(getInputAmount());
            } else {
                mOnAmountClickListener.onAmountZero();
            }
        }
    }
    
    private void onAmountDot() {
        if (!tempAmount.contains(".")) {
            tempAmount += ".";
            updateAmount(tempAmount);
        }
    }
    
    private void updateAmount(String text) {
        tv_amount.setText(text);
    }
    
    public void setAmount(String amount) {
        tempAmount = amount;
        updateAmount(tempAmount);
    }
    
    public float getInputAmount() {
        float result = 0;
        
        if (!tempAmount.isEmpty()) {
            result = Float.valueOf(tempAmount);
        }
        
        return result;
    }
    
    public void setOnAmountClickListener(OnAmountClickListener listener) {
        mOnAmountClickListener = listener;
    }
    
    public interface OnAmountClickListener {
        void onAmountDone(float amount);
        
        void onAmountZero();
    }
}

//~ Formatted by Jindent --- http://www.jindent.com
