package com.qichong.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class AllegeInfo {
    Integer id;
    //申诉的内容
    String allegeContent;
    //申诉的图片
    String allegeImage;
    //申诉者的userid
    Integer userId;
    //该申诉是否已经过期，0是未过期，1是已经过期
    Integer expire;
    //审核结果
    String allegeResult;
    //申诉的时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    //申诉是否已经通过，0表示还未进行审核，1表示被拒绝，2表示申诉通过
    private Integer allegeState;

    public Integer getAllegeState() {
        return allegeState;
    }

    public void setAllegeState(Integer allegeState) {
        this.allegeState = allegeState;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getAllegeResult() {
        return allegeResult;
    }

    public void setAllegeResult(String allegeResult) {
        this.allegeResult = allegeResult;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAllegeContent() {
        return allegeContent;
    }

    public void setAllegeContent(String allegeContent) {
        this.allegeContent = allegeContent;
    }

    public String getAllegeImage() {
        return allegeImage;
    }

    public void setAllegeImage(String allegeImage) {
        this.allegeImage = allegeImage;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(Integer expire) {
        this.expire = expire;
    }

    @Override
    public String toString() {
        return "AllegeInfo{" +
                "id=" + id +
                ", allegeContent='" + allegeContent + '\'' +
                ", allegeImage='" + allegeImage + '\'' +
                ", userId=" + userId +
                ", expire=" + expire +
                ", allegeResult='" + allegeResult + '\'' +
                '}';
    }
}

