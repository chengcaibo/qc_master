package com.qichong.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ComplaintVO {
    private  Integer id;
    /**
     * 投诉人的userId
     */
    private Integer complainant;
    /**
     * 投诉人的昵称
     */
    private String complainantNickName;
    /**
     * 被投诉人的userid
     */
    private Integer respondent;
    /**
     * 被投诉人的昵称
     */
    private String respondentNickName;
    /**
     * 投诉的内容
     */
    private String complaintDetails;
    /**
     * 投诉的需求id
     */
    private Integer demandId;
    /**
     * 投诉的需求内容
     */
    private String content;
    /**
     * 该投诉闯将的时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getComplainant() {
        return complainant;
    }

    public void setComplainant(Integer complainant) {
        this.complainant = complainant;
    }

    public String getComplainantNickName() {
        return complainantNickName;
    }

    public void setComplainantNickName(String complainantNickName) {
        this.complainantNickName = complainantNickName;
    }

    public Integer getRespondent() {
        return respondent;
    }

    public void setRespondent(Integer respondent) {
        this.respondent = respondent;
    }

    public String getRespondentNickName() {
        return respondentNickName;
    }

    public void setRespondentNickName(String respondentNickName) {
        this.respondentNickName = respondentNickName;
    }

    public String getComplaintDetails() {
        return complaintDetails;
    }

    public void setComplaintDetails(String complaintDetails) {
        this.complaintDetails = complaintDetails;
    }

    public Integer getDemandId() {
        return demandId;
    }

    public void setDemandId(Integer demandId) {
        this.demandId = demandId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "ComplaintVO{" +
                "id=" + id +
                ", complainant=" + complainant +
                ", complainantNickName='" + complainantNickName + '\'' +
                ", respondent=" + respondent +
                ", respondentNickName='" + respondentNickName + '\'' +
                ", complaintDetails='" + complaintDetails + '\'' +
                ", demandId=" + demandId +
                ", content='" + content + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}
