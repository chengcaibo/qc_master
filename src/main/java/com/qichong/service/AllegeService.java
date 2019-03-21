package com.qichong.service;

import com.qichong.dao.AllegeDao;
import com.qichong.entity.AllegeInfo;
import com.qichong.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AllegeService {
    @Autowired
    AllegeDao allegeDao;

    public Map disposeAllege(AllegeInfo allegeInfo) {
        Map<String,String> map = new HashMap<>();
        int ret =allegeDao.insertAllegeInfo(allegeInfo);
        if(ret>0){
            map.put("ret","1");
            map.put("msg","插入申诉信息成功");
        }else{
            map.put("ret","0");
            map.put("msg","插入申诉信息失败");
        }
        return map;
    }

    public Map getAllAllegeInfo() {
        Map<String,Object> map = new HashMap<>();
        List<AllegeInfo> allegeInfos = allegeDao.getAllInfo();

        if(allegeInfos==null){
            map.put("ret",0);
        }else{
            map.put("ret",allegeInfos);
        }
        return map;
    }

    /**
     *  获取没有被审核的用户的申诉信息
     * @param userId
     * @param pageHelper
     * @return
     */
    public Map getAllegeInfoByUserId(Integer userId, PageHelper pageHelper) {
        Map<String,Object> map = new HashMap();
        Map pageHelperParams =pageHelper.getParams();
        pageHelperParams.put("userId",userId);
        pageHelperParams.put("allegeState",0);
        List<AllegeInfo> allegeInfo = allegeDao.getAllegeInfoByUserId(pageHelperParams);
        map.put("code",0);
        map.put("count",allegeInfo.size());
        map.put("data",allegeInfo);
        return map;
    }

    /**
     * 审核结果
     * @return
     * @param userId
     * @param allegeResult
     */
    public Map findingsOfAudit(Integer userId, String allegeResult) {
        Map<String,String> map = new HashMap<>();
        Integer allegeState =1;
        int ret = allegeDao.updateAllegeResult(userId,allegeResult,allegeState);
        if(ret>0){
            map.put("msg","更新审核结果成功");
            map.put("ret","1");
        }else{
            map.put("msg","更新审核结果失败");
            map.put("ret","0");
        }
        return map;
    }

    /**
     *
     * @param userId 要获取该用户失败的申诉信息
     * @param pageHelper
     * @return
     */
    public Map getAllegeInfoFailed(Integer userId, PageHelper pageHelper) {
        Map<String,Object> map = new HashMap<>();
        Map pageHelperParams=pageHelper.getParams();
        pageHelperParams.put("allegeState",1);
        pageHelperParams.put("userId",userId);
        List<AllegeInfo> allegeInfo = allegeDao.getAllegeInfoByUserId(pageHelperParams);
        map.put("code",0);
        map.put("count",allegeInfo.size());
        map.put("data",allegeInfo);
        return map;
    }

    public Map getAllegeInfoSuccess(Integer userId, PageHelper pageHelper) {
        Map<String,Object> map = new HashMap<>();
        Map pageHelperParams=pageHelper.getParams();
        pageHelperParams.put("allegeState",2);
        pageHelperParams.put("userId",userId);
        List<AllegeInfo> allegeInfo = allegeDao.getAllegeInfoByUserId(pageHelperParams);
        map.put("code",0);
        map.put("count",allegeInfo.size());
        map.put("data",allegeInfo);
        return map;
    }

    public Map changeAllegeStateToSuccess(Integer userId) {
        Map map = new HashMap<>();
        Integer allegeState=2;
        int ret = allegeDao.updateAllegeStateToSuccess(userId,allegeState);
        int result = allegeDao.dragOutBlackListByUserId(userId);
        if(ret>0&&result>0){
            map.put("ret",1);
            map.put("msg","申诉成功");
        }else{
            map.put("ret",0);
            map.put("msg","申诉失败");
        }
        return map;
    }
}
