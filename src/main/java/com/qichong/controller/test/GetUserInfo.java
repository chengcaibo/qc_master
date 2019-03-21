package com.qichong.controller.test;

import com.qichong.entity.UserInfo;
import com.qichong.model.JSONResult;
import com.qichong.service.test.GetAllUserInfoService;
import com.qichong.util.PageHelper;
import com.qichong.util.web.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GetUserInfo {

    @Autowired
    GetAllUserInfoService getAllUserInfoService;

    /**
     *
     * @param pageHelper 这是个分页工具 用来返回指定条数的信息
     * @return
     */
    @RequestMapping(value = "/getallinfo",method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String getAllUserInfo(PageHelper pageHelper){
        Map<String,Object> map =  new HashMap<String,Object>();
        List<UserInfo> userInfos= getAllUserInfoService.getAllInfo(pageHelper);
        map.put("code",0);
        map.put("count",20);
        map.put("data",userInfos);
        return JSONResult.builder(map);
    }
}
