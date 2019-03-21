package com.qichong.service.test;

import com.qichong.dao.test.GetAllUserInfoDao;
import com.qichong.entity.UserInfo;
import com.qichong.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllUserInfoService {
    @Autowired
    GetAllUserInfoDao getAllUserInfoDao;
    public List<UserInfo> getAllInfo(PageHelper pageHelper) {

        return getAllUserInfoDao.getAllUserInfo(pageHelper.getParams());
    }
}
