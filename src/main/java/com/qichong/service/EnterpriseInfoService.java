package com.qichong.service;

import static com.qichong.util.web.ServletUtil.isEmpty;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.qichong.dao.CertificationEnterpriseDao;
import com.qichong.dao.EnterpriseInfoDao;
import com.qichong.entity.CertificationEnterprise;
import com.qichong.entity.EnterpriseInfo;
import com.qichong.entity.Users;
import com.qichong.enums.PathEnum;
import com.qichong.enums.RetEnum;
import com.qichong.model.EnterpriseTopOneModel;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.util.OSSUtil;
import com.qichong.util.Utils;
import com.qichong.util.web.ServletUtil;

/**
 * 企业信息Service层实现类
 *
 * @创建人 吴志伟
 * @修改人 孙建雷
 * @修改时间 2017-11-9 13:43:55
 */
@Service
public class EnterpriseInfoService {
	@Autowired
	IndustryTypeService industryService;
	@Autowired
	RegionsService regionService;

	@Autowired
	UsersService usersService;

	@Autowired
	EnterpriseInfoDao dao;

	/** ossUtil */
	private static OSSUtil ossUtil = new OSSUtil(PathEnum.ENT_LOGO);
	/** logo */
	private static OSSUtil ossUtil1 = new OSSUtil(PathEnum.ENT_LOGO);
	/** 营业执照 */
	private static OSSUtil ossUtil2 = new OSSUtil(PathEnum.ENT_LICENSE);
	/** 企业自上传 */
	private static OSSUtil ossUtil3 = new OSSUtil(PathEnum.ENT_PICTURE);

	/** 搜索所有的企业信息，并筛选 */
	public List<EnterpriseInfo> search(Filters filters) {
		return dao.selectAllByFilter(filters);
	}

	/** 搜索多条记录 */
	private List<EnterpriseInfo> searchList(Filters filters) {
		return dao.selectAllByFilter(filters);
	}

