package com.peil.smartmoney.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 理财知识
 */
@Entity
public class FinanicalItem {
    
    @Id(autoincrement = true)
    Long id;
    String title;
    String content;
    Long createTime;
    @Generated(hash = 1882921243)
    public FinanicalItem(Long id, String title, String content, Long createTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
    }
    @Generated(hash = 1684308449)
    public FinanicalItem() {
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
    public Long getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    
    
}
