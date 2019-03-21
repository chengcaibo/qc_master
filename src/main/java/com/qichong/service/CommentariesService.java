package com.qichong.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qichong.dao.CommentariesDao;
import com.qichong.entity.Commentaries;

/**
 * CommentariesService
 * 
 * @创建人 吴志伟
 * @修改人 孙建雷
 * @修改时间 2017年11月9日 下午5:41:15
 */

@Service
public class CommentariesService {

	@Autowired
	CommentariesDao commentariesDao;
	
	/**
	 * 新增评价
	 */
	public boolean insertCommentaries(Commentaries commentaries) {
		return commentariesDao.insertCommentaries(commentaries);
	}

	/**
	 * 修改评论信息
	 */
	public boolean updateIdCommentaries(Commentaries commentaries) {
		return commentariesDao.updateIdCommentaries(commentaries);
	}

	/**
	 * 根据用户id查询单个用户所被评论信息
	 */
	public List<Commentaries> queryUserCommentaries(int reviewersBId) {
		return commentariesDao.queryUserCommentaries(reviewersBId);
	}

	/**
	 * 根据评论id删除评论信息
	 */
	public boolean deleteCommentaries(int commentariesId) {
		return commentariesDao.deleteCommentaries(commentariesId);
	}
	public Double avgScore(int reviewrsId){
		return commentariesDao.avgScore(reviewrsId);
	}

	
	public int countCommentaries(Date startTime, Date endTime,int userAId,int userBId){
		return commentariesDao.countCommentaries(startTime, endTime,userAId,userBId);
	}
}