	/** 搜索一条记录 */
	private EnterpriseInfo searchOne(Filters filters) {
		List<EnterpriseInfo> list = this.searchList(filters);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	/** 根据UserID查询 */
	public EnterpriseInfo queryOneByUserId(Integer userId) {
		Filters filters = new Filters();
		filters.setUserId(userId);
		return this.searchOne(filters);
	}

	public List<EnterpriseTopOneModel> enterprisePreferred() {
		return dao.enterprisePreferred();
	}

	/** 根据行业查询企业信息 */
	public List<EnterpriseInfo> industryByEnterprise(String industryDetails) {
		return dao.industryByEnterprise(industryDetails);
	}

	/** 查询最新注册的五个企业用户 */
	public List<EnterpriseInfo> queryQuantityOrderByCreateTime(int pageSize) {
		Filters filter = new Filters();
		filter.setPageNum(1);
		filter.setPageSize(pageSize);
		return dao.selectAll(filter);
	}

	/** 查询所有的企业信息，并进行筛选 */
	public List<EnterpriseInfo> queryAll(Filters filter) {
		return dao.selectAll(filter);
	}

	/** 查询所有的企业信息，并进行筛选 */
	public int queryAll_total(Filters filter) {
		return dao.selectAllTotal(filter);
	}

	/** 查询所有的企业信息，返回筛选不分页总结果数 */
	public int enterpriseInfoFilters_total(Filters filter, String city) {
		return dao.enterpriseInfoFilters_total(filter, city);
	}

	/** 根据行业类别进行模糊查询 */
	public List<EnterpriseTopOneModel> queryLikeByTypeName(String industryName, String regionCity) {
		return dao.queryLikeByTypeName(industryName, regionCity);
	}

	/** 新增企业信息 */
	public boolean add(EnterpriseInfo enterpriseInfo) {
		return dao.insertOne(enterpriseInfo) > 0;
	}

	@Autowired
	CertificationEnterpriseDao ceDao;

	/** 上传图片 */
	public JSONResult uploadPicture(int userId, String pictureName, MultipartFile logo, MultipartFile license) {
		JSONResult jr = new JSONResult();
		if (pictureName == null)
			pictureName = userId + Utils.randomString(32) + ".jpg";
		else
			pictureName = Utils.getFileName(pictureName);
		try {
			EnterpriseInfo ent = new EnterpriseInfo();
			ent.setUser(new Users(userId));
			CertificationEnterprise ce = new CertificationEnterprise();
			ce.setUser(ent.getUser());
			if (logo != null) {
				ossUtil1.uploadFile2OSS(logo.getInputStream(), pictureName);
				ent.setLogo(pictureName + "?" + Utils.randomNumber(6));
				dao.updateOne(ent);
				jr.setValue(RetEnum.SUCCESS);
			} else if (license != null) {
				ossUtil2.uploadFile2OSS(license.getInputStream(), pictureName);
				ent.setBusinessLicense(pictureName + "?" + Utils.randomNumber(6));
				ce.setBusinessLicense(ent.getBusinessLicense().getName());
				dao.updateOne(ent);
				ceDao.byUserIdUpdateOne(ce);
				jr.setValue(RetEnum.SUCCESS);
			} else {
				jr.setValue(RetEnum.PARAM_LACK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jr.setValue(RetEnum.EXCEPTION, e.getMessage());
		}
		return jr;
	}

	/** 修改企业信息 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult updateOne(EnterpriseInfo enterpriseInfo) {
		JSONResult jr = new JSONResult(RetEnum.SUCCESS);
		try {
			Users u = enterpriseInfo.getUser();
			// 判断是否修改了users表
			if (isEmpty(u.getUsername(), u.getPassword(), u.getTypeId() == null ? null : String.valueOf(u.getTypeId()),
					u.getWxOpenId()) < 4) {
				usersService.changeUser(u);
			}
			// 更新user_info表
			if (dao.updateOne(enterpriseInfo) <= 0)
				jr.setValue(RetEnum.AUTH_ERROR, "没权限");
		} catch (DuplicateKeyException dke) {
			jr.setValue(RetEnum.VALUE_EXIST, "触发唯一键约束");
		} catch (Exception e) {
			e.printStackTrace();
			jr.setValue(RetEnum.EXCEPTION, e.getMessage());
		}
		return jr;
	}

	/** 删除企业自上传图片 */
	public JSONResult deletePicture(Integer userId, String num, String name) {
		JSONResult jr = commonPicture(num);
		if (jr.getRet() == RetEnum.SUCCESS.getValue()) {
			Integer intNum = (int) jr.getResult();
			jr = new JSONResult();
			// 删除阿里云OSS上的图片
			// OSSUtil oss = new OSSUtil(PathEnum.ENT_PICTURE);
			name = name.substring(0, name.indexOf("?") == -1 ? name.length() : name.indexOf("?"));
			ossUtil3.deleteOneFile(name);
			// 更新数据库
			if (dao.updatePicture(userId, intNum, null) > 0)
				jr.setValue(RetEnum.SUCCESS);
			else
				jr.setValue(RetEnum.ERROR, "非企业用户不能删除");
		}
		return jr;
	}

	/** 上传或更新企业的二维码图片 */
	public JSONResult updateQrCode(Integer userId, MultipartFile file, String qrCodeName, String picName) {
		JSONResult jr = new JSONResult();
		try {
			// 上传到阿里云OSS
			if (ServletUtil.isEmpty(picName))
				picName = userId + "-qrcode-" + Utils.randomString(32) + ".jpg";
			else
				picName = Utils.getFileName(picName);
			if (ServletUtil.isEmpty(qrCodeName)) {
				jr.setValue(RetEnum.PARAM_LACK, "缺少参数");
			} else {
				// 存入数据库
				EnterpriseInfo ei = new EnterpriseInfo(new Users(userId));
				ei.setQrCodePicture(picName + "?" + Utils.randomNumber(6));
				ei.setQrCodeName(qrCodeName);
				if (dao.updateOne(ei) > 0) {
					ossUtil.uploadFile2OSS(PathEnum.ENT_QR_CODE, file.getInputStream(), picName);
					jr.setValue(RetEnum.SUCCESS);
				} else
					jr.setValue(RetEnum.AUTH_ERROR, "非企业用户不能上传");
			}
		} catch (IOException e) {
			jr.setValue(RetEnum.EXCEPTION, "发生IO异常");
			e.printStackTrace();
		}
		return jr;
	}

	/** 上传或更新企业Banner图片 */
	public JSONResult updateBanner(Integer userId, String num, String picName, MultipartFile file) {
		JSONResult jr = new JSONResult();
		try {
			// 上传到阿里云OSS
			if (ServletUtil.isEmpty(picName))
				picName = userId + "-banner-" + num + Utils.randomString(32) + ".jpg";
			else
				picName = Utils.getFileName(picName);
			ossUtil.uploadFile2OSS(PathEnum.ENT_BANNER, file.getInputStream(), picName);
			// 存入数据库
			if (dao.updateBanner(userId, num, picName + "?" + Utils.randomNumber(6)) > 0)
				jr.setValue(RetEnum.SUCCESS);
			else
				jr.setValue(RetEnum.ERROR, "非企业用户不能上传");
		} catch (IOException e) {
			jr.setValue(RetEnum.EXCEPTION, "发生IO异常");
			e.printStackTrace();
		}
		return jr;
	}

	private JSONResult commonPicture(String num) {
		JSONResult jr = new JSONResult();
		try {
			// 转成数字
			Integer intNum = Integer.parseInt(num);
			// 判断是否超出范围
			if (intNum < 0 || intNum > 9)
				jr.setValue(RetEnum.PARAM_ERROR, "num超出最小范围或最大范围");
			else {
				jr.setValue(RetEnum.SUCCESS, intNum);
			}
		} catch (NumberFormatException e) {
			jr.setValue(RetEnum.ERROR, "num不是数字");
		}
		return jr;
	}

	/** 上传或更新企业资质图片 */
	public JSONResult updatePicture(Integer userId, String num, String lastName, MultipartFile file) {
		JSONResult jr = this.commonPicture(num);
		if (jr.getRet() == RetEnum.SUCCESS.getValue()) {
			Integer intNum = (int) jr.getResult();
			jr = new JSONResult();
			try {
				// 上传到阿里云OSS
				String name = lastName;
				if (ServletUtil.isEmpty(name))
					name = userId + "_" + intNum + "_" + Utils.randomString(6) + ".jpg";
				else
					name = name.substring(0, name.indexOf("?") == -1 ? name.length() : name.indexOf("?"));
				// OSSUtil oss = new OSSUtil(PathEnum.ENT_PICTURE);
				ossUtil3.uploadFile2OSS(file.getInputStream(), name);
				// 存入数据库
				if (dao.updatePicture(userId, intNum, name + "?" + Utils.randomNumber(6)) > 0)
					jr.setValue(RetEnum.SUCCESS);
				else
					jr.setValue(RetEnum.ERROR, "非企业用户不能上传");
			} catch (IOException e) {
				jr.setValue(RetEnum.EXCEPTION, "发生IO异常");
				e.printStackTrace();
			}
		}
		return jr;
	}

	/** 查询最新注册的的五条企业 */
	public List<EnterpriseTopOneModel> oderByEnterpriseMaxFive() {
		return dao.oderByEnterpriseMaxFive();
	}

	/** 删除企业信息 */
	public boolean delete(int enterpriseId) {
		return dao.deleteOne(enterpriseId);
	}

	/** 查询id最大的13条 */
	public List<EnterpriseTopOneModel> enterPriseThirteen(String city) {
		return dao.enterPriseThirteen(city);
	}

	public EnterpriseInfo byIdEnterprise(Integer id) {
		return dao.byIdEnterprise(id);
	}

	/**
	 *
	 * @param userId 用户id
	 * @param philosophyFile 企业理念图片
	 * @param picName 图片的名字
	 * @return
	 */
	public JSONResult updatePhilosophy(Integer userId, MultipartFile philosophyFile, String picName,String idea) {
		JSONResult jr = new JSONResult();
		try {
			// 上传到阿里云OSS
			if (ServletUtil.isEmpty(picName))
				picName = userId + "-philosophy-"  + Utils.randomString(32) + ".jpg";
			else
				picName = Utils.getFileName(picName);
			ossUtil.uploadFile2OSS(PathEnum.ENT_PHILOSOPHY, philosophyFile.getInputStream(), picName);
			// 存入数据库
			if (dao.updateEnterpriseInfo(userId, picName + "?" + Utils.randomNumber(6),null,null,null,idea,null,null,null) > 0)
				jr.setValue(RetEnum.SUCCESS);
			else
				jr.setValue(RetEnum.ERROR, "非企业用户不能上传");
		} catch (IOException e) {
			jr.setValue(RetEnum.EXCEPTION, "发生IO异常");
			e.printStackTrace();
		}
		return jr;

	}

	/**
	 *
	 * @param userId 用户id
	 * @param profileFile 企业简介
	 * @param picName 图片名字
	 * @return
	 */
	public JSONResult updateProfile(Integer userId, MultipartFile profileFile, String picName,String introduce) {
		JSONResult jr = new JSONResult();
		try {
			// 上传到阿里云OSS
			if (ServletUtil.isEmpty(picName))
				picName = userId + "-profile-"  + Utils.randomString(32) + ".jpg";
			else
				picName = Utils.getFileName(picName);
			ossUtil.uploadFile2OSS(PathEnum.ENT_PROFILE, profileFile.getInputStream(), picName);
			// 存入数据库
			if (dao.updateEnterpriseInfo(userId, null,picName + "?" + Utils.randomNumber(6),null,null,null,null,null,introduce) > 0)
				jr.setValue(RetEnum.SUCCESS);
			else
				jr.setValue(RetEnum.ERROR, "非企业用户不能上传");
		} catch (IOException e) {
			jr.setValue(RetEnum.EXCEPTION, "发生IO异常");
			e.printStackTrace();
		}
		return jr;

	}

	/**
	 *
	 * @param userId
	 * @param advantageFile
	 * @param picName
	 * @return
	 */
	public JSONResult updateAdvantage(Integer userId, MultipartFile advantageFile, String picName,String advantageContent) {
		JSONResult jr = new JSONResult();
		try {
			// 上传到阿里云OSS
			if (ServletUtil.isEmpty(picName))
				picName = userId + "-advantage-"  + Utils.randomString(32) + ".jpg";
			else
				picName = Utils.getFileName(picName);
			ossUtil.uploadFile2OSS(PathEnum.ENT_ADVANTAGE, advantageFile.getInputStream(), picName);
			// 存入数据库
			if (dao.updateEnterpriseInfo(userId, null,null,picName + "?" + Utils.randomNumber(6),null,null,null,advantageContent,null) > 0)
				jr.setValue(RetEnum.SUCCESS);
			else
				jr.setValue(RetEnum.ERROR, "非企业用户不能上传");
		} catch (IOException e) {
			jr.setValue(RetEnum.EXCEPTION, "发生IO异常");
			e.printStackTrace();
		}
		return jr;

	}

	/**
	 *
	 * @param userId
	 * @param honorFile
	 * @param picName
	 * @param honorContent
	 * @return
	 */
	public JSONResult updateHonor(Integer userId, MultipartFile honorFile, String picName,String honorContent) {
		JSONResult jr = new JSONResult();
		try {
			// 上传到阿里云OSS
			if (ServletUtil.isEmpty(picName))
				picName = userId + "-honor-"  + Utils.randomString(32) + ".jpg";
			else
				picName = Utils.getFileName(picName);
			ossUtil.uploadFile2OSS(PathEnum.ENT_HONOR, honorFile.getInputStream(), picName);
			// 存入数据库
			if (dao.updateEnterpriseInfo(userId, null,null,null,picName + "?" + Utils.randomNumber(6),null,honorContent,null,null) > 0)
				jr.setValue(RetEnum.SUCCESS);
			else
				jr.setValue(RetEnum.ERROR, "非企业用户不能上传");
		} catch (IOException e) {
			jr.setValue(RetEnum.EXCEPTION, "发生IO异常");
			e.printStackTrace();
		}
		return jr;

	}


}
