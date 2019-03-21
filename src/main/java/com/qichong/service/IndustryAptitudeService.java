package com.qichong.service;

import static com.qichong.util.web.ServletUtil.isEmpty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qichong.dao.IndustryAptitudeDao;
import com.qichong.entity.IndustryAptitude;
import com.qichong.enums.PathEnum;
import com.qichong.enums.RetEnum;
import com.qichong.model.JSONResult;
import com.qichong.util.OSSUtil;
import com.qichong.util.Utils;

/**
 * 行业 资质 Service层
 * 
 * @创建人 吴志伟
 * @修改人 孙建雷
 * @修改时间 2017年11月9日 下午5:38:34
 */
@Service
public class IndustryAptitudeService {

	@Autowired
	IndustryAptitudeDao dao;

	private static OSSUtil ossUtil = new OSSUtil(PathEnum.USER_CERT);

	/** 修改资质 */
	public JSONResult updateOne(MultipartFile coverFile, IndustryAptitude aptitude, String pictureName) {
		JSONResult jr = new JSONResult();
		try {
			if (isEmpty(aptitude.getText())) {// 判断参数是否完整
				jr.setValue(RetEnum.PARAM_LACK, "缺少参数");
			} else {
				pictureName = Utils.getFileName(pictureName);
				aptitude.setPicture(pictureName + "?" + Utils.randomNumber(6));
				if (dao.updateOne(aptitude) > 0) {
					jr.setValue(RetEnum.SUCCESS, "修改成功");
					if (coverFile != null) { // 修改发布到阿里云OSS
						ossUtil.uploadFile2OSS(coverFile.getInputStream(), pictureName);
					}
				} else {
					jr.setValue(RetEnum.AUTH_ERROR, "没有权限");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			jr.setValue(RetEnum.EXCEPTION, e.getMessage());
		}
		return jr;
	}

	/** 删除资质 */
	public JSONResult deleteOne(int id, int userId, String pictureName) {
		JSONResult jr = new JSONResult();
		if (isEmpty(pictureName)) {
			jr.setValue(RetEnum.PARAM_LACK, "图片名称不能为空");
		} else if (dao.deleteOne(id, userId) > 0) {
			ossUtil.deleteOneFile(Utils.getFileName(pictureName)); // 删除OSS上的图片
			jr.setValue(RetEnum.SUCCESS, "删除成功");
		} else {
			jr.setValue(RetEnum.AUTH_ERROR, "没有权限");
		}
		return jr;
	}

	/** 查询单个用户的资质 */
	public List<IndustryAptitude> queryUserIdAptitude(int userId) {
		return dao.queryUserIdAptitude(userId);
	}

	/** 新增用户资质信息 */
	public JSONResult insertOne(MultipartFile coverFile, IndustryAptitude aptitude) {
		JSONResult jr = new JSONResult();
		try {
			if (isEmpty(aptitude.getText()) || coverFile == null) // 判断参数是否完整
				jr.setValue(RetEnum.PARAM_LACK, "缺少参数");
			else {
				String name = aptitude.getUser().getId() + Utils.randomString(32) + ".jpg";
				ossUtil.uploadFile2OSS(coverFile.getInputStream(), name); // 发布到阿里云OSS
				aptitude.setPicture(name + "?" + Utils.randomNumber(6));
				// 插入数据
				dao.insertOne(aptitude);
				jr.setValue(RetEnum.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jr.setValue(RetEnum.EXCEPTION, e.getMessage());
		}
		return jr;
	}

}
