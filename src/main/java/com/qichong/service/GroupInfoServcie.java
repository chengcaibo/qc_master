package com.qichong.service;

import static com.qichong.util.web.ServletUtil.isEmpty;
import static com.qichong.util.web.ServletUtil.queryAllChildCodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.qichong.dao.GroupInfoDao;
import com.qichong.dao.GroupPersonnelDao;
import com.qichong.entity.GroupInfo;
import com.qichong.entity.GroupPersonnel;
import com.qichong.entity.IndustryType;
import com.qichong.entity.OrderGroupMargin;
import com.qichong.entity.Regions;
import com.qichong.enums.GroupIdentityEnum;
import com.qichong.enums.MarginPrice;
import com.qichong.enums.PathEnum;
import com.qichong.enums.RetEnum;
import com.qichong.exception.QCErrorException;
import com.qichong.model.Filters;
import com.qichong.model.GroupInfoModel;
import com.qichong.model.JSONResult;
import com.qichong.util.OSSUtil;
import com.qichong.util.Utils;
import com.qichong.util.web.ServletUtil;

/**
 * 团体信息Service层
 *
 * @创建人 孙建雷
 * @修改时间 2017-11-9 13:43:55
 */
@Service
public class GroupInfoServcie {

	@Autowired
	GroupInfoDao groupDao;
	@Autowired
	IndustryTypeService industryService;
	@Autowired
	RegionsService regionService;
	@Autowired
	GroupPersonnelDao groupPersonnelDao;

	@Autowired
	UserInfoService userInfoService;

	@Autowired
	OrderMarginService marginService;

	@Autowired
	Gson gson;

	private static OSSUtil ossUtil = new OSSUtil(PathEnum.GROUP_COVER);

	/* = = = = = = = = = = 以下是新版团队的方法 = = = = = = = = = = = = = = */

	/** 根据userId查询所在团队 */
	public List<GroupPersonnel> queryGroupsByUserId(int userId) {
		return groupPersonnelDao.selectGroupsByUserId(userId);
	}

	/** 根据groupId查询所有队员 */
	public List<GroupPersonnel> queryPersonnelByGroupId(int groupId) {
		return groupPersonnelDao.selectPersonnelByGroupId(groupId);
	}

	/** 根据手机号码邀请用户 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult  inviteNewPersonnelByPhone(int userId, int groupId, String phone) {
		try {
			List<GroupPersonnel> gps = groupPersonnelDao.selectPersonnelByGroupId(groupId);
			GroupPersonnel gp = null;
			// 首先根据userId和groupId查询出该用户在该团队中的身份
			for (GroupPersonnel groupPersonnel : gps) {
				if (groupPersonnel.getUserId().equals(userId)) {
					gp = groupPersonnel;
					break;
				}
			}
			if (gp == null) {
				return JSONResult.builder(RetEnum.ERROR, "你不是团长或管理员，不能邀请或添加新团员。");
			}
			int identityId = gp.getIdentityId();
			int groupHead = GroupIdentityEnum.GROUP_HEAD.getId();
			int groupAdmin = GroupIdentityEnum.GROUP_ADMIN.getId();
			// 判断用户身份
			if (identityId == groupHead || identityId == groupAdmin) {
				// 查询保证金情况
				List<OrderGroupMargin> ogms = marginService.queryByGroupId(groupId);
				System.out.println("\n= = = = = = = 保证金情况 = = = = = = =");
				System.out.println(Arrays.toString(ogms.toArray()));
				if (ogms.size() == 0) {
					System.out.println("尚未支付保证金");
					return JSONResult.builder(RetEnum.MARGIN_NO_PAY, "尚未交付保证金");
				}
				// 算出保证金总价格
				double countMargin = marginService.queryMarginCountByGroupId(groupId);
				// for (OrderGroupMargin ogm : ogms) {
				// if (ogm.getTransactionId() != null)
				// // 实际交付金额 + 优惠金额
				// countMargin += (ogm.getMargin() + ogm.getOfferMoney());
				// }
				System.out.println("总保证金（查）：" + countMargin);
				// 算出价位
				int peopleCount = gps.size();
				int maxPeople = 0;
				if (0.0 == countMargin) {
					System.out.println("保证金价位：未支付");
					return JSONResult.builder(RetEnum.MARGIN_NO_PAY, "尚未交付保证金");
				} else if (MarginPrice.STAGE1.getPrice() == countMargin) {
					System.out.println("保证金价位：STAGE1");
					maxPeople = MarginPrice.STAGE1.getMax();

				} else if (MarginPrice.STAGE2.getPrice() == countMargin) {
					System.out.println("保证金价位：STAGE2");
					maxPeople = MarginPrice.STAGE2.getMax();

				} else if (MarginPrice.STAGE3.getPrice() == countMargin) {
					System.out.println("保证金价位：STAGE3");
					maxPeople = MarginPrice.STAGE3.getMax();

				} else if (MarginPrice.STAGE4.getPrice() == countMargin) {
					System.out.println("保证金价位：STAGE4");
					maxPeople = MarginPrice.STAGE4.getMax();

				} else if (MarginPrice.STAGE5.getPrice() == countMargin) {
					System.out.println("保证金价位：STAGE5");
					maxPeople = MarginPrice.STAGE5.getMax();
				} else {
					System.out.println("保证金价位：计算失败");
					return JSONResult.builder(RetEnum.ERROR, "未知错误");
				}
				// 并判断人数是否超出
				System.out.println("当前价位最高人数：" + maxPeople);
				System.out.println("添加后人数：" + (peopleCount + 1));
				if ((peopleCount + 1) > maxPeople) {
					System.out.println("人数超出价位，返回补交保证金");
					return JSONResult.builder(RetEnum.MARGIN_LESS, "保证金价位不足");
				}

				// 根据手机号获取用户id
				Integer newUserId = userInfoService.queryUserIdByTelephone(phone);
				if (newUserId != null) {
					GroupPersonnel newGp = new GroupPersonnel(groupId, newUserId, GroupIdentityEnum.GROUP_PLAYER);
					groupPersonnelDao.insertOne(newGp);
					return JSONResult.builder(RetEnum.SUCCESS, "添加成功");

				}
				return JSONResult.builder(RetEnum.ERROR, "所填手机号尚未注册奇虫的个人账户");
			} else {
				return JSONResult.builder(RetEnum.ERROR, "你不是团长或管理员，不能邀请或添加新团员。");
			}
		} catch (org.springframework.dao.DuplicateKeyException e) {
			return JSONResult.builder(RetEnum.ERROR, "对方已是您团队中的一员，无需重复添加！");
		} catch (Exception e) {
			e.printStackTrace();
			return JSONResult.builder(RetEnum.EXCEPTION, e.getMessage());
		}
	}

	/** 添加团队队长，用于创建团队后第一次添加人员 */
	public boolean addGroupHead(int groupId, int userId) {
		GroupPersonnel gp = new GroupPersonnel(groupId, userId, GroupIdentityEnum.GROUP_HEAD);
		return groupPersonnelDao.insertOne(gp) > 0;
	}

