package com.qichong.controller;

import com.google.gson.JsonObject;
import com.qichong.entity.Complaints;
import com.qichong.model.JSONResult;
import com.qichong.service.ComplaintService;
import com.qichong.util.PageHelper;
import com.qichong.util.web.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Author ing
 * 用户投诉
 */
@Controller
@RequestMapping("/complain")
public class ComplaintController {
    @Autowired
    ComplaintService complaintService;
    /**
     *@param complaints
     *
     * @return
     */
    @RequestMapping(path="/complaints",method = RequestMethod.POST,produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String complain(Complaints complaints){

        //投诉的信息先插入到数据库,更新被投诉者的信息，更新userinfo中的被举报次数
        Map result = complaintService.disposeComplaint(complaints);
        return JSONResult.builder(result);
    }

    /**
     *
     * @return 获取被投诉的黑名单
     */
    @RequestMapping(path="/blackList",method = RequestMethod.GET,produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String getBlackList(){
        Map map =complaintService.getAllBackList();
        return JSONResult.builder(map);
    }
    /**
     *
     * @return 根据userId获取该用户被投诉的所有信息
     */
    @RequestMapping(path="/complaintList/{userId}",method = RequestMethod.GET,produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String getComplaintListByUserId(@PathVariable("userId") Integer userId,PageHelper pageHelper){
        Map map =complaintService.getAllComplaintsList(userId,pageHelper);
        return JSONResult.builder(map);
    }

    /**
     *
     * @param userId 将该用户拖拽出黑名单
     * @return
     */
    @RequestMapping(path="/dragOut",method = RequestMethod.POST,produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String dragOutBlackList(Integer userId){
        Map map = complaintService.dragOutBlackListByUserId(userId);
        return JSONResult.builder(map);
    }
    /**
     *
     * @param userId 将该用户加入到黑名单
     * @return
     */
    @RequestMapping(path="/addtoblacklist",method = RequestMethod.POST,produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String addToBlackList(Integer userId,String reason){
        Map map = complaintService.addToBlackListByUserId(userId,reason);
        return JSONResult.builder(map);
    }
    /**
     *获取所有被投诉的用户列表
     * @return
     */
    @RequestMapping(path="/getComplaintList",method = RequestMethod.GET,produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String getAllComplaintList(PageHelper pageHelper){
        Map map = complaintService.getAllComplaints(pageHelper);
        return JSONResult.builder(map);
    }

}

