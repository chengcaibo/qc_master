package com.qichong.entity;

/**
 * 
 * 订单评论表（order_comment）
 * 
 * @创建人 孙建雷
 * @创建时间 2018年8月7日16:26:06
 */
public class OrderComment {

	private Integer commentId;// 评论id，自增长，唯一主键
	private String orderId;// 评论的订单id
	private Integer userId;
	private Integer score;// 评分（最低一分，最高十分）
	private String commentContent;// 评论内容（至少五个字）
	private Integer stateId;// 状态，默认1

	public OrderComment() {
	}

	public OrderComment(Integer commentId) {
		this.commentId = commentId;
	}

	public OrderComment(String orderId) {
		this.orderId = orderId;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	@Override
	public String toString() {
		return "OrderComment [commentId=" + commentId + ", orderId=" + orderId + ", score=" + score
				+ ", commentContent=" + commentContent + ", stateId=" + stateId + "]";
	}

}
