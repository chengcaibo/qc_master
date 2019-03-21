package com.qichong.service;

import com.aliyuncs.exceptions.ClientException;
import com.qichong.dao.PrizeDrawDao;
import com.qichong.entity.ActivityState;
import com.qichong.entity.AwardWinnerInfo;
import com.qichong.entity.UserInfo;
import com.qichong.util.WinningCodeUtil;
import com.qichong.util.redisutil.JedisAdapter;
import com.qichong.util.sms.SMSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ing
 * 新年活动抽奖的service层
 */
@Service
public class PrizeDrawService {

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    PrizeDrawDao prizeDrawDao;
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    WinningCodeUtil winningCodeUtil;

    /**
     * 抽到中奖者后，应该将中奖者的信息保存到数据库中
     * @param userIds 被抽到的中奖者的userId
     * @param winningAmount 中奖者的中奖金额
     * @return 返回插入情况
     */
    public Map insertAwardWinners(List<String> userIds,Integer winningAmount){
        HashMap<String, String> map = new HashMap<>();
        AwardWinnerInfo awardWinnerInfo = new AwardWinnerInfo();
        for (String userId:userIds) {
            UserInfo userInfo = userInfoService.queryUserInfo(Integer.parseInt(userId));
            awardWinnerInfo.setUserId(userInfo.getUser().getId());
            awardWinnerInfo.setTelephone(userInfo.getTelephone());
            awardWinnerInfo.setWinningAmount(winningAmount);
            awardWinnerInfo.setWinningCode(winningCodeUtil.createWinningCode());
            Integer ret = prizeDrawDao.insertAwardWinner(awardWinnerInfo);
            //向中奖者发送短信
            try {
                SMSUtil.winningNotify(userInfo.getTelephone(),Integer.parseInt(jedisAdapter.get("code")));
            } catch (Exception e) {
                map.put("msg","短信发送失败");
            }
            if(ret>0){
                System.out.println("用户id为"+userId+" 插入成功");
            }else{
                System.out.println("用户id为"+userId+" 插入失败");
            }
        }
        map.put("ret","success");
        return map;
    }

    /**
     *
     * @param activity 根据用户的userId来查询出参与者的信息，这里需要查询的有20条信息
     * @return 返回根据userid来查询到的用的用户的信息
     */
    public List<UserInfo> queryParticipator(List<String> activity) {

        List<UserInfo> userInfos = new ArrayList<>();
        for (String userId:activity) {
            UserInfo userInfo = userInfoService.queryUserInfo(Integer.parseInt(userId));
            userInfos.add(userInfo);
        }
        return userInfos;
    }

    /**
     *
     * @param amount 中奖的金额
     * @return 根据中奖的金额来查询该中奖金额下的中奖用户信息，包括电话号，中奖吗等等
     */
    public Map<String, Object> queryAwardWinners(Integer amount) {
        HashMap<String, Object> map = new HashMap<>();
        List<AwardWinnerInfo> awardWinnerInfos =  prizeDrawDao.queryAwardWinnersByWinningAmount(amount);
        if(awardWinnerInfos==null||"".equals(awardWinnerInfos)){
            map.put("ret","empty");
        }else{
            map.put("ret",awardWinnerInfos);
        }
        return map;
    }

    /**
     *
     * @param userId 要查询的中奖用户的userId
     * @return 返回中奖信息，是否中奖，中了多少钱，以及要加的微信号是什么。
     */
    public Map queryCurrrentParticipatorState(Integer userId) {
        HashMap<String, Object> map = new HashMap<>();
        //根基用户的userId去中奖名单里查找中奖信息
        List<AwardWinnerInfo> awardWinnerInfo=null;
  /*      if(total>=50000){
            awardWinnerInfo= prizeDrawDao.queryAwardWinnerStateByUserId(userId,8000);
        }else if(total>=10000 && total<50000){
            awardWinnerInfo= prizeDrawDao.queryAwardWinnerStateByUserId(userId,500);
        }else if(total>=5000 && total<10000){
            awardWinnerInfo= prizeDrawDao.queryAwardWinnerStateByUserId(userId,200);
        }else if(total>=2000 && total<5000){
            awardWinnerInfo= prizeDrawDao.queryAwardWinnerStateByUserId(userId,80);
        }else if(total>=1200 && total<2000){
            awardWinnerInfo= prizeDrawDao.queryAwardWinnerStateByUserId(userId,50);
        }else if(total>=800 && total<1200){
            awardWinnerInfo= prizeDrawDao.queryAwardWinnerStateByUserId(userId,30);
        }else if(total>=500 && total<800){
            awardWinnerInfo= prizeDrawDao.queryAwardWinnerStateByUserId(userId,20);
        }else if(total>=200 && total<500){
            awardWinnerInfo= prizeDrawDao.queryAwardWinnerStateByUserId(userId,10);
        }else if(total>=100 && total<200){
            awardWinnerInfo= prizeDrawDao.queryAwardWinnerStateByUserId(userId,5);
        }*/
        //中奖情况
        awardWinnerInfo = prizeDrawDao.queryAwardWinnerStateByUserId(userId);
        if(awardWinnerInfo.size()>0){
            map.put("ret","success");
            /*map.put("amount",winningAmount);
            //根据中奖金额查询对应的微信号，不同轮次的添加不同的微信号
            String wxcode = prizeDrawDao.queryWxCodeByWinningAmount(winningAmount);
            map.put("wxcode",wxcode);*/
            map.put("info",awardWinnerInfo);
        }else{
            //没有中奖的情况
            map.put("ret","fail");
        }
        return map;
    }

    /**
     *  查询活动是否开启，如果已经开启，返回start
     * @return
     */
    public Map queryActivityState() {
        HashMap<String, Object> map = new HashMap<>();
        ActivityState activityState = prizeDrawDao.queryState();

        map.put("activityState",activityState.getStart());

        return map;
    }
}
