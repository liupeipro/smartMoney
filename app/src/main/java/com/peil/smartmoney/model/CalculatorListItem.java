package com.peil.smartmoney.model;

import com.peil.smartmoney.util.FloatUtils;
import java.util.List;

public class CalculatorListItem {
    Double totalAmount    = 0d,
           totalInAmount  = 0d,
           totalOutAmount = 0d;

    // CostItemAmountType currentAmountType;
    Long typeId;

    // CostItemAmountType itemType;
    List<CostItem> data;
    String         scaleIn;

//  String scaleOut;

    public CalculatorListItem() {}

    public CalculatorListItem(Long typeId, List<CostItem> data) {
        this.typeId = typeId;
        this.data   = data;
    }

    public void updateTotalAmount() {
        for (CostItem item : data) {

//          CostItemAmountType amountType = item.getCostAmountType();
//          if (amountType.getName().equals("收入")) {
            totalAmount = FloatUtils.add(totalAmount, Double.valueOf(item.getCostAmount()));

//          } else {
//              totalOutAmount = FloatUtils.add(totalOutAmount, Double.valueOf(item.getCostAmount()));
//          }
//          itemType = item.getCostAmountType();
        }
    }

    public List<CostItem> getData() {
        return data;
    }

    public void setData(List<CostItem> data) {
        this.data = data;
    }

    public String getScaleIn() {
        return scaleIn;
    }

    public void setScaleIn(String scaleIn) {
        this.scaleIn = scaleIn;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

//  public Double getTotalInAmount() {
//      return totalInAmount;
//  }
//
//  public Double getTotalOutAmount() {
//      return totalOutAmount;
//  }
    public String getTotalAmountStr() {
        return String.valueOf(totalAmount);
    }

//
//  public String getTotalInAmountStr() {
//      return String.valueOf(totalInAmount);
//  }
//
//  public String getTotalOutAmountStr() {
//      return String.valueOf(totalOutAmount);
//  }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

//  public String getScaleOut() {
//      return scaleOut;
//  }
//
//  public void setScaleOut(String scaleOut) {
//      this.scaleOut = scaleOut;
//  }
    // public CostItemAmountType getCurrentAmountType() {
//      return currentAmountType;
//  }
//
//  public void setCurrentAmountType(CostItemAmountType currentAmountType) {
//      this.currentAmountType = currentAmountType;
//  }
}


//~ Formatted by Jindent --- http://www.jindent.com
