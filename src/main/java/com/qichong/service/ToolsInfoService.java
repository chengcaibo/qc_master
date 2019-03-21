package com.qichong.service;

import static com.qichong.util.web.ServletUtil.queryAllChildCodes;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qichong.dao.ToolsInfoDao;
import com.qichong.entity.State;
import com.qichong.entity.ToolsInfo;
import com.qichong.entity.Users;
import com.qichong.enums.PathEnum;
import com.qichong.enums.RetEnum;
import com.qichong.model.Filters;
import com.qichong.model.JSONResult;
import com.qichong.util.OSSUtil;
import com.qichong.util.Utils;
import com.qichong.util.web.ServletUtil;

/** 工具共享业务逻辑层 */
@Service
public class ToolsInfoService {

	@Autowired
	private ToolsInfoDao dao;

	@Autowired
	SharedTypeService sharedTypeService;

	private static OSSUtil ossUtil = new OSSUtil(PathEnum.TOOL_PICTURE);

	/** 搜索多条记录 */
	private List<ToolsInfo> searchList(Filters filters) {
		// 将搜索某项的所有子级
		filters.setSharedCodes(queryAllChildCodes(filters.getSharedCode(), sharedTypeService));
		return dao.selectAllByFilter(filters);
	}

	/** 搜索一条记录 */
	private ToolsInfo searchOne(Filters filters) {
		List<ToolsInfo> list = this.searchList(filters);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	/** 搜索 */
	public List<ToolsInfo> search(Filters filters) {
		// 构造Filter
		return this.searchList(filters);
	}

	/** 查询单个对象，根据id */
	public ToolsInfo queryOneById(Integer id) {
		Filters filters = new Filters();
		filters.setId(id);
		return this.searchOne(filters);
	}

	/** 查询单个对象，根据userId */
	public List<ToolsInfo> queryListByUserId(Integer userId, Filters filters) {
		filters = filters == null ? new Filters() : filters;
		filters.setUserId(userId);
		return this.searchList(filters);
	}

	/** 查询单个对象，根据userId */
	public List<ToolsInfo> queryListByUserId(Integer userId) {
		return this.queryListByUserId(userId, null);
	}

	/** 发布或修改工具信息 */
	public JSONResult submitORUpdate(ToolsInfo tools) {
		JSONResult jr = new JSONResult();
		if (tools.getId() == null) {
			jr = this.submitOne(tools);
		} else {
			jr = this.updateOne(tools);
		}
		return jr;
	}

	/** 发布工具信息 */
	public JSONResult submitOne(ToolsInfo tools) {
		JSONResult jr = new JSONResult();
		// 判断string类型参数是否完整
		boolean paramFlag = ServletUtil.isEmpty(tools.getToolName(), tools.getToolDescription(), tools.getAddress(),
				tools.getAddressName()) > 0;
		// 判断其他非string类型
		paramFlag = paramFlag
				|| (tools.getUser() == null || tools.getUser().getId() == null || tools.getToolRent() == null
						|| tools.getSharedType() == null || tools.getSharedType().getCode() == null
						|| tools.getLatitude() == null || tools.getLongitude() == null);
		if (paramFlag) {
			jr.setValue(RetEnum.PARAM_LACK, "缺少参数");
		} else {
			// 默认值
			tools.setState(new State(1));
			tools.setCreateTime(new Date());
			tools.getPicture1().setName("uploading.jpg"); // 图片上传中
			// 执行插入
			boolean result = dao.insertOne(tools) > 0;
			if (result)
				jr.setValue(RetEnum.SUCCESS, tools.getId());
			else
				jr.setValue(RetEnum.ERROR, "发布工具信息失败，错误未知...");
		}
		return jr;
	}

	/** 修改工具共享信息 */
	public JSONResult updateOne(ToolsInfo tools) {
		JSONResult jr = new JSONResult();
		tools.setUpdateTime(new Date());
		boolean result = dao.updateOne(tools) > 0;
		if (result) {
			jr.setValue(RetEnum.SUCCESS, tools.getId());
		} else {
			jr.setValue(RetEnum.ERROR, "没有权限");
		}
		return jr;
	}

	/**
	 * 上传图片
	 * 
	 * @param file
	 *            图片文件
	 * @param index
	 *            图片编号
	 * @param name
	 *            图片名称，修改的时候需要用到此参数，第一次上传的时候可传null
	 */
	public JSONResult uploadPicture(int toolId, int userId, MultipartFile file, String index, String name) {
		JSONResult jr = new JSONResult();
		if (file == null) {
			jr.setValue(RetEnum.PARAM_LACK);
			return jr;
		}
		try {
			ToolsInfo tools = new ToolsInfo(new Users(userId));
			tools.setId(toolId);
			// 设置图片名称
			name = name == null ? (userId + Utils.randomString(32).toLowerCase() + ".jpg") : Utils.getFileName(name);
			if (index.equals("1")) // 判断类型
				tools.setPicture1(name + "?" + Utils.randomNumber(6));
			else if (index.equals("2"))
				tools.setPicture2(name + "?" + Utils.randomNumber(6));
			else if (index.equals("3"))
				tools.setPicture3(name + "?" + Utils.randomNumber(6));
			else {
				jr.setValue(RetEnum.PARAM_LACK);
				return jr;
			}
			// 执行SQL语句
			if (dao.updateOne(tools) > 0) {
				jr.setValue(RetEnum.SUCCESS, tools.getId());
				// 上传到OSS服务器 如果同名文件会覆盖服务器上的
				ossUtil.uploadFile2OSS(file.getInputStream(), name);
			} else {
				jr.setValue(RetEnum.ERROR, "更新失败...");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jr.setValue(RetEnum.EXCEPTION, e.getMessage());
		}
		return jr;
	}

	/** 删除工具共享信息 */
	public JSONResult deleteOne(Integer id, int userId, String name1, String name2, String name3) {
		JSONResult jr = new JSONResult();
		// 判断参数是否完整
		if (ServletUtil.isEmpty(name1)) {
			jr.setValue(RetEnum.PARAM_LACK);
		} else {
			boolean result = dao.deleteOne(id, userId) > 0;
			if (result) {
				jr.setValue(RetEnum.SUCCESS);
				// 删除阿里云OSS上的图片1
				String name = Utils.getFileName(name1);
				if (!name.equals("uploading.jpg"))
					ossUtil.deleteOneFile(name);
				// 删除图片2
				if (!ServletUtil.isEmpty(name2)) {
					name = Utils.getFileName(name2);
					if (!name.equals("uploading.jpg"))
						ossUtil.deleteOneFile(name);
				}
				// 删除图片3
				if (!ServletUtil.isEmpty(name3)) {
					name = Utils.getFileName(name3);
					if (!name.equals("uploading.jpg"))
						ossUtil.deleteOneFile(name);
				}
			} else {
				jr.setValue(RetEnum.ERROR, "没有权限");
			}
		}
		return jr;
	}

}
