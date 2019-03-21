package com.qichong.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Complaints {
    Integer id;
    //投诉人
    Integer complainant;
    //被投诉人
    Integer respondent;
    //投诉内容
    String complaintDetails;
    //被投诉的需求id
    Integer demandId;
    //投诉的时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

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

    public Integer getRespondent() {
        return respondent;
    }

    public void setRespondent(Integer respondent) {
        this.respondent = respondent;
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

    @Override
    public String toString() {
        return "Complaints{" +
                "id=" + id +
                ", complainant=" + complainant +
                ", respondent=" + respondent +
                ", complaintDetails='" + complaintDetails + '\'' +
                ", demandId=" + demandId +
                '}';
    }
}
