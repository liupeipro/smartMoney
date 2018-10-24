package com.peil.smartmoney.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 理财知识
 */
@Entity public class FinanceItem {
    @Id(autoincrement = true) Long id;
    String title;
    String content;
    
    public FinanceItem(String title , String content) {
        this.title = title;
        this.content = content;
    }
    
    @Generated(hash = 38785258) public FinanceItem(Long id , String title , String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    
    @Generated(hash = 632012124) public FinanceItem() {
    }
    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
}
