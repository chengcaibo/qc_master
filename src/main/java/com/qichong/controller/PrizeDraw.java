package com.qichong.controller;

import com.google.gson.JsonObject;
import com.qichong.entity.AwardWinnerInfo;
import com.qichong.entity.UserInfo;
import com.qichong.entity.Users;
import com.qichong.model.JSONResult;
import com.qichong.service.PrizeDrawService;
import com.qichong.service.UsersService;
import com.qichong.token.LoginToken;
import com.qichong.util.redisutil.JedisAdapter;
import com.qichong.util.web.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PrizeDraw {

    @Autowired
    UsersService usersService;
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    PrizeDrawService prizeDrawService;
    /**
     *
     * @param token 参与活动得的用户标识
     * @return 返回用户是否参与成功
     */
    @RequestMapping(path ="/activity/participate", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String participateActivity(HttpSession session,LoginToken token){
        Map<String, String> map = new HashMap<>();
        //参与活动之前首先判断用户是否已经登录，没有登录，则返回没有登录的提示信息
        System.out.println("开始助力");
        Users loginUser = ServletUtil.getThisLoginUser(session, token, usersService);
        if (loginUser==null||loginUser.getId() == null){
            return JSONResult.noLogin().toJSON();
        }
        System.out.println("助力人员id"+loginUser.getId());
        long activity = jedisAdapter.sadd("activity", token.getUserId().toString());
        if(activity>0){
            map.put("ret","success");
            Integer total = (int)jedisAdapter.scard("activity");
            prizeDraw(total);
        }else {
            map.put("ret","fail");
        }

        long activity1 = jedisAdapter.scard("activity");
        System.out.println("当前参加的用户数是:"+activity1);
        //如果没有参加过这个活动的话，那么就将该用户的userid放入到参与者的名单中来
        return JSONResult.builder(map);
    }

    /**
     *
     * @param userId 根据userid来获取到当前请求是用户未登录时的请求还是用户登录以后的请求
     *               未登录的时候，那么默认用户没有参加过该活动，那么该用户在数据库是不存在的
     *
     * @return 返回用户是否已经参加该活动
     */
    @RequestMapping(path ="/activity/activityCurrentState", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String activityCurrentState(@RequestParam("userId") Integer userId){
            Map<String, String> map = new HashMap<>();
        //用户没有登录的时候，默认没有参加该活动
        if(userId==null||"".equals(userId)){
            map.put("ret","notexist");
            System.out.println("该用户么有登录，所以不存在");
            return JSONResult.builder(map);

        }
        //如果登录成功的话，那么判断是否已经参加过这个活动，即查询redis中是否有对应的userid
        boolean activity = jedisAdapter.sismember("activity", userId.toString());
        System.out.println("该用户存在吗？ "+activity);

        if(activity){
            map.put("ret","exist");
        }else{
            map.put("ret","notexist");
        }
        return JSONResult.builder(map);
    }

    /**
     *
     * @return 获取到参加活动的总人数
     */
    @RequestMapping(path ="/activity/total", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String participatorTotal(){
        Map<String, Object> map = new HashMap<>();
        //返回现在已经参加活动的总人数，通过查询redis数据库中的userid的数量来返回对应的数量
        Integer activity1 = (int)jedisAdapter.scard("activity");
        map.put("amount",activity1);
        if( activity1>50000){
            map.put("dest",50001);
            map.put("one_round","1万");
            map.put("two_rounds","2万");
            map.put("three_rounds","3万");
            map.put("four_rounds","4万");
            map.put("five_rounds","5万");
            map.put("bounty","iPhone X");
            map.put("percent",(activity1*100)/5000);
        } else if(activity1>=10000 && activity1<=50000){
            map.put("dest",50000);
            map.put("one_round","1万");
            map.put("two_rounds","2万");
            map.put("three_rounds","3万");
            map.put("four_rounds","4万");
            map.put("five_rounds","5万");
            map.put("bounty","iPhone X");
            map.put("percent",(activity1*100)/5000);
        }else if(activity1>=5000 && activity1<10000){
            map.put("dest",10000);
            map.put("one_round",2000);
            map.put("two_rounds",4000);
            map.put("three_rounds",6000);
            map.put("four_rounds",8000);
            map.put("five_rounds","1万");
            map.put("bounty","500元");
            map.put("percent",(activity1*100)/10000);
        }else if(activity1>=2000 && activity1<5000){
            map.put("dest",5000);
            map.put("one_round",1000);
            map.put("two_rounds",2000);
            map.put("three_rounds",3000);
            map.put("four_rounds",4000);
            map.put("five_rounds",5000);
            map.put("bounty","200元");
            map.put("percent",(activity1*100)/5000);
        }else if(activity1>=1200 && activity1<2000){
            map.put("dest",2000);
            map.put("one_round",400);
            map.put("two_rounds",800);
            map.put("three_rounds",1200);
            map.put("four_rounds",1600);
            map.put("five_rounds",2000);
            map.put("bounty","100元");
            map.put("percent",(activity1*100)/2000);
        }else if(activity1>=800 && activity1<1200){
            map.put("dest",1200);
            map.put("one_round",240);
            map.put("two_rounds",480);
            map.put("three_rounds",720);
            map.put("four_rounds",960);
            map.put("five_rounds",1200);
            map.put("bounty","50元");
            map.put("percent",(activity1*100)/1200);
        }else if(activity1>=500 && activity1<800){
            map.put("dest",800);
            map.put("one_round",160);
            map.put("two_rounds",320);
            map.put("three_rounds",480);
            map.put("four_rounds",640);
            map.put("five_rounds",800);
            map.put("bounty","30元");
            map.put("percent",(activity1*100)/800);
        }else if(activity1>=200 && activity1<500){
            map.put("dest",500);
            map.put("one_round",100);
            map.put("two_rounds",200);
            map.put("three_rounds",300);
            map.put("four_rounds",400);
            map.put("five_rounds",500);
            map.put("bounty","20元");
            map.put("percent",(activity1*100)/500);
        }else if(activity1>=100 && activity1<200){
            map.put("dest",200);
            map.put("one_round",40);
            map.put("two_rounds",80);
            map.put("three_rounds",120);
            map.put("four_rounds",160);
            map.put("five_rounds",200);
            map.put("bounty","10元");
            map.put("percent",(activity1*100)/200);
        }else if(activity1<100){
            map.put("dest",100);
            map.put("one_round",20);
            map.put("two_rounds",40);
            map.put("three_rounds",60);
            map.put("four_rounds",80);
            map.put("five_rounds",100);
            map.put("bounty","5元");
            map.put("percent",(activity1*100)/100);
        }
        return JSONResult.builder(map);
    }

    public void prizeDraw(Integer winningtotal){
        //根据不同的中奖率来抽奖，返回被抽中的userid
        if(winningtotal==100){
            List<String> userIds = jedisAdapter.srandmember("activity", 100);
            //根据userid来查询出抽奖者的信息，然后给这些用户发短信(附带中奖编号)，并将该信息保存到中奖名单的数据库中，该中奖名单应该标明这是抽多少钱的
            Map map = prizeDrawService.insertAwardWinners(userIds,5);
        }else if(winningtotal==200){
            List<String> userIds = jedisAdapter.srandmember("activity", 160);
            Map map = prizeDrawService.insertAwardWinners(userIds,10);
        }else if(winningtotal==500){
            List<String> userIds = jedisAdapter.srandmember("activity", 150);
            Map map = prizeDrawService.insertAwardWinners(userIds,20);
        }else if(winningtotal==800){
            List<String> userIds = jedisAdapter.srandmember("activity", 160);
            Map map = prizeDrawService.insertAwardWinners(userIds,30);
        }else if(winningtotal==1200){
            List<String> userIds = jedisAdapter.srandmember("activity", 100);
            Map map = prizeDrawService.insertAwardWinners(userIds,50);
        }else if(winningtotal==2000){
            List<String> userIds = jedisAdapter.srandmember("activity", 50);
            Map map = prizeDrawService.insertAwardWinners(userIds,100);
        }else if(winningtotal==5000){
            List<String> userIds = jedisAdapter.srandmember("activity", 3);
            Map map = prizeDrawService.insertAwardWinners(userIds,200);
        }else if(winningtotal==10000){
            List<String> userIds = jedisAdapter.srandmember("activity", 2);
            Map map = prizeDrawService.insertAwardWinners(userIds,500);
        }else if(winningtotal==50000){
            List<String> userIds = jedisAdapter.srandmember("activity", 1);
            Map map = prizeDrawService.insertAwardWinners(userIds,8000);
        }

    }

    /**
     * 参与活动的活动者名单，这里选最新的20条
     * @return
     */
    @RequestMapping(path ="/activity/participators", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String participators(){
        HashMap<String, Object> map = new HashMap<>();
        List<String> activity = jedisAdapter.srandmember("activity", 20);
        //如果此时没有人参加活动的话，那么activity应该为空
        if(activity==null||"".equals(activity)){
            map.put("ret","empty");
        }else{
            List<UserInfo> userInfos = prizeDrawService.queryParticipator(activity);
            map.put("ret",userInfos);
        }
        return JSONResult.builder(map);
    }

    /**
     *
     * @param amount 要查询该中奖额的对应中奖名单
     * @return 返回对应的中奖名单
     */
    @RequestMapping(path ="/activity/winner/{amount}", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String winner(@PathVariable("amount")Integer amount){
        //根据抽奖的金额来返回中奖者的名单，直接从数据库中进行查询
        Map awardWinnerInfos = prizeDrawService.queryAwardWinners(amount);
        return JSONResult.builder(awardWinnerInfos);
    }

    /**
     *
     * @param userId 根据当前用户的userId来查询用户是否中奖
     * @return 返回是否中奖的信息
     */
    @RequestMapping(path ="/activity/winner/win", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String winningState(Integer userId){
        //Integer total = (int)jedisAdapter.scard("activity");
        Map winningState = prizeDrawService.queryCurrrentParticipatorState(userId);
        return JSONResult.builder(winningState);
    }
    /**
     * 查询活动是否已经开启
     * @return
     */
    @RequestMapping(path ="/activity/start", method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String activityStart(){
        Map state = prizeDrawService.queryActivityState();
        return JSONResult.builder(state);
    }
}
