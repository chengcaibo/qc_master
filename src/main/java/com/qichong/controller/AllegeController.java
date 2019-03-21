package com.qichong.controller;

import com.qichong.entity.AllegeInfo;
import com.qichong.enums.PathEnum;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.service.AllegeService;
import com.qichong.util.OSSUtil;
import com.qichong.util.PageHelper;
import com.qichong.util.web.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @Author ing
 *  用户被投诉后的申诉渠道
 */
@Controller
@RequestMapping("/allege")
public class AllegeController {

    @Autowired
    AllegeService allegeService;
    /**
     *
     * @param allegeInfo 申诉的信息
     * @param allegePicture 用户提交的申诉图片（可选项）
     * @return
     */
    @RequestMapping(path = "/allegeUpload",method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String allege(AllegeInfo allegeInfo, MultipartFile allegePicture){
        if(allegeInfo==null||allegeInfo.getUserId()==null){
            return JSONResult.builder(RetEnum.PARAM_LACK,"参数不完整").toJSON();
        }
        //因为图片是选填的，所以用户可能没有上传图片
        if(allegePicture!=null){

            System.out.println("allegePicture="+allegePicture.getOriginalFilename());
            String[] split = allegePicture.getOriginalFilename().split("\\.");
            //构建用户上传的图片的名字
            Integer userId = allegeInfo.getUserId();
            String name =userId+ UUID.randomUUID().toString()+"."+split[split.length-1];
            //将图片上传至阿里云
            boolean ret = this.certUploadToOSS(allegePicture, name);
            if(!ret){
                System.out.println("图片上传失败");
            }
            //将图片的名字放入到bean中
            allegeInfo.setAllegeImage(name);
        }
        Map map = allegeService.disposeAllege(allegeInfo);
        return JSONResult.builder(map);
    }
    /** 实名认证图片上传到阿里云OSS */
    private boolean certUploadToOSS(MultipartFile file, String name) {
        try {
            new OSSUtil(PathEnum.USER_ALLEGE).uploadFile2OSS(file.getInputStream(), name);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     *
     * @param userId 根据用户的userid返回用户的申诉结果
     * @return
     */
    @RequestMapping(path = "/allegeResult/{userId}",method = RequestMethod.GET, produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String allegeResult(@PathVariable("userId") Integer userId, PageHelper pageHelper){
        Map map = allegeService.getAllegeInfoByUserId(userId,pageHelper);
        return JSONResult.builder(map);
    }

    /**
     *
     *
     * @return 返回所有的申诉信息，供管理员审核
     */
    @RequestMapping(path = "/allegeInfo",method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String allegeInfo(){
        Map map = allegeService.getAllAllegeInfo();
        return JSONResult.builder(map);
    }
    /**
     *
     *
     * @return 管理员审核申诉信息后的结果
     */
    @RequestMapping(path = "/allegeDisposeResult",method = RequestMethod.POST, produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String diposeAllegeResult(Integer userId,String allegeResult){
        Map map = allegeService.findingsOfAudit(userId,allegeResult);
        return JSONResult.builder(map);
    }
    /**
     *根据用户的userId获取到该用户申诉失败的信息
     * @return
     */
    @RequestMapping(path="/getAllegeInfoFailed/{userId}",method = RequestMethod.GET,produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String getAllegeFailInfoByUserId(@PathVariable("userId")Integer userId,PageHelper pageHelper){
        Map map = allegeService.getAllegeInfoFailed(userId,pageHelper);
        return JSONResult.builder(map);
    }
    /**
     *根据用户的userId获取到该用户申诉成功的信息
     * @return
     */
    @RequestMapping(path="/getAllegeInfoSuccess/{userId}",method = RequestMethod.GET,produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String getAllegeSuccessInfoByUserId(@PathVariable("userId") Integer userId,PageHelper pageHelper){
        Map map = allegeService.getAllegeInfoSuccess(userId,pageHelper);
        return JSONResult.builder(map);
    }
    /**
     *接收用户的申诉要求，将用户的申诉状态改为成功(2),然后将用户拉出黑名单
     * @return
     */
    @RequestMapping(path="/accept",method = RequestMethod.POST,produces = ServletUtil.JSON_PRODUCES)
    @ResponseBody
    public String acceptAllege(Integer userId){
        Map map = allegeService.changeAllegeStateToSuccess(userId);
        return JSONResult.builder(map);
    }

}
