package com.qichong.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qichong.entity.Commentaries;

/**
 * 
 * @author Administrator 评论实体类接口
 */
public interface CommentariesDao {
	
	
	/**
	 * 新增评论
	 * 
	 * @param commentaries
	 * @return boolean
	 */
	boolean insertCommentaries(@Param("commentaries") Commentaries commentaries);

	/**
	 * 修改评论信息
	 * 
	 * @param commentaries
	 * @return boolean
	 */
	boolean updateIdCommentaries(@Param("commentaries") Commentaries commentaries);

	/**
	 * 根据用户id查询单个用户所被评论信息
	 * 
	 * @param reviewersBId
	 * @return list
	 */
	List<Commentaries> queryUserCommentaries(@Param("reviewersBId") int reviewersBId);

	/**
	 * 根据评论id删除评论信息
	 * 
	 * @param commentariesId
	 * @return boolean
	 */
	boolean deleteCommentaries(@Param("commentariesId") int commentariesId);

	/**
	 * 计算平均分
	 * 
	 * @param reviewrsId
	 * @return
	 */
	Double avgScore(int reviewrsId);

	int countCommentaries(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
			@Param("userAId") int userAId, @Param("userBId") int userBId);

}
