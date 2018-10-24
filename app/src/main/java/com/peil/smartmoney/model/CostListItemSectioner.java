package com.peil.smartmoney.model;

import com.peil.smartmoney.util.FloatUtils;

public class CostListItemSectioner {
    private double totalAmount = 0f;
    private String date;
    
    public CostListItemSectioner() {
    }
    
    public CostListItemSectioner(String date , float totalAmount) {
        this.date = date;
        this.totalAmount = totalAmount;
    }
    
    public void addTotalAmount(String amount , CostItemAmountType amountType) {
        if (amountType.getName().equals("收入")) {
            this.totalAmount = FloatUtils.add(this.totalAmount , Double.valueOf(amount));
        } else {
            this.totalAmount = FloatUtils.sub(this.totalAmount , Double.valueOf(amount));
        }
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getTotalAmountStr() {
        return String.valueOf(totalAmount);
    }
}


