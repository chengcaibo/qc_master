package com.qichong.dao.test;

import com.qichong.entity.UserInfo;
import com.qichong.util.PageHelper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface GetAllUserInfoDao {
    @Select("select * from user_info limit #{limit} offset #{offset}")
    List<UserInfo> getAllUserInfo(Map<String, Object> pageHelper);
}
