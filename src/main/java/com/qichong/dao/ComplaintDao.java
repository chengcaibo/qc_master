package com.qichong.dao;

import com.qichong.entity.ComplaintVO;
import com.qichong.entity.Complaints;
import com.qichong.entity.UserInfo;
import com.qichong.util.PageHelper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface ComplaintDao {
    @Insert("insert into complaints values(null,#{complainant},#{respondent},#{complaintDetails},#{demandId},NOW(),0)")
    int insertComplaint(Complaints complaints);
    @Update("update user_info set reportedTimes=reportedTimes+1 where user_id=#{respondent}")
    int updateNumbersOfComplaints(Integer respondent);
    @Select("select id,user_id,nick_name,telephone,reportedTimes,reason from user_info where  blackList=1")
    List<UserInfo> getAllBackListUser();
    @Update("update user_info set blackList=0 where user_id=#{userId}")
    int updateBlackListByUserId(Integer userId);
    @Select("select c.id,c.complainant ,u1.nick_name complainantNickName,c.respondent,u2.nick_name respondentNickName,c.complaintDetails,c.demandId,d.content,c.createdTime from complaints c INNER JOIN user_info u1 INNER JOIN user_info u2 INNER JOIN demand_info d on(c.complainant=u1.user_id and c.respondent=u2.user_id and c.demandId=d.id) where u1.blackList=0 and u2.blackList=0 limit #{limit} offset #{offset}")
    List<ComplaintVO> getComplaintsList(Map<String, Object> pageHelper);
    @Select("select count(*) from complaints c INNER JOIN user_info u1 INNER JOIN user_info u2 INNER JOIN demand_info d on(c.complainant=u1.user_id and c.respondent=u2.user_id and c.demandId=d.id) where u1.blackList=0 and u2.blackList=0")
    int geComplaintsCount();
    @Update("update user_info set blackList=1,reason=#{reason} where user_id=#{userId}")
    int addToBlackList(@Param("userId") Integer userId, @Param("reason") String reason);
    @Select("select c.id,c.complainant ,u1.nick_name complainantNickName,c.respondent,u2.nick_name respondentNickName,c.complaintDetails,c.demandId,d.content,c.createdTime from complaints c INNER JOIN user_info u1 INNER JOIN user_info u2 INNER JOIN demand_info d on(c.complainant=u1.user_id and c.respondent=u2.user_id and c.demandId=d.id) where c.respondent=#{userId} limit #{limit} offset #{offset};")
    List<ComplaintVO> getComplaintsByUserId(Map<String, Object> pageHelper);
}
