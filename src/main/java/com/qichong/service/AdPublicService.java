package com.qichong.service;

import static com.qichong.util.web.ServletUtil.isEmpty;
import static com.qichong.util.web.ServletUtil.queryAllChildCodes;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qichong.dao.AdPublicDao;
import com.qichong.entity.AdPublic;
import com.qichong.entity.State;
import com.qichong.entity.Users;
import com.qichong.enums.PathEnum;
import com.qichong.enums.RetEnum;
import com.qichong.model.AdpublicInfoModel;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.util.OSSUtil;
import com.qichong.util.Utils;

/**
 * 公开小广告Service层
 *
 * @创建人 吴志伟
 * @修改人 孙建雷
 * @修改时间 2017-11-9 17:34:08
 */
@Service
public class AdPublicService {

	@Autowired
	AdPublicDao adPublicDao;
	@Autowired
	SharedTypeService sharedTypeService;

	/** 搜索多条记录 */
	private List<AdPublic> searchList(Filters filters) {
		// 将搜索某项的所有子级
		filters.setIndustryCodes(queryAllChildCodes(filters.getSharedCode(), sharedTypeService));
		return adPublicDao.selectAllByFilter(filters);
	}

	/** 搜索一条记录 */
	private AdPublic searchOne(Filters filters) {
		List<AdPublic> list = this.searchList(filters);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	/** 搜索 */
	public List<AdPublic> search(Filters filters) {
		return this.searchList(filters);
	}

	/** 根据广告ID查询单个广告 */
	public AdPublic queryOneByAdId(int adId) {
		// 构造Filter
		Filters filters = new Filters();
		filters.setAdId(adId);
		// 执行查询
		return this.searchOne(filters);
	}

	/** 根据用户ID查询广告 */
	public List<AdPublic> queryListByUserId(int userId) {
		// 构造Filter
		Filters filters = new Filters();
		filters.setUserId(userId);
		// 执行查询
		return this.searchList(filters);
	}

	/**
	 * 根据 UserId查询广告信息
	 */
	public AdPublic queryOneByuserId(int userId) {
		// 构造Filter
		Filters filters = new Filters();
		filters.setUserId(userId);
		return this.searchOne(filters);
	}

	/** 查询全部个人发布广告信息 用于首页更多 */
	public List<AdPublic> queryUserInfoAdpublicMore() {
		return adPublicDao.queryUserInfoAdpublicMore();
	}

	/** 查询全部个人发布广告信息 用于首页更多 */
	public List<AdpublicInfoModel> selAdpublicAll(Filters filters) {
		return adPublicDao.selAdpublicAll(filters);
	}

	/** 查询全部企业发布广告信息 用于首页更多 */
	public List<AdPublic> queryEnterpriseAdpublicMore() {
		return adPublicDao.queryEnterpriseAdpublicMore();
	}

	/**
	 * 查询全部并筛选
	 */
	public List<AdPublic> queryAllAndFilter(Filters filters) {
		return adPublicDao.selectAllAndFilter(filters);
	}

	/** 根据 UserId查询广告信息 List */
	public List<AdPublic> queryAdpublic(int userId) {
		return adPublicDao.selAdpublic(userId);
	}

	public boolean delAdpublic(int id) {
		return adPublicDao.deleteOne(id) > 0;
	}

	/**
	 * 添加或者修改小广告
	 * 
	 * @param adPublic
	 *            广告的具体内容
	 * @param coverFile
	 *            小广告的封面图
	 */
	public JSONResult addOrChange(AdPublic adPublic, MultipartFile coverFile) {
		JSONResult jr = new JSONResult();

		// 检查参数
		if (isEmpty(adPublic.getContent(), adPublic.getPhone(), adPublic.getAddress()) > 0) {
			jr.setValue(RetEnum.PARAM_LACK, "缺少参数");
			return jr;
		}
		// 查询此人是否发布过广告
		AdPublic temp = this.queryOneByuserId(adPublic.getUser().getId());
		// 查询为空，未发布过广告，新增一条数据
		if (temp == null) {
			return this.submitOneAdPublic(adPublic, coverFile);
		} else {
			// 否则就是修改
			adPublic.setPicture(temp.getPicture().getName());
			return this.changeAdPublic(adPublic, coverFile);
		}
	}

	/**
	 * 修改广告，当某个用户已经发过一次广告，那么之后只能修改，根据userId修改
	 */
	private JSONResult changeAdPublic(AdPublic adPublic, MultipartFile coverFile) {
		JSONResult jr = new JSONResult();
		try {
			// 默认值
			adPublic.setState(new State(1));
			adPublic.setTime(new Date());
			// 上传到阿里云OSS
			if (!coverFile.isEmpty()) {
				OSSUtil oss = new OSSUtil(PathEnum.AD_PUBLIC);
				String name = Utils.urlRemoveParameter(adPublic.getPicture().getName());
				oss.uploadFile2OSS(coverFile.getInputStream(), name);
				adPublic.setPicture(name + "?" + Utils.randomNumber(6));
			}
			adPublicDao.updateOne(adPublic);
			jr.setValue(RetEnum.SUCCESS);
		} catch (IOException e) {
			e.printStackTrace();
			jr.setValue(RetEnum.EXCEPTION, "服务器匆忙");
		}
		return jr;
	}

	/** 更新一条广告记录 */
	public JSONResult submitOrUpdate(AdPublic ad) {
		JSONResult jr = new JSONResult();
		try {
			if (ad.getId() == null) {
				// 默认值
				ad.setState(new State(1));
				ad.setTime(new Date());
				ad.setPicture("uploading.jpg?1");
				// 向数据库中插入一条数据
				adPublicDao.insertOne(ad);
				jr.setValue(RetEnum.SUCCESS, ad.getId());
			} else {
				ad.setTime(new Date());
				// 向数据库中更新一条数据状态
				if (adPublicDao.updateOne(ad) > 0) {
					jr.setValue(RetEnum.SUCCESS, ad.getId());
				} else {
					jr.setValue(RetEnum.AUTH_ERROR, "没有权限");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			jr.setValue(RetEnum.EXCEPTION, "服务器匆忙");
		}
		return jr;
	}

	/** 更新一条广告记录 */
	public JSONResult updateOne(AdPublic adpublic) {
		JSONResult jr = new JSONResult();
		try {
			// 向数据库中更新一条数据状态
			if (adPublicDao.updateOne(adpublic) > 0) {
				jr.setValue(RetEnum.SUCCESS);
			} else {
				jr.setValue(RetEnum.AUTH_ERROR, "没有权限");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jr.setValue(RetEnum.EXCEPTION, "服务器匆忙");
		}
		return jr;
	}

	private static OSSUtil ossUtil = new OSSUtil(PathEnum.AD_PUBLIC);

	/** 更改广告的封面图片 */
	public JSONResult uploadPicture(Integer id, Integer userId, MultipartFile file, String picName) throws IOException {
		JSONResult jr = new JSONResult();
		if (file == null || file.isEmpty()) {
			jr.setValue(RetEnum.PARAM_LACK);
		} else {
			// 获取图片名称
			String random = id + Utils.randomString(32).toLowerCase() + ".jpg";
			picName = isEmpty(picName) ? random : Utils.getFileName(picName);
			picName = isEmpty(picName) ? random : picName;
			// 更新到数据库
			AdPublic ad = new AdPublic(id);
			ad.setUser(new Users(userId));
			ad.setPicture(picName + "?" + Utils.randomNumber(6));
			jr = this.updateOne(ad);
			// 更新成功后再上传图片
			if (jr.getRet() == RetEnum.SUCCESS.getValue()) {
				ossUtil.uploadFile2OSS(file.getInputStream(), picName);
			}
		}
		return jr;

	}

	/**
	 * 发布一条广告
	 * 
	 * @param adPublic
	 *            待发布的广告对象
	 * @param coverFile
	 *            小广告封面
	 * @return 执行结果 JSONResult
	 */
	private JSONResult submitOneAdPublic(AdPublic adPublic, MultipartFile coverFile) {
		JSONResult jr = new JSONResult();
		try {
			if (coverFile.isEmpty())
				jr.setValue(RetEnum.PARAM_LACK, "缺少参数");
			else {
				// 默认值
				adPublic.setState(new State(1));
				adPublic.setTime(new Date());
				// 上传到阿里云OSS
				String name = "ad_" + Utils.randomNumber(16) + ".jpg";
				new OSSUtil(PathEnum.AD_PUBLIC).uploadFile2OSS(coverFile.getInputStream(), name);
				adPublic.setPicture(name);
				// 向数据库中插入一条数据
				adPublicDao.insertOne(adPublic);
				jr.setValue(RetEnum.SUCCESS);
			}
		} catch (IOException e) {
			e.printStackTrace();
			jr.setValue(RetEnum.EXCEPTION, "服务器匆忙");
		}
		return jr;
	}

	/**
	 * 修改广告信息
	 */
	public boolean editAdPublic(AdPublic adPublic) {
		return adPublicDao.updateOne(adPublic) > 0;
	}

	/**
	 * 查询个人用户发布的广告
	 */
	public List<AdPublic> queryAdPersonal(int limit, int offset, String city) {
		return adPublicDao.queryAdPublicUserMaxFive(limit, offset, city);
	}

	/**
	 * 查询企业用户发布的广告
	 */

	public List<AdPublic> queryAdEnterprise(int limit, int offset, String city) {
		return adPublicDao.queryAdPublicEnterpriseMaxFive(limit, offset, city);
	}

	/**
	 * 查询五条Id最大的五条广告
	 */
	public List<AdPublic> queryAdPublicMaxFive(int limit, int offset, String city) {
		return adPublicDao.queryAdPublicMaxFive(limit, offset, city);
	}

	/**
	 * 根据Id查询用户所发布过的广告
	 */
	public List<AdPublic> byUserIdAdPublic(int userId) {
		return adPublicDao.byUserIdAdPublic(userId);
	}

	/**
	 * 查询全部
	 */
	public List<AdPublic> queryAdPublic() {
		return adPublicDao.queryAdPublic();
	}

	/**
	 * 查询单个广告详细信息
	 * 
	 * @param adPublicId
	 * @return
	 */
	public AdPublic queryOneAdpublic(int adPublicId) {
		return adPublicDao.queryOneAdpublic(adPublicId);
	}

	/**
	 * 查询广告详情
	 * 
	 * @param adPublicId
	 * @return
	 */
	public AdPublic queryAdpublicInfo(Integer id) {
		return adPublicDao.queryAdpublicInfo(id);
	}

	/**
	 * 查询你可能喜欢
	 * 
	 * @param content
	 * @param ruleOutId
	 *            排除Id
	 * @return
	 */
	public List<AdPublic> queryLike(String content, int ruleOutId) {
		return adPublicDao.queryLike(content, ruleOutId);
	}

}