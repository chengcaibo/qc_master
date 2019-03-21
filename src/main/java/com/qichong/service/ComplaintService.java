package com.qichong.service;

import com.qichong.dao.ComplaintDao;
import com.qichong.entity.ComplaintVO;
import com.qichong.entity.Complaints;
import com.qichong.entity.UserInfo;
import com.qichong.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ComplaintService {
    @Autowired
    ComplaintDao complaintDao;

    public  Map disposeComplaint(Complaints complaints) {
        Map<String,String> map = new HashMap();
        //将被投诉的信息插入到数据库中
        int ret = complaintDao.insertComplaint(complaints);
        //更新被投诉用户的被投诉次数
        int result = complaintDao.updateNumbersOfComplaints(complaints.getRespondent());
        if(ret==0){
            map.put("ret","0");
            map.put("msg","投诉信息插入失败");
        }else if(result==0){
            map.put("ret","0");
            map.put("msg","更新用户的被投诉次数失败");
        }else{
            map.put("ret","1");
            map.put("msg","投诉成功");
        }
        return map;
    }

    public Map getAllBackList() {
        Map<String,Object> map = new HashMap();
        List<UserInfo> userInfos =  complaintDao.getAllBackListUser();
        map.put("code",0);
        map.put("count",userInfos.size());
        map.put("data",userInfos);
        return map;
    }

    public Map dragOutBlackListByUserId(Integer userId) {
        Map<String,Object> map = new HashMap();
        int ret = complaintDao.updateBlackListByUserId(userId);
        if(ret>0){
            map.put("ret",1);
            map.put("msg","解除黑名单成功");
        }else{
            map.put("ret",0);
            map.put("msg","解除黑名单失败");
        }
        return map;
    }

    public Map getAllComplaints(PageHelper pageHelper) {
        Map map = new HashMap<>();
        List<ComplaintVO> complaintVOS = complaintDao.getComplaintsList(pageHelper.getParams());
        int count =complaintDao.geComplaintsCount();
        if(complaintVOS==null||"".equals(complaintVOS)){
            map.put("code",1);
            map.put("count",0);
        }else{
            map.put("code",0);
            map.put("count",count);
            map.put("data",complaintVOS);
        }
        return map;
    }

    public Map addToBlackListByUserId(Integer userId, String reason) {
        Map map = new HashMap<>();
        Integer ret= ret = complaintDao.addToBlackList(userId,reason);

        if(ret>0){
            map.put("ret",1);
            map.put("msg","拉黑成功");
        }else{
            map.put("ret",0);
            map.put("msg","拉黑失败");
        }
        return map;
    }

    public Map getAllComplaintsList(Integer userId, PageHelper pageHelper) {
        Map map = new HashMap<>();
        Map pageHelpers = pageHelper.getParams();
        pageHelpers.put("userId",userId);
        List<ComplaintVO> complaintVOS = complaintDao.getComplaintsByUserId(pageHelpers);
        map.put("code",0);
        map.put("count",complaintVOS.size());
        map.put("data",complaintVOS);
        return  map;
    }


}
