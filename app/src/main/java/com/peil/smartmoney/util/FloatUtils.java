package com.peil.smartmoney.util;

import java.math.BigDecimal;

public class FloatUtils {
    private static final int DEF_DIV_SCALE = 10;
    
    //  public static void main(String[] args){
    //      System.out.println(add(0.01, 0.05));
    //      System.out.println(sub(1.0, 0.42));
    //      System.out.println(mul(4.015, 100));
    //      System.out.println(div(123.3, 100));
    //  }
    //
    // 相加
    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        
        return b1.add(b2).doubleValue();
    }
    
    // 相除
    public static double div(double d1, double d2) {
        return div(d1, d2, DEF_DIV_SCALE);
    }
    
    public static double div(double d1, double d2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    // 相乘
    public static double mul(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        
        return b1.multiply(b2).doubleValue();
    }
    
    // 相减
    public static double sub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        
        return b1.subtract(b2).doubleValue();
    }
}

//~ Formatted by Jindent --- http://www.jindent.com