	/* = = = = = = = = = = 以上是新版团队的方法 = = = = = = = = = = = = = = */

	/** 搜索多条记录 */
	private List<GroupInfo> searchList(Filters filters) {
		// 将搜索某项的所有子级
		filters.setIndustryCodes(queryAllChildCodes(filters.getIndustryCode(), industryService));
		filters.setRegionCodes(queryAllChildCodes(filters.getRegionCode(), regionService));
		return groupDao.selectAllByFilter(filters);
	}

	/** 搜索一条记录 */
	private GroupInfo searchOne(Filters filters) {
		List<GroupInfo> list = this.searchList(filters);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	/** 搜索 */
	public List<GroupInfo> search(Filters filters) {
		// 构造Filter
		return this.searchList(filters);
	}

	/** 根据团体ID查询单个团体 */
	public GroupInfo queryOneByGroupId(int groupId) {
		// 构造Filter
		Filters filters = new Filters();
		filters.setGroupId(groupId);
		// 执行查询
		return this.searchOne(filters);
	}

	/** 根据用户ID查询团体 */
	public List<GroupInfo> queryListByUserId(Filters filters) {
		// 执行查询
		return this.searchList(filters);
	}

	/** 分页查询所有团体信息，并且可以筛选 */
	public List<GroupInfo> queryAll(Filters filters) {
		return groupDao.selectAllByFilter(filters);
	}

	public int groupInfoFilters_total(Filters filters, String city) {
		return groupDao.groupInfoFilters_total(filters, city);
	}

	/** 查询最近注册的三个用户的信息 */
	public List<GroupInfoModel> groupPreferred() {
		return groupDao.groupPreferred();
	}

	/** 查询最大得的五条团体信息 */
	public List<GroupInfoModel> queryFiveGroup() {
		return groupDao.querFiveGroup();
	}

	@Autowired
	RegionsService regionsService;
	@Autowired
	IndustryTypeService industryTypeService;

	/**
	 * 发布一条团体信息
	 * 
	 * @param coverFile
	 *            要上传的团体图片
	 * @param group
	 *            要发布的团体信息
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult submit(MultipartFile coverFile, GroupInfo group) throws Exception {
		// 判断参数是否完整
		if ((coverFile == null || group.getQuantity() == null || group.getRegion() == null
				|| group.getIndustry() == null)
				|| (isEmpty(group.getGroupName(), group.getIntroduce(), group.getToolCost(), group.getPersonnelCost(),
						group.getRegion().getRegionCode(), group.getIndustry().getIndustryCode()) > 0)) {
			return JSONResult.builder(RetEnum.PARAM_LACK, "缺少参数");
		}
		int userId = group.getUser().getId();

		// 拼接行业和地区
		this.pjIndustryAndRegion(group);
		// 填充默认数据
		group.setCreateTime(new Date());
		// 上传图片到阿里云
		String name = userId + Utils.randomString(32).toLowerCase() + ".jpg";
		ossUtil.uploadFile2OSS(coverFile.getInputStream(), name);
		// 存入数据库
		group.setPicture(name + "?" + Utils.randomNumber(6));
		groupDao.insertOne(group);
		// 添加队长
		int groupId = group.getId();
		addGroupHead(groupId, userId);

		return JSONResult.builder(RetEnum.SUCCESS);
	}

	/** 拼接行业和地区 */
	private void pjIndustryAndRegion(GroupInfo group) {
		// 拼接行业
		if (group.getIndustry() != null && !group.getIndustry().getIndustryCode().equals("0")) {
			IndustryType industryType3 = industryTypeService.selectOne(group.getIndustry().getIndustryCode());
			IndustryType industryType2 = industryTypeService.selectOne(industryType3.getParentCode());
			IndustryType industryType1 = industryTypeService.selectOne(industryType2.getParentCode());
			List<String> list = new ArrayList<String>();
			list.add(industryType1.getIndustryName());
			list.add(industryType2.getIndustryName());
			list.add(industryType3.getIndustryName());
			group.setIndustryDetails(gson.toJson(list));
		} else {
			List<String> list = new ArrayList<String>();
			list.add(String.valueOf(group.getIndustryDetails()));
			group.setIndustryDetails(gson.toJson(list));
		}
		// 拼接地区
		if (group.getRegion() != null && !group.getRegion().getRegionCode().equals("0")) {
			Regions region3 = regionsService.selectOne(group.getRegion().getRegionCode());
			Regions region2 = regionsService.selectOne(region3.getParentRegionCode());
			Regions region1 = regionsService.selectOne(region2.getParentRegionCode());
			String address = region1.getRegionName() + region2.getRegionName() + region3.getRegionName() + " ";
			group.setAddress(address + group.getAddress());
		}
	}

	/** 更新一条团体 */
	public JSONResult updateOne(MultipartFile coverFile, GroupInfo group) {
		// 创建 JSONResult对象，用于返回JSON字符串
		JSONResult jr = new JSONResult();
		// 判断参数是否完整
		if (coverFile == null || isEmpty(group.getPicture().getName())) {
			jr.setValue(RetEnum.PARAM_LACK, "缺少参数");
			return jr;
		}
		try {
			// 修改发布到阿里云OSS
			String name = group.getPicture().getName();
			int indexOf = name.indexOf("?");
			name = name.substring(0, indexOf == -1 ? name.length() : indexOf);
			ossUtil.uploadFile2OSS(coverFile.getInputStream(), name);
			group.setPicture(name + "?" + Utils.randomNumber(6));
			// set默认值
			group.setCreateTime(new Date());
			// 拼接行业和地区
			this.pjIndustryAndRegion(group);
			groupDao.updateOne(group);
			jr.setValue(RetEnum.SUCCESS);
		} catch (IOException e) {
			e.printStackTrace();
			jr.setValue(RetEnum.EXCEPTION, e.getMessage());
		}
		return jr;
	}

	@Autowired
	GroupPersonnelDao personnelDao;

	/**
	 * 根据Id删除一条团体信息
	 * 
	 * @throws QCErrorException
	 */
	@Transactional(rollbackFor = Exception.class)
	public JSONResult deleteOne(Integer id, int loginUserId, String pictureName) throws QCErrorException {
		JSONResult jr = new JSONResult();
		if (id == null || ServletUtil.isEmpty(pictureName)) {
			jr.setValue(RetEnum.PARAM_LACK, "缺少参数");
		} else {
			// 删除人员
			int result = personnelDao.deleteByGroupId(id);
			// 删除数据库
			result += groupDao.deleteOne(id, loginUserId);
			if (result > 0) {
				// 删除阿里云OSS上的图片
				ossUtil.deleteOneFile(Utils.getFileName(pictureName));
				jr.setValue(RetEnum.SUCCESS);
			} else {
				throw new QCErrorException("删除失败：没有权限");
				// jr.setValue(RetEnum.AUTH_ERROR, "删除失败：没有权限");
			}
		}
		return jr;
	}

	/** 根据行业进行模糊查询 */
	public List<GroupInfoModel> byNameGroup(String name, String regionCity) {
		return groupDao.byNameGroupInfo(name, regionCity);
	}

	/** 查询ID最大13条数据 */
	public List<GroupInfoModel> querthirteenGroup(String city) {
		return groupDao.querthirteenGroup(city);
	}

	/** 查询ID最大1条数据 */
	public GroupInfoModel queryOneGroup(String city) {
		return groupDao.queryOneGroup(city);
	}

	/** 查询相同行业的团体 */
	public List<GroupInfoModel> likeGroup(String industryCode) {
		return groupDao.likeGroup(industryCode);
	}

	/** 更新一条团体状态(后台) */
	public JSONResult updateOneState(GroupInfo group) {
		// 创建 JSONResult对象，用于返回JSON字符串
		JSONResult jr = new JSONResult();
		// 判断参数是否完整
		if (group.getId() <= 0 || group.getState().getId() <= 0) {
			jr.setValue(RetEnum.PARAM_LACK, "缺少参数");
			return jr;
		}
		try {
			groupDao.updateOne(group);
			jr.setValue(RetEnum.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			jr.setValue(RetEnum.EXCEPTION, e.getMessage());
		}
		return jr;
	}

}
