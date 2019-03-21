package com.qichong.entity;

public class AwardWinnerInfo {
    //中奖名单的id，唯一标识自增
    Integer id;
    //中奖者的userId
    Integer userId;
    //中奖者的手机号
    String telephone;
    //中奖者的中奖码
    Integer winningCode;
    //中奖者的中奖金额
    Integer winningAmount;

    String wxcode;

    public String getWxcode() {
        return wxcode;
    }

    public void setWxcode(String wxcode) {
        this.wxcode = wxcode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getWinningCode() {
        return winningCode;
    }

    public void setWinningCode(Integer winningCode) {
        this.winningCode = winningCode;
    }

    public Integer getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(Integer winningAmount) {
        this.winningAmount = winningAmount;
    }

    @Override
    public String toString() {
        return "AwardWinnerInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", telephone='" + telephone + '\'' +
                ", winningCode=" + winningCode +
                ", winningAmount=" + winningAmount +
                '}';
    }
}
